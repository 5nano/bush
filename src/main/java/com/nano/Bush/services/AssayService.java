package com.nano.Bush.services;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nano.Bush.datasources.CropsDao;
import com.nano.Bush.datasources.measures.AssaysDao;
import com.nano.Bush.datasources.measures.TreatmentsDao;
import com.nano.Bush.model.*;
import io.vavr.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AssayService {

    private static final Logger logger = LoggerFactory.getLogger(AssayService.class);

    @Autowired
    AssaysDao assaysDao;
    @Autowired
    TreatmentsDao treatmentDao;
    @Autowired
    TagsService tagsService;
    @Autowired
    CropsDao cropsDao;

    public List<Assay> getAssays() throws SQLException {
        return assaysDao.getAssays();
    }

    public List<AssayResponse> getAssaysWithExtraInfo() throws SQLException {
      final Map<Integer, Set<Integer>> assayWithTags = tagsService.assayWithTags();
      //sure we have the key
      final Map<Integer,Tag> tagsMap = tagsService.getTags().stream().collect(Collectors.toMap(tag -> tag.getIdTag().get(), Function.identity()));
      final Map<Integer, Crop> cropsMap = cropsDao.getCrops().stream().collect(Collectors.toMap(crop -> crop.getIdCrop().get(), Function.identity()));
        return getAssays().stream()
                .map(assay -> {
                    Crop crop = cropsMap.get(assay.getIdCrop());
                    Agrochemical agrochemical = new Agrochemical(Optional.of(1),"nombre","desc");
                    Mixture mixture = new Mixture(Optional.of(1),"nombre","desc");
                    List<Tag> tags = Option.of(assayWithTags.get(assay.getIdAssay().get()))
                            .map(idTags -> idTags.stream().map(idTag -> tagsMap.get(idTag)).collect(Collectors.toList()))
                            .getOrElse(Collections.emptyList());
                    return new AssayResponse(assay,tags,crop,agrochemical,mixture);
                })
                .collect(Collectors.toList());
    }

    public List<Treatment> getTreatmentsFrom(Integer assayId) {

        List<Treatment> treatments;

        try {
            treatments = treatmentDao.getTreatments(assayId);
        } catch (SQLException e) {
            logger.error("Error al obtener el nombre del experimento, exception: " + e);
            throw new RuntimeException("Error al obtener el nombre del experimento, exception: " + e);
        }
        return treatments;
    }


    public AssayInsertResponse insert(Assay assay) throws SQLException {
        Integer insertAndReturnIdAssay = assaysDao.insert(assay);
        return new AssayInsertResponse(insertAndReturnIdAssay);
    }

}
