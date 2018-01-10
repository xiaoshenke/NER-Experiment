package wuxian.me.ner;

import org.apache.commons.lang3.tuple.*;
import wuxian.me.ner.service.FileUtil;
import wuxian.me.ner.service.statistic.MidStatistics;
import wuxian.me.ner.service.statistic.Util;
import wuxian.me.ner.service.statistic.Writings;

import java.io.File;
import java.util.*;

/**
 * Created by wuxian on 10/1/2018.
 */
public class StatisticSingle {

    public static void main(String[] args) throws Exception {
        if (args == null || args.length < 1) {
            throw new IllegalArgumentException(
                    "must give these args: inputPath | outputPath ");
        }

        String str = String.valueOf(args[0]);
        if (str.length() == 0) {
            throw new IllegalArgumentException("first argument can't be empty");
        }

        String output = null;
        if (args.length >= 2) {
            output = String.valueOf(args[1]);
        }

        boolean isFile = false;
        File file = null;
        file = new File(str);

        if (file.exists() && file.isFile()) {
            isFile = true;
        }

        if (!isFile) {
            file = new File(FileUtil.getCurrentPath() + "/" + str);
            if (!file.exists() || !file.isFile()) {
                isFile = true;
            }
        }
        String content = isFile ? FileUtil.readFromFile(file.getAbsolutePath()) : str;

        String[] contents = content.split(" ");
        Writings writings = new Writings();
        writings.setBaseWordList(Arrays.asList(contents));
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

        StringBuilder builder = new StringBuilder("");
        for (int i = 0; i < list.size(); i++) {
            Pair<String, Integer> pair = list.get(i);
            builder.append(i + 1 + " " + pair.getKey() + " " + pair.getValue());
            builder.append("\n");
        }

        if (output != null) {
            FileUtil.writeToFile(output, builder.toString());
        } else {
            System.out.println("--------------------- result ---------------------");
            System.out.println(builder.toString());
        }

    }
}
