package wuxian.me.ner.service.statistic;


import java.util.*;

/**
 * Created by wuxian on 10/8/2017.
 */
public class Util {

    private Util() {
    }

    public static List<StatisModel> toModelList(Map<MidStatistics.Word, List<Integer>> map) {
        if (map == null) {
            return null;
        }

        List<StatisModel> modelList = new ArrayList<StatisModel>(map.size());

        Iterator<Map.Entry<MidStatistics.Word, List<Integer>>> iter = map.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry<MidStatistics.Word, List<Integer>> entry = iter.next();

            StatisModel m = new StatisModel();
            m.key = entry.getKey().getWord();
            m.positions = entry.getValue();

            modelList.add(m);
        }
        return modelList;
    }

    public static <T, U> List<Map.Entry<T, U>> getSortedMap(Map<T, U> map, Comparator<U> comparator) {
        if (map == null || comparator == null) {
            return null;
        }

        List<Map.Entry<T, U>> l = new ArrayList<>(map.entrySet());
        Collections.sort(l, new Comparator<Map.Entry<T, U>>() {

            public int compare(Map.Entry<T, U> left, Map.Entry<T, U> right) {
                return comparator.compare(left.getValue(), right.getValue());
            }
        });
        return l;
    }

    public static void sortAndPrint(Map<MidStatistics.Word, Double> map, int maxNum) {
        if (map != null) {
            final List<Map.Entry<MidStatistics.Word, Double>> l
                    = new ArrayList<Map.Entry<MidStatistics.Word, Double>>(map.entrySet());

            Collections.sort(l, new Comparator<Map.Entry<MidStatistics.Word, Double>>() {

                public int compare(Map.Entry<MidStatistics.Word, Double> left, Map.Entry<MidStatistics.Word, Double> right) {

                    if (left.getValue() > right.getValue()) {
                        return -1;
                    }
                    if (right.getValue() > left.getValue()) {
                        return 1;
                    }
                    return 0;
                }
            });


            Map<MidStatistics.Word, Double> ret = new HashMap<MidStatistics.Word, Double>();

            Iterator<Map.Entry<MidStatistics.Word, Double>> iter = l.iterator();
            Map.Entry<MidStatistics.Word, Double> tmpEntry = null;

            int i = 0;
            while (iter.hasNext()) {

                if (++i >= maxNum) {
                    break;
                }
                tmpEntry = iter.next();
                ret.put(tmpEntry.getKey(), tmpEntry.getValue());

                //LogManager.info("key:" + tmpEntry.getKey().toString() + " value:" + tmpEntry.getValue());
            }
        }
    }

}