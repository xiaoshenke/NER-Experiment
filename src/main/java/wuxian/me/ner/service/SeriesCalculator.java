package wuxian.me.ner.service;

import wuxian.me.ner.service.statistic.MidStatistics;
import wuxian.me.ner.service.statistic.Util;
import wuxian.me.ner.service.statistic.Writings;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.*;

/**
 * Created by wuxian on 1/1/2018.
 */
public class SeriesCalculator {

    public SeriesCalculator() {
    }

    public List<Pair<String, Integer>> calculate(List<String> list
            , int len) throws Exception {

        Writings writings = new Writings();
        writings.setBaseWordList(list);

        MidStatistics midStatistics = new MidStatistics(len);
        midStatistics.generateWordsMap(writings);
        Map<MidStatistics.Word, List<Integer>> map = midStatistics.getWordPostionMap();
        List<Map.Entry<MidStatistics.Word, List<Integer>>> sortedList = Util.getSortedMap(map, new Comparator<List<Integer>>() {
            @Override
            public int compare(List<Integer> o1, List<Integer> o2) {
                int ret = o1.size() < o2.size() ? 1 : (o1.size() == o2.size() ? 0 : -1);
                return ret;
            }
        });

        List<Pair<String, Integer>> ret = new ArrayList<>(sortedList.size());
        for (Map.Entry<MidStatistics.Word, List<Integer>> entry : sortedList) {
            ret.add(new ImmutablePair(entry.getKey().getWord(), entry.getValue().size()));
        }
        return ret;
    }

}
