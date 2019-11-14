package com.nano.Bush.services.generalMetrics;

import com.nano.Bush.datasources.AgrochemicalsDao;
import com.nano.Bush.datasources.MixturesDao;
import com.nano.Bush.datasources.measures.TreatmentsDao;
import com.nano.Bush.model.Mixture;
import com.nano.Bush.model.generalMetrics.GeneralMixturesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GeneralMixturesService {
    @Autowired
    TreatmentsDao treatmentsDao;
    @Autowired
    MixturesDao mixturesDao;
    @Autowired
    AgrochemicalsDao agrochemicalsDao;

    public GeneralMixturesDTO getGeneralMixturesMetric(Integer companyId) throws SQLException {

        List<Mixture> allMixtures = mixturesDao.getMixtures();

        List<Integer> usedMixturesIds = treatmentsDao.getMixturesUsedInAllTreatmentsByCompany(companyId);

        List<String> usedMixturesNames = allMixtures
                .stream()
                .filter(mixture -> usedMixturesIds.contains(mixture.getIdMixture().get()))
                .map(Mixture::getName)
                .collect(Collectors.toList());

        Map<Integer, Long> countUsedMixturesIds = usedMixturesIds.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        List<String> labels = new ArrayList<>();
        labels.add("MEZCLAS");
        labels.addAll(usedMixturesNames);

        List<String> parents = new ArrayList<>();
        parents.add("");
        parents.addAll(generateEmptyListFrom(usedMixturesNames));

        List<Integer> values = new ArrayList<>();
        values.add(countUsedMixturesIds.values().stream().mapToInt(Long::intValue).sum());
        values.addAll(countUsedMixturesIds.values().stream().map(number -> Integer.parseInt(number.toString())).collect(Collectors.toList()));

        /*List<Tuple3<String, String, Long>> agrochemicalsUsed = agrochemicalsDao.getAllAgrochemicalsUsed();

        parents.addAll(agrochemicalsUsed.stream().map(agrochemicalUsed -> agrochemicalUsed._1).collect(Collectors.toList()));
        labels.addAll(agrochemicalsUsed.stream().map(agrochemicalUsed -> agrochemicalUsed._2).collect(Collectors.toList()));
        values.addAll(agrochemicalsUsed.stream().map(agrochemicalUsed -> agrochemicalUsed._3).collect(Collectors.toList()));*/

        return new GeneralMixturesDTO(values, labels, parents);
    }

    private List<String> generateEmptyListFrom(List<String> mixturesUsed) {
        List<String> emptyList = new ArrayList<>();
        mixturesUsed.forEach(mixture -> emptyList.add("MEZCLAS"));
        return emptyList;
    }


}
