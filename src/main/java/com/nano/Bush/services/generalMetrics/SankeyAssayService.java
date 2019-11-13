package com.nano.Bush.services.generalMetrics;

import com.nano.Bush.datasources.measures.TreatmentsDao;
import com.nano.Bush.model.generalMetrics.SankeyAssayDTO;
import io.vavr.Tuple3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SankeyAssayService {

    private List<Integer> source = new ArrayList<>();
    private List<Integer> target = new ArrayList<>();
    private List<Integer> value = new ArrayList<>();

    @Autowired
    private TreatmentsDao treatmentsDao;

    public SankeyAssayService() {
    }

    public SankeyAssayDTO getSankeyAssays(Integer companyId) throws SQLException {
        List<Tuple3<String, String, String>> allRelations = new ArrayList<>();
        allRelations.addAll(treatmentsDao.getRelationForSankeyGraphicTuple(cropMixtureRelationQuery(companyId)));
        //allRelations.addAll(treatmentsDao.getRelationForSankeyGraphicTuple(mixtureAgrochemicalRelationQuery));//TODO: agregarlo cuando quede bien la base
        allRelations.addAll(treatmentsDao.getRelationForSankeyGraphicTuple(agrochemicalStateRelationQuery(companyId)));
        allRelations.addAll(treatmentsDao.getRelationForSankeyGraphicTuple(stateStarsRelationQuery(companyId)));

        List<String> labels = allRelations.stream().map(Tuple3::_1).distinct().collect(Collectors.toList());

        labels.addAll(allRelations.stream().map(Tuple3::_2).distinct().collect(Collectors.toList()));

        labels = labels.stream().distinct().collect(Collectors.toList());

        Map<String, Integer> labelWithIndex = new HashMap<>();

        List<String> finalLabels = labels;

        labels.forEach(label -> labelWithIndex.put(label, finalLabels.indexOf(label)));

        List<Tuple3<Integer, Integer, Integer>> finalRelations = new ArrayList<>();

        allRelations.forEach(relation -> finalRelations.add(new Tuple3<>(labelWithIndex.get(relation._1()), labelWithIndex.get(relation._2()), Integer.parseInt(relation._3()))));

        finalRelations.forEach(this::addTupleInLists);

        return new SankeyAssayDTO(labels, source, target, value);
    }

    private void addTupleInLists(Tuple3<Integer, Integer, Integer> relation) {
        source.add(relation._1());
        target.add(relation._2());
        value.add(relation._3());
    }

    private String cropMixtureRelationQuery(Integer companyId) {
        return "SELECT c.nombre AS cultivo,m.nombre mezcla, COUNT(t.idTratamiento) AS cant" +
                " FROM tratamiento t " +
                " LEFT JOIN ensayo e ON e.idEnsayo = t.idEnsayo " +
                " LEFT JOIN cultivo c ON e.idCultivo = c.idCultivo " +
                " LEFT JOIN mezcla m ON t.idMezcla = m.idMezcla " +
                " WHERE e.idCompania = " + companyId +
                " GROUP BY cultivo,mezcla ";
    }

    private String mixtureAgrochemicalRelationQuery(Integer companyId) {
        return " SELECT m.nombre AS mezcla,a.nombre AS agroquimico,COUNT(ma.idmezclaAgroquimico) AS cant" +
                " FROM mezclaAgroquimico ma " +
                " LEFT JOIN mezcla m ON ma.idMezcla = m.idMezcla " +
                " LEFT JOIN agroquimico a ON ma.idAgroquimico = a.idAgroquimico " +
                " WHERE e.idCompania = " + companyId +
                " GROUP BY mezcla,agroquimico ";
    }

    private String agrochemicalStateRelationQuery(Integer companyId) {
        return "SELECT m.nombre AS mezcla,e.estado AS estado,COUNT(t.idTratamiento)" +
                " FROM tratamiento t " +
                " LEFT JOIN mezcla m ON m.idMezcla = t.idMezcla " +
                " LEFT JOIN ensayo e ON e.idEnsayo = t.idEnsayo " +//TODO: ACA TIENE QUE SER AGROQUIMICO CON ESTADO
                " WHERE e.idCompania = " + companyId +
                " GROUP BY mezcla,estado ";
    }

    private String stateStarsRelationQuery(Integer companyId) {
        return "SELECT e.estado AS estado,et.estrellas,COUNT(t.idTratamiento)" +
                " FROM tratamiento t " +
                " LEFT JOIN ensayo e ON e.idEnsayo = t.idEnsayo " +
                " LEFT JOIN ensayoTerminado et ON e.idEnsayo = et.idEnsayo " +
                " WHERE e.estado = 'FINISHED' " +
                " WHERE e.idCompania = " + companyId +
                " GROUP BY estado,et.estrellas";
    }

}
