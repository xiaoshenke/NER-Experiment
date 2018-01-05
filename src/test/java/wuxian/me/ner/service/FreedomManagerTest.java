package wuxian.me.ner.service;

import org.junit.Test;

/**
 * Created by wuxian on 1/1/2018.
 */
public class FreedomManagerTest {

    @Test
    public void testAdd() throws Exception {

        Long articleId = ArticleManager.addAritle("test1_seg"
                , FileUtil.getCurrentPath() + "/article/test1_seg.txt");

        Long exeId = FreedomManager.addFreedom(articleId, 2, 0);
        FreedomManager.Freedom series = FreedomManager.findFreedomByExeId(exeId);//.findSeriesByExeId(exeId);
        System.out.println(series.list.subList(0, 10));
    }

}