package wuxian.me.ner.server.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wuxian on 1/1/2018.
 * 计算凝聚度并排序
 */
public class ConsilienceController extends BaseController {

    public ConsilienceController(String url) {
        super(url);
    }

    @Override
    public void doGet(HttpServletRequest req
            , HttpServletResponse resp) throws ServletException, IOException {

    }
}
