package wuxian.me.ner.statistic;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by wuxian on 1/1/2018.
 */
public class WritingsTest {

    Writings writings;

    @Before
    public void setup() {
        writings = new Writings();
        List<String> baseWordList = new ArrayList<>();
        baseWordList.add("今年");
        baseWordList.add("马上");
        baseWordList.add("就");
        baseWordList.add("要");
        baseWordList.add("结束");
        baseWordList.add("明年");
        baseWordList.add("马上");
        baseWordList.add("就");
        baseWordList.add("来了");

        writings.setBaseWordList(baseWordList);  //这里应该由分词程序切好词 然后set一下
    }

    @Test
    public void testWritings() {
        writings.generateWordsMap(writings);
        System.out.println(writings.getWordPostionMap());
    }

    @Test
    public void testSegAndSort() {
        writings.generateWordsMap(writings);
        System.out.println(Util.getSortedMap(writings.getWordPostionMap(), new Comparator<List<Integer>>() {
            @Override
            public int compare(List<Integer> o1, List<Integer> o2) {
                int ret = o1.size() < o2.size() ? 1 : (o1.size() == o2.size() ? 0 : -1);
                return ret;
            }
        }));
    }

    @Test
    public void fulltest() {
        //https://zhuanlan.zhihu.com/p/32462707?group_id=930521045799038976
        /*
        DictionaryTrie trie = DictionaryTrie.getIns();
        Segmentation segmentation = new MaxLengthMatching(true);
        long cur = System.currentTimeMillis();
        trie.initWithDefaultWords();
        System.out.println("load dictionary cost " + (System.currentTimeMillis() - cur) + " millis");
        segmentation.setDictionary(trie);

        cur = System.currentTimeMillis();
        List<String> baseWordList = segmentation.seg(FileUtil.readFromFile(FileUtil.getCurrentPath() + "/article/test1.txt"));

        writings.setBaseWordList(baseWordList);
        writings.generateWordsMap(writings);

        Map<MidStatistics.Word, List<Integer>> map = writings.getWordPostionMap();
        List<Map.Entry<MidStatistics.Word, List<Integer>>> sortedList = Util.getSortedMap(map, new Comparator<List<Integer>>() {
            @Override
            public int compare(List<Integer> o1, List<Integer> o2) {
                int ret = o1.size() < o2.size() ? 1 : (o1.size() == o2.size() ? 0 : -1);
                return ret;
            }
        });
        List<Pair<String, Integer>> list = new ArrayList<>(map.size());

        for (Map.Entry<MidStatistics.Word, List<Integer>> tmpEntry : sortedList) {
            list.add(new ImmutablePair(tmpEntry.getKey().getWord(), tmpEntry.getValue().size()));
        }
        System.out.println(list);
        */
    }
}