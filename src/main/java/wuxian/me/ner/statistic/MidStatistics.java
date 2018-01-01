package wuxian.me.ner.statistic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wuxian on 10/8/2017.
 * 从writings中获取长度为dimension的词频
 * 比如说这么一个句子 我在电影院看电影院。 Writings分词 我 在 电影 院 看 电影 院。
 * 那么长度为2的MidStatistics为 我在,0 在电影,1 电影院,2 5 院看,3 看电影,4。
 * 长度为3的MidStatistics为 我在电影,0 在电影院,1 电影院看,2 院看电影,3 看电影院,4。
 */
public class MidStatistics {

    //单个单词的长度限制 若这个值不为默认值-1 那么超过这个长度的词会被抛弃 从而不放入@wordPostionMap
    public int singleWordLenLimit = -1;

    //必须在调用@generateWordsMap前调用
    public void setSingleWordLenLimit(int limit) {
        if (limit < -1) {  //无效的值
            return;
        }
        singleWordLenLimit = limit;
    }

    public int dimension;

    public Map<Word, List<Integer>> wordPostionMap;

    //从统计词云@wordPostionMap中找到某个词在writings的位置总数
    public Integer getPositionsLen(Word word) {
        return getPositionsOfWord(word).size();
    }

    //从统计词云@wordPostionMap中找到某个词在writings的所有位置
    public List<Integer> getPositionsOfWord(Word word) {
        List<Integer> positions = new ArrayList<Integer>();
        if (word == null || wordPostionMap == null || !wordPostionMap.containsKey(word)) {
            return positions;
        }
        return wordPostionMap.get(word);
    }

    public int size() {
        return wordPostionMap == null ? 0 : wordPostionMap.keySet().size();
    }

    private Writings mWritings;

    public Writings getWritings() {
        return mWritings;
    }

    public boolean ready() {
        return wordPostionMap != null && mWritings != null;
    }

    public MidStatistics(int dimension) {
        this.dimension = dimension;
    }

    public int getDimension() {
        return dimension;
    }

    public Map<Word, List<Integer>> getWordPostionMap() {
        return wordPostionMap;
    }


    public void setWordPostionMap(Map<Word, List<Integer>> map) {
        this.wordPostionMap = map;
    }

    public int getSize() {
        return wordPostionMap == null ? 0 : wordPostionMap.size();
    }

    public int getOriginWordSize() {
        return mWritings == null ? 0 : (mWritings.getBaseWordList().size() - dimension + 1);
    }

    //生成 "切分词" 在原先writings里的位置 比如说 {比特币 [1,12,98]},{湖人 [3,4]}
    public void generateWordsMap(Writings writings) {
        if (writings == null) {
            return;
        }

        if (wordPostionMap != null) {
            return;
        }
        mWritings = writings;

        List<String> list = writings.getBaseWordList();
        Map<Word, List<Integer>> wordListMap = new HashMap<Word, List<Integer>>();
        //从origin word list里拿到n个长度词组成的词 及扫描记录位置
        for (int i = 0; i < list.size() - dimension + 1; i++) {
            Word word = new Word();
            int num = 0;
            while (num < dimension) {
                word.addContent(list.get(i + num));
                num++;
            }
            word.generateWord();

            //抛弃那些单词长度太长的"切分词"
            if (singleWordLenLimit != -1 && word.size() > singleWordLenLimit) {
                continue;
            }

            if (wordListMap.containsKey(word)) {
                wordListMap.get(word).add(i);
            } else {
                List<Integer> pList = new ArrayList<Integer>();
                pList.add(i);
                wordListMap.put(word, pList);
            }
        }
        wordPostionMap = wordListMap;
    }

    public static class Word {
        @Override
        public String toString() {
            return "Word{" +
                    "word='" + word + '\'' +
                    '}';
        }

        public Word() {
        }

        public Word(String s) {
            word = s;

        }

        public String word;

        public List<Word> getSubWords() {
            return splits;
        }

        //单词组成
        public List<Word> splits = new ArrayList<Word>();

        public void addContent(String s) {
            splits.add(new Word(s));  //Fixme:这样子就只有一层了
        }

        public void generateWord() {
            StringBuilder b = new StringBuilder("");
            for (Word s : splits) {
                b.append(s.word);
            }
            word = b.toString();
        }

        public int size() {
            return word == null ? 0 : word.length();
        }

        public String getWord() {
            return word;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Word)) return false;

            Word word1 = (Word) o;

            return word != null ? word.equals(word1.word) : word1.word == null;

        }

        @Override
        public int hashCode() {
            return word != null ? word.hashCode() : 0;
        }
    }
}