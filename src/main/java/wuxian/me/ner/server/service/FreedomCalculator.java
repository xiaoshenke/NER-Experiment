package wuxian.me.ner.server.service;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import wuxian.me.ner.statistic.MidStatistics;
import wuxian.me.ner.statistic.Util;
import wuxian.me.ner.statistic.Writings;

import java.util.*;

/**
 * Created by wuxian on 1/1/2018.
 */
public class FreedomCalculator {

    private int wordAppearanceLimit = -1;

    public void setWordAppearanceLimit(int limit) {
        if (limit < -1) {
            return;
        }
        wordAppearanceLimit = limit;
    }

    public FreedomCalculator() {

    }

    public List<Pair<String, Double>> calculate(List<String> wordList, int len, int type) throws Exception {
        Map<MidStatistics.Word, Double> map = null;
        Writings writings = new Writings();
        writings.setBaseWordList(wordList);

        MidStatistics midStatistics = new MidStatistics(len);
        midStatistics.generateWordsMap(writings);

        if (type == 0) { //left
            map = calLeftFreedom(midStatistics);
        } else { //right
            map = calRightFreedom(midStatistics);
        }

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

    public Map<MidStatistics.Word, Double> calRightFreedom(MidStatistics mid) {
        if (mid == null || !mid.ready()) {
            return null;
        }

        Writings writings = mid.getWritings();
        if (!writings.ready()) {
            return null;
        }

        Iterator<Map.Entry<MidStatistics.Word, List<Integer>>> iterator = mid.wordPostionMap.entrySet().iterator();

        Map<MidStatistics.Word, Double> ret = new HashMap<MidStatistics.Word, Double>(mid.size());

        int i = 0;
        while (iterator.hasNext()) {
            Map.Entry<MidStatistics.Word, List<Integer>> entry = iterator.next();
            MidStatistics.Word w = entry.getKey();
            //这是 你好啊
            ret.put(w, calFreedom(calRigthMidFreedom(w, entry.getValue(), writings)));
        }

        return ret;
    }

    public Map<MidStatistics.Word, Double> calLeftFreedom(MidStatistics mid) {
        if (mid == null || !mid.ready() || mid.dimension < 2) {
            return null;
        }

        Writings writings = mid.getWritings();
        if (!writings.ready()) {
            return null;
        }

        Iterator<Map.Entry<MidStatistics.Word, List<Integer>>> iterator = mid.wordPostionMap.entrySet().iterator();

        Map<MidStatistics.Word, Double> ret = new HashMap<MidStatistics.Word, Double>(mid.size());

        int i = 0;
        while (iterator.hasNext()) {
            Map.Entry<MidStatistics.Word, List<Integer>> entry = iterator.next();
            MidStatistics.Word w = entry.getKey();
            ret.put(w, calFreedom(calLeftMidFreedom(entry.getValue(), writings)));
        }

        return ret;
    }

    //找出所有右集合
    private Map<String, Integer> calRigthMidFreedom(MidStatistics.Word word, List<Integer> postions, Writings writings) {
        if (postions == null || word == null || word.word == null || postions.size() == 0 || writings == null || !writings.ready()) {
            return null;
        }

        Map<String, Integer> map = new HashMap<String, Integer>(postions.size());
        for (Integer pos : postions) {
            if (pos <= 0 || (pos + word.word.length()) >= writings.size()) {
                continue;
            }
            //注意这里位置的计算
            String s = writings.indexOf(pos + word.word.length());
            if (s == null) {
                continue;
            }
            if (map.containsKey(s)) {
                map.put(s, map.get(s) + 1);
            } else {
                map.put(s, 1);
            }
        }
        return map;
    }

    //找出所有左集合
    private Map<String, Integer> calLeftMidFreedom(List<Integer> postions, Writings writings) {
        if (postions == null || postions.size() == 0 || writings == null || !writings.ready()) {
            return null;
        }

        Map<String, Integer> map = new HashMap<String, Integer>(postions.size());
        for (Integer pos : postions) {
            if (pos <= 0 || pos >= writings.size()) {
                continue;
            }
            String s = writings.indexOf(pos - 1);
            if (s == null) {
                continue;
            }
            if (map.containsKey(s)) {
                map.put(s, map.get(s) + 1);
            } else {
                map.put(s, 1);
            }
        }
        return map;
    }

    //根据集合计算熵
    private double calFreedom(Map<String, Integer> mid) {
        if (mid == null) {
            return -1;
        }

        if (mid.size() == 1) {
            return 0;
        }

        //抛弃数据量比较少的数据 加快计算
        if (wordAppearanceLimit != -1 && mid.size() < wordAppearanceLimit) {
            return 0;
        }

        List<Integer> l = new ArrayList<Integer>(mid.values());
        int total = 0;
        for (Integer i : l) {
            total += i;
        }
        if (total == 0) {
            return -1;
        }
        Iterator<Map.Entry<String, Integer>> iter = mid.entrySet().iterator();

        double ret = 0;

        while (iter.hasNext()) {
            Map.Entry<String, Integer> entry = iter.next();
            double d = entry.getValue() / (double) total;
            ret += (-d * Math.log(d));

        }
        return ret;
    }


}
