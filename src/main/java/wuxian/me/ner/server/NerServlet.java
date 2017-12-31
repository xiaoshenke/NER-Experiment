package wuxian.me.ner.server;

import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by wuxian on 31/12/2017.
 * <p>
 * 1 上传切分好词的接口 article:String,content:List[String]
 * 2 词频统计接口
 * 3 根据(以n为长度)连词词频识别未登陆词接口
 * 4 根据freedom识别未登陆词接口
 * 5 根据凝聚度识别未登陆词接口
 * 6 动态将"已识别词"加入词典接口
 * 7 打印"已识别词"接口
 */
public class NerServlet extends HttpServlet {

    private NerServer application;

    public NerServlet() {
        super();
    }

    @Override
    public void init(final ServletConfig config) throws ServletException {

        System.out.println("executor servlet");
        this.application =
                (NerServer) config.getServletContext().getAttribute(
                        Constants.NER_SERVLET_CONTEXT_KEY);
    }

    @Override
    public void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {

        System.out.println("doGet");
        if (hasParam(req, "hello")) {
            writeJSON(resp, "hello world");
        }
    }

    public boolean hasParam(final HttpServletRequest request, final String param) {
        return request.getParameter(param) != null;
    }

    public static final String JSON_MIME_TYPE = "application/json";

    protected void writeJSON(final HttpServletResponse resp, final Object obj)
            throws IOException {
        resp.setContentType(JSON_MIME_TYPE);
        final ObjectMapper mapper = new ObjectMapper();
        final OutputStream stream = resp.getOutputStream();
        mapper.writeValue(stream, obj);
    }
}

