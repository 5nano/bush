package com.nano.Bush.services.generalMetrics;

import com.nano.Bush.datasources.measures.TreatmentsDao;
import com.nano.Bush.model.generalMetrics.SankeyAssayDTO;
import io.vavr.Tuple3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class SankeyAssayService {


    private final String cropMixtureRelationQuery = "SELECT c.nombre AS cultivo,m.nombre mezcla, COUNT(t.idTratamiento) AS cant" +
            " FROM tratamiento t " +
            " LEFT JOIN ensayo e ON e.idEnsayo = t.idEnsayo " +
            " LEFT JOIN cultivo c ON e.idCultivo = c.idCultivo " +
            " LEFT JOIN mezcla m ON t.idMezcla = m.idMezcla " +
            " GROUP BY cultivo,mezcla ";
    private final String mixtureAgrochemicalRelationQuery = " SELECT m.nombre AS mezcla,a.nombre AS agroquimico,COUNT(ma.idmezclaAgroquimico) AS cant" +
            " FROM mezclaAgroquimico ma " +
            " LEFT JOIN mezcla m ON ma.idMezcla = m.idMezcla " +
            " LEFT JOIN agroquimico a ON ma.idAgroquimico = a.idAgroquimico " +
            " GROUP BY mezcla,agroquimico ";
    private final String agrochemicalStateRelationQuery = "SELECT e.estado AS estado,a.nombre AS agroquimico,COUNT(t.idTratamiento)" +
            " FROM tratamiento t " +
            " LEFT JOIN agroquimico a ON t.idAgroquimico = a.idAgroquimico " +
            " LEFT JOIN ensayo e ON e.idEnsayo = t.idEnsayo " +
            " GROUP BY estado,agroquimico ";
    private final String stateStarsRelationQuery = "SELECT e.estado AS estado,et.estrellas,COUNT(t.idTratamiento)" +
            " FROM tratamiento t " +
            " LEFT JOIN ensayo e ON e.idEnsayo = t.idEnsayo " +
            " LEFT JOIN ensayoTerminado et ON e.idEnsayo = et.idEnsayo " +
            " WHERE e.estado = 'FINISHED' " +
            " GROUP BY estado,et.estrellas";

    @Autowired
    private TreatmentsDao treatmentsDao;

    public SankeyAssayService() throws SQLException {
    }

    public SankeyAssayDTO getSankeyAssays() throws SQLException {
        return null;
    }

    private List<Tuple3<String, String, String>> getCropMixtureRelation() throws SQLException {
        treatmentsDao.getRelationForSankeyGraphicTuple(cropMixtureRelationQuery);

    }

    private List<Tuple3<String, String, String>> getMixtureAgrochemicalRelation() throws SQLException {
        treatmentsDao.getRelationForSankeyGraphicTuple(mixtureAgrochemicalRelationQuery);
    }

    private List<Tuple3<String, String, String>> getAgrochemicalStateRelation() throws SQLException {
        treatmentsDao.getRelationForSankeyGraphicTuple(agrochemicalStateRelationQuery);
    }

    private List<Tuple3<String, String, String>> getStateStarsRelation() throws SQLException {
        treatmentsDao.getRelationForSankeyGraphicTuple(stateStarsRelationQuery);
    }

}
