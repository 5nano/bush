package com.nano.Bush.services.generalMetrics;

import com.nano.Bush.datasources.measures.TreatmentsDao;
import com.nano.Bush.model.generalMetrics.SankeyAssayDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Service
public class SankeyAssayService {

    @Autowired
    private TreatmentsDao treatmentsDao;

    public SankeyAssayService() {
    }

    public SankeyAssayDTO getSankeyAssays(Integer companyId) throws SQLException {

        List<String> labels = Arrays.asList("Ray Grass", "Mostaza", "Trigo", "A", "B", "C", "Diurex", "Adengo", "Starane Xtra", "Amicor", "Galant", "Unimark", "Hussar", "Solomon",
                "Mospilan", "Assail", "4 Estrellas", "3 Estrellas", "5 Estrellas", "1 estrella");
        List<Integer> source = Arrays.asList(0, 0, 0, 1, 1, 2, 3, 4, 5, 5, 4, 6, 7, 10, 10, 8, 15, 14);
        List<Integer> target = Arrays.asList(10, 4, 5, 3, 10, 5, 6, 8, 7, 14, 15, 16, 17, 16, 18, 19, 19, 18);
        List<Integer> value = Arrays.asList(3, 3, 2, 1, 2, 1, 1, 2, 2, 2, 1, 1, 3, 2, 1, 1, 2, 1, 1, 1, 1);

        /*
        List<Tuple3<String, String, String>> allRelations = new ArrayList<>();

        allRelations.addAll(treatmentsDao.getRelationForSankeyGraphicTuple(cropMixtureRelationQuery(companyId)));
        //allRelations.addAll(treatmentsDao.getRelationForSankeyGraphicTuple(mixtureAgrochemicalRelationQuery));//TODO: agregarlo cuando quede bien la base
        allRelations.addAll(treatmentsDao.getRelationForSankeyGraphicTuple(agrochemicalStateRelationQuery(companyId)));
        allRelations.addAll(treatmentsDao.getRelationForSankeyGraphicTuple(stateStarsRelationQuery(companyId)));

        List<String> labels = allRelations.stream().map(Tuple3::_1).distinct().collect(Collectors.toList());

        labels.addAll(allRelations.stream().map(Tuple3::_2).distinct().collect(Collectors.toList()));

        labels = labels.stream().distinct().collect(Collectors.toList());
        labels.add("SIN FEEDBACK");

        Map<String, Integer> labelWithIndex = new HashMap<>();

        List<String> finalLabels = labels;

        labels.forEach(label -> labelWithIndex.put(label, finalLabels.indexOf(label)));

        List<Tuple3<Integer, Integer, Integer>> finalRelations = new ArrayList<>();

        allRelations.forEach(relation -> finalRelations
                .add(new Tuple3<>(labelWithIndex.get(relation._1()), labelWithIndex.get(relation._2()), Integer.parseInt(relation._3()))));

        finalRelations.add(new Tuple3<>(labelWithIndex.get("ACTIVE"), labelWithIndex.get("SIN FEEDBACK"),
                finalRelations.stream().filter(t -> t._2.equals(labelWithIndex.get("ACTIVE"))).mapToInt(t -> t._3).sum()));

        finalRelations.forEach(relation -> {
            source.add(relation._1());
            target.add(relation._2());
            value.add(relation._3());
        });
         */

        return new SankeyAssayDTO(labels, source, target, value);
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
                " AND e.idCompania = " + companyId +
                " GROUP BY estado,et.estrellas";
    }

}
