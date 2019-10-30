package com.nano.Bush.services.generalMetrics;

import com.nano.Bush.datasources.measures.MeasuresDao;
import com.nano.Bush.model.generalMetrics.HistogramDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class HistogramService {

    @Autowired
    MeasuresDao measuresDao;

    public HistogramDTO getHistogramTest() {
        List<LocalDate> dateForPictures = measuresDao.getDateForPictures();

        Map<String, Long> countPicturesByDay = dateForPictures
                .stream()
                .map(LocalDate::toString)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Map<String, Long> countPicturesByDaySorted = new TreeMap<>(countPicturesByDay);


        List<String> count = countPicturesByDaySorted.values()
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        return new HistogramDTO(new ArrayList<>(countPicturesByDaySorted.keySet()), count);
    }
}
