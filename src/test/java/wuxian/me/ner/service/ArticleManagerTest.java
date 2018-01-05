package wuxian.me.ner.service;

import org.junit.Test;

/**
 * Created by wuxian on 1/1/2018.
 */
public class ArticleManagerTest {

    @Test
    public void testAddWord() throws Exception {

        System.out.println(ArticleManager.addAritle("test1_seg", FileUtil.getCurrentPath() + "/article/test1_seg.txt"));

    }


}