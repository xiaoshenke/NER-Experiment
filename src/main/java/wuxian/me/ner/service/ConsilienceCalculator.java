package wuxian.me.ner.service;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import wuxian.me.ner.service.statistic.MidStatistics;
import wuxian.me.ner.service.statistic.Util;
import wuxian.me.ner.service.statistic.Writings;

import java.util.*;

/**
 * Created by wuxian on 1/1/2018.
 */
public class ConsilienceCalculator {

    private int wordAppearanceLimit = -1;

    public void setWordAppearanceLimit(int limit) {
        if (limit < -1) {
            return;
        }
        wordAppearanceLimit = limit;
    }

    public ConsilienceCalculator() {
    }

    public List<Pair<String, Double>> calculate(List<String> wordList, int len) throws Exception {

        Map<MidStatistics.Word, Double> map = null;
        Writings writings = new Writings();
        writings.setBaseWordList(wordList);

        MidStatistics midStatistics = new MidStatistics(len);
        midStatistics.generateWordsMap(writings);

        map = calConcretion(midStatistics);

        if (map == null) {
            throw new Exception("calculate freedom fail!");
        }
        List<Map.Entry<MidStatistics.Word, Double>> sortedList = Util.getSortedMap(map, new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                int ret = o1 < o2 ? 1 : (o1.equals(o2) ? 0 : -1);
                return ret;
            }
        });
        List<Pair<String, Double>> ret = new ArrayList<>(sortedList.size());
        for (Map.Entry<MidStatistics.Word, Double> entry : sortedList) {
            ret.add(new ImmutablePair(entry.getKey().getWord(), entry.getValue()));
        }

        return ret;

    }

    public Map<MidStatistics.Word, Double> calConcretion(MidStatistics mid) {
        if (mid == null || !mid.ready() || mid.dimension < 2) {
            return null;
        }

        Writings writings = mid.getWritings();
        if (!writings.ready()) {
            return null;
        }

        Map<MidStatistics.Word, Double> maps = new HashMap<MidStatistics.Word, Double>(mid.getSize());

        Iterator<Map.Entry<MidStatistics.Word, List<Integer>>> iterator = mid.wordPostionMap.entrySet().iterator();

        int writingsTotal = writings.getOriginWordSize();
        int midTotal = mid.getOriginWordSize();

        //LogManager.info("writing total:" + writingsTotal + " mid total:" + midTotal);

        while (iterator.hasNext()) {

            Map.Entry<MidStatistics.Word, List<Integer>> entry = iterator.next();

            MidStatistics.Word w = entry.getKey();

            List<MidStatistics.Word> list = w.getSubWords();

            double d = 1;

            //为了加快计算 忽略那些频次太低的数据
            if (wordAppearanceLimit != -1 && mid.getPositionsLen(w) < wordAppearanceLimit) {
                maps.put(w, (double) 0);
                continue;
            }

            //Todo:边缘光滑
            for (MidStatistics.Word fw : list) {
                d = d * writings.getPositionsLen(fw) / (double) writingsTotal;
            }

            d = (mid.getPositionsLen(w) / (double) midTotal) / d;  //概率的比值
            maps.put(w, d);
        }
        return maps;
    }
}