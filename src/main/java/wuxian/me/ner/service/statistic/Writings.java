package wuxian.me.ner.service.statistic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxian on 10/8/2017.
 */
public class Writings extends MidStatistics {

    List<String> baseWordList;

    private boolean hasStopword;

    public Writings(boolean hasStopword) {
        super(1);
        this.hasStopword = hasStopword;
    }

    public Writings() {
        this(true);  //默认有stopword
    }

    public int size() {
        return baseWordList == null ? 0 : baseWordList.size();
    }

    public String indexOf(int pos) {
        if (baseWordList == null) {
            return null;
        }

        if (pos < 0 || pos >= baseWordList.size()) {
            return null;
        }

        return baseWordList.get(pos);
    }

    public void setBaseWordList(List<String> wordList) {
        this.baseWordList = wordList;
        setReady(true);
    }

    public List<String> getBaseWordList() {
        if (baseWordList == null) {
            baseWordList = new ArrayList<String>();
        }
        return baseWordList;
    }

    List<Sentence> sentenceList = new ArrayList<Sentence>();

    public boolean addSentence(Sentence sentence) {
        if (sentence != null && !sentenceList.contains(sentence)) {
            sentence.setWritings(this);
            sentenceList.add(sentence);

            return true;
        }
        return false;
    }

    public String getContents() {
        StringBuilder content = new StringBuilder("");
        for (int i = 0; i < sentenceList.size(); i++) {
            content.append(sentenceList.get(i).getContent());
        }
        return content.toString();
    }

    public int getPosionOf(Sentence sentence) {
        if (sentence == null) {
            return -1;
        }
        return sentenceList.indexOf(sentence);
    }

}