package wuxian.me.ner.service.server;

import org.codehaus.jackson.map.ObjectMapper;
import wuxian.me.ner.service.server.controller.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by wuxian on 31/12/2017.
 */
public class NerServlet extends HttpServlet {

    private static final String BASE_URL = "/ner";
    private NerServer application;

    public NerServlet() {
        super();
    }

    @Override
    public void init(final ServletConfig config) throws ServletException {
        //System.out.println("enr servlet");
        this.application = (NerServer) config.getServletContext().getAttribute(
                Constants.NER_SERVLET_CONTEXT_KEY);
    }

    @Override
    public void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {

        String relativeURI = req.getRequestURI().substring(BASE_URL.length());

        if (relativeURI.startsWith("/upload")) {
            new UploadController(relativeURI).doGet(req, resp);

        } else if (relativeURI.startsWith("/series")) {
            new SeriesController(relativeURI).doGet(req, resp);

        } else if (relativeURI.startsWith("/freedom")) {
            new FreedomController(relativeURI).doGet(req, resp);

        } else if (relativeURI.startsWith("/consilience")) {
            new ConsilienceController(relativeURI).doGet(req, resp);

        } else if (relativeURI.startsWith("/exportWords")) {
            new ExportWordsController(relativeURI).doGet(req, resp);

        } else if (relativeURI.startsWith("/print")) {
            new PrintController(relativeURI).doGet(req, resp);

        } else if (relativeURI.startsWith("/remove")) {
            new RemoveController(relativeURI).doGet(req, resp);

        } else if (relativeURI.startsWith("/addword")) {
            new AddWordController(relativeURI).doGet(req, resp);
        }

        //if (hasParam(req, "hello")) {
        //    writeJSON(resp, "hello world");
        //}
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

