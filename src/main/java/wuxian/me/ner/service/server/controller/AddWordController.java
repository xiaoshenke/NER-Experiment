package wuxian.me.ner.service.server.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wuxian on 1/1/2018.
 * 将识别出的词汇加入词典接口
 */
public class AddWordController extends BaseController {

    public AddWordController(String url) {
        super(url);
    }

    @Override
    public void doGet(HttpServletRequest req
            , HttpServletResponse resp) throws ServletException, IOException {

    }
}
