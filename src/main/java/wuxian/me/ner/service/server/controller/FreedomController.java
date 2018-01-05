package wuxian.me.ner.service.server.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wuxian on 1/1/2018.
 * 计算自由度并排序
 */
public class FreedomController extends BaseController {

    public FreedomController(String url) {
        super(url);
    }

    @Override
    public void doGet(HttpServletRequest req
            , HttpServletResponse resp) throws ServletException, IOException {

    }
}
