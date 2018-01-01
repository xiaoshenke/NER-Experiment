package wuxian.me.ner.statistic;

/**
 * Created by wuxian on 10/8/2017.
 */
public class Sentence {

    private String content;
    private Writings writings;

    public String getContent() {
        return content;
    }

    public Sentence(String content) {
        this.content = content;
    }

    public Sentence(String content, Writings writings) {

        this.content = content;
        this.writings = writings;
    }

    public void setWritings(Writings writings) {
        this.writings = writings;
    }

    public int getPostion() {
        if (writings == null) {
            return -1;
        }

        return writings.getPosionOf(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sentence)) return false;

        Sentence sentence = (Sentence) o;

        if (!content.equals(sentence.content)) return false;
        return writings != null ? writings.equals(sentence.writings) : sentence.writings == null;
    }

    @Override
    public int hashCode() {
        int result = content.hashCode();
        result = 31 * result + (writings != null ? writings.hashCode() : 0);
        return result;
    }
}