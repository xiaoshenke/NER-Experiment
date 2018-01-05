package wuxian.me.ner.service;

import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.tuple.*;

/**
 * Created by wuxian on 1/1/2018.
 */
public class SeriesManager {

    private SeriesManager() {
    }

    static List<Series> seriesList = new ArrayList<>();

    public static Long addSeries(Long articleId, Integer len) throws Exception {
        return addSeries(articleId, len, false);
    }

    public static Long addSeries(Long articleId, Integer len, boolean force) throws Exception {
        if (!force) {
            Series series = findSeriesByArticleId(articleId, len);
            if (series != null) {
                return series.articleId;
            }
        }

        Series series = new Series();
        series.seriesLength = len;
        series.articleId = articleId;
        series.executeId = genId("");

        List<String> wordList = ArticleManager.getWordsBy(series.articleId);
        if (wordList == null) {
            throw new Exception("no cutted word list of articleId: " + articleId + " is found!");
        }
        series.list = new SeriesCalculator().calculate(wordList, len);

        seriesList.add(series);
        return series.executeId;

    }

    private static AtomicLong id = new AtomicLong(0);

    private static Long genId(String title) {
        return id.getAndIncrement();
    }


    public static void removeByExeId(Long exeId) {
        Iterator<Series> iterator = seriesList.iterator();
        while (iterator.hasNext()) {
            Series series = iterator.next();
            if (series.articleId.equals(exeId)) {
                iterator.remove();
                return;
            }
        }
    }

    public static void removeByArticleId(Long articleId) {
        Iterator<Series> iterator = seriesList.iterator();
        while (iterator.hasNext()) {
            Series series = iterator.next();
            if (series.articleId.equals(articleId)) {
                iterator.remove();
            }
        }
    }

    @Nullable
    public static Series findSeriesByArticleId(Long articleId, Integer length) {
        for (Series series : seriesList) {
            if (series.articleId.equals(articleId) && series.seriesLength.equals(length)) {
                return series;
            }
        }
        return null;
    }

    @Nullable
    public static Series findSeriesByExeId(Long exeId) {
        for (Series serie : seriesList) {
            if (serie.executeId.equals(exeId)) {
                return serie;
            }
        }
        return null;
    }

    public static class Series {
        Long executeId;
        Long articleId;
        Integer seriesLength;  //连续多少个词进行计算
        List<Pair<String, Integer>> list;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Series)) return false;

            Series series = (Series) o;

            if (executeId != null ? !executeId.equals(series.executeId) : series.executeId != null) return false;
            if (articleId != null ? !articleId.equals(series.articleId) : series.articleId != null) return false;
            return seriesLength != null ? seriesLength.equals(series.seriesLength) : series.seriesLength == null;
        }

        @Override
        public int hashCode() {
            int result = executeId != null ? executeId.hashCode() : 0;
            result = 31 * result + (articleId != null ? articleId.hashCode() : 0);
            result = 31 * result + (seriesLength != null ? seriesLength.hashCode() : 0);
            return result;
        }
    }
}
