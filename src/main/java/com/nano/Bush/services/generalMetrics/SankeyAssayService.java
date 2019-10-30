package com.nano.Bush.services.generalMetrics;

import com.nano.Bush.datasources.AgrochemicalsDao;
import com.nano.Bush.datasources.CropsDao;
import com.nano.Bush.datasources.MixturesDao;
import com.nano.Bush.datasources.measures.AssaysDao;
import com.nano.Bush.model.Agrochemical;
import com.nano.Bush.model.Crop;
import com.nano.Bush.model.Mixture;
import com.nano.Bush.model.generalMetrics.SankeyAssayDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SankeyAssayService {

    @Autowired
    AgrochemicalsDao agrochemicalsDao;
    @Autowired
    MixturesDao mixturesDao;
    @Autowired
    CropsDao cropsDao;
    @Autowired
    AssaysDao assaysDao;

    public SankeyAssayDTO getSankeyAssays() throws SQLException {


        return new SankeyAssayDTO(getlabels(), , , );
    }

    private List<String> getlabels() throws SQLException {
        List<String> labels = new ArrayList<>();
        labels.addAll(cropsDao.getCrops().stream().map(Crop::getName).collect(Collectors.toList()));
        labels.addAll(agrochemicalsDao.getAgrochemicals().stream().map(Agrochemical::getName).collect(Collectors.toList()));
        labels.addAll(mixturesDao.getMixtures().stream().map(Mixture::getName).collect(Collectors.toList()));
        labels.addAll(assaysDao.getAssays().stream().map(assay -> assay.getState().get().toString()).collect(Collectors.toList()))

    }

    /**
     * cultivos -> mezclas-> agroquimicos -> estado */

    /**
     *  select m.nombre, a.nombre,count(ma.idAgroquimico) as cant_agroquimicos_mezclas from mezclaAgroquimico ma
     *  join mezcla m on m.idMezcla=ma.idMezcla
     *  join agroquimico a on a.idAgroquimico=ma.idAgroquimico
     *  group by m.nombre, a.nombre
     *
     *
     *  select m.nombre, count(t.idTratamiento) as cant_tratamientos_mezcla from tratamiento t
     *  join mezcla m on m.idMezcla=t.idMezcla
     *  group by t.idMezcla,m.nombre
     *
     *  select c.nombre, count(e.nombre) as cant_ensayos_cultivos from ensayo e
     *  join cultivo c on c.idCultivo=e.idCultivo
     *  group by e.idCultivo,c.nombre*/
}
