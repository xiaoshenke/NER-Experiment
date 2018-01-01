package wuxian.me.ner.server.service;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wuxian on 1/1/2018.
 */
public class SeriesManagerTest {

    @Test
    public void testAdd() throws Exception {

        Long articleId = ArticleManager.addAritle("test1_seg"
                , FileUtil.getCurrentPath() + "/article/test1_seg.txt");

        System.out.println(SeriesManager.addSeries(articleId, 2));

        Long exeId = SeriesManager.addSeries(articleId, 2);
        SeriesManager.Series series = SeriesManager.findSeriesByExeId(exeId);
        System.out.println(series.list.subList(0, 10));
    }

}