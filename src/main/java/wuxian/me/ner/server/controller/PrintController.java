package wuxian.me.ner.server.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wuxian on 1/1/2018.
 * 打印接口 打印计算好的结果
 */
public class PrintController extends BaseController {


    public PrintController(String url) {
        super(url);
    }

    @Override
    public void doGet(HttpServletRequest req
            , HttpServletResponse resp) throws ServletException, IOException {

    }
}
