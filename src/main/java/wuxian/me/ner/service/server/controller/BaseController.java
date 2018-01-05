package wuxian.me.ner.service.server.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wuxian on 1/1/2018.
 */
public abstract class BaseController {

    protected String relativeURL;

    public BaseController(String url) {
        this.relativeURL = url;
    }

    public abstract void doGet(final HttpServletRequest req
            , final HttpServletResponse resp) throws ServletException, IOException;


}
