package com.nano.Bush.services;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nano.Bush.datasources.CropsDao;
import com.nano.Bush.datasources.measures.AssaysDao;
import com.nano.Bush.datasources.measures.MeasuresDao;
import com.nano.Bush.datasources.measures.TreatmentsDao;
import com.nano.Bush.model.*;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Service
public class AssayService {

  private static final Logger logger = LoggerFactory.getLogger(AssayService.class);
  public static final String all = "ALL";


  @Autowired
  AssaysDao assaysDao;
  @Autowired
  TreatmentsDao treatmentDao;
  @Autowired
  MeasuresDao measuresDao;
  @Autowired
  TagsService tagsService;
  @Autowired
  CropsDao cropsDao;
  @Autowired
  TreatmentsService treatmentsService;


  public List<AssayResponse> getAllAssays() {
    return Try.of(() -> assaysDao.getAllAssays())
            .onFailure(e -> logger.error("Unexpected error", e))
            .map(assays -> enrichAssays(assays))
            .getOrElse(emptyList());
  }

  public List<AssayResponse> getAssaysByState(String state) {
    AssayStatesEnum assayState = AssayStatesEnum.valueOf(state);
    return Try.of(() -> assaysDao.getAssaysByState(assayState))
            .onFailure(e -> logger.error("Unexpected error", e))
            .map(assays -> enrichAssays(assays))
            .getOrElse(emptyList());
  }

  public void deleteTestFromExperiments(Integer assayId) throws SQLException {
    assaysDao.getExperimentsFromAssay(assayId)
            .forEach(experiment -> measuresDao.deleteExperiment(assayId, experiment.getExperimentId().get()));
  }

  public List<AssayResponse> enrichAssays(List<Assay> assays) {
    final Map<Integer, Set<Integer>> assayWithTags = tagsService.assayWithTags();
    //TODO conviene mucho mas tal vez consultar ahora directamente por assay en vez de buscarme toodo
    // por el tema de que ahora piden los ensayos por estado
    // aunque serian muchos pedidos a la base
    // resolver por queris? Me da mucha paja to mucho joins
    final List<Tag> tags = Try.of(() -> tagsService.getTags())
            .onFailure(e -> logger.error("Unexpected error", e))
            .getOrElse(emptyList());
    final List<Crop> crops = Try.of(() -> cropsDao.getCrops())
            .onFailure(e -> logger.error("Unexpected error", e))
            .getOrElse(emptyList());
    //sure we have the key
    final Map<Integer, Tag> tagsMap = tags.stream().collect(Collectors.toMap(tag -> tag.getIdTag().get(), Function.identity()));
    final Map<Integer, Crop> cropsMap = crops.stream().collect(Collectors.toMap(crop -> crop.getIdCrop().get(), Function.identity()));
    // para traerme el id de de mezclas y agroquimicos necesito los tratamientos del ensayo

    return assays.stream()
            .map(assay -> {
              //crop = cultivo
              final Crop crop = cropsMap.get(assay.getIdCrop());
              final List<Tag> assayTags = Option.of(assayWithTags.get(assay.getIdAssay().get()))
                      .map(idTags -> idTags.stream().map(idTag -> tagsMap.get(idTag)).collect(Collectors.toList()))
                      .getOrElse(emptyList());
              Tuple2<Set<Integer>, Set<Integer>> agrochemicalMixtures = Tuple.of(Sets.newHashSet(), Sets.newHashSet());
              final List<Treatment> treatments = treatmentsService.treatments(assay.getIdAssay().get());
              List<Integer> associatedExperiments = Lists.newArrayList();
              treatments.forEach(treatment -> {
                agrochemicalMixtures._1.add(treatment.getIdAgrochemical());
                agrochemicalMixtures._2.add(treatment.getIdMixture());
                associatedExperiments.add(treatmentDao.getExperimentsCount(treatment.getIdTreatment().get()));
              });
              final Integer associatedExperimentsValue = associatedExperiments.stream().mapToInt(Integer::intValue).sum();
              return new AssayResponse(assay,assayTags, crop, agrochemicalMixtures._1, agrochemicalMixtures._2,treatments.size(),associatedExperimentsValue);
            })
            .collect(Collectors.toList());
  }


  public AssayInsertResponse insert(Assay assay) throws SQLException {
    Integer insertAndReturnIdAssay = assaysDao.insert(assay);
    return new AssayInsertResponse(insertAndReturnIdAssay);
  }

  public void archiveAssay(Integer idAssay) throws SQLException {
    assaysDao.archiveAssay(idAssay);
  }

  public void activeAssay(Integer idAssay) throws SQLException {
    assaysDao.activeAssay(idAssay);
  }

  public void finishAssay(Integer idAssay, Integer stars, String commments) throws SQLException {
    assaysDao.finishAssay(idAssay, stars, commments);
  }

}
