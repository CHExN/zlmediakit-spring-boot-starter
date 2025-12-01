package io.github.lunasaw.zlm.net.method;

import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;

import java.net.URI;

/**
 * @author luna
 */
public class HttpDelete extends HttpUriRequestBase {

    public final static String METHOD_NAME = "DELETE";

    public HttpDelete(String uri) {
        super(METHOD_NAME, URI.create(uri));
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }
}
