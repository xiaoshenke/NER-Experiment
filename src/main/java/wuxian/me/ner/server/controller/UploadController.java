package wuxian.me.ner.server.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wuxian on 1/1/2018.
 * 上传且分词接口
 */
public class UploadController extends BaseController {

    public UploadController(String url) {
        super(url);
    }

    @Override
    public void doGet(HttpServletRequest req
            , HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("UploadController.doGet");

    }
}
