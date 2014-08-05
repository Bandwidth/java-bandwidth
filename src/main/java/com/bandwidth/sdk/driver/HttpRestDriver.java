package com.bandwidth.sdk.driver;

import org.apache.commons.lang.StringUtils;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

/**
 * @author vpotapenko
 */
public class HttpRestDriver implements IRestDriver {

    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int READ_TIMEOUT = 30500;

    private final String userId;
    private final String token;
    private final String secret;

    private final DefaultHttpClient httpclient;

    public HttpRestDriver(String userId, String token, String secret) {
        this.userId = userId;
        this.token = token;
        this.secret = secret;

        httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
        httpclient.getParams().setParameter("http.socket.timeout", new Integer(READ_TIMEOUT));
        httpclient.getParams().setParameter("http.connection.timeout", new Integer(CONNECTION_TIMEOUT));
        httpclient.getParams().setParameter("http.protocol.content-charset", "UTF-8");
        httpclient
                .getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), new UsernamePasswordCredentials(token, secret));
    }

    @Override
    public JSONObject requestAccountInfo() throws IOException {
        String path = getAccountPath();
        BandwidthRestResponse response = request(path, HttpMethod.GET, Collections.<NameValuePair>emptyList());

        return new JSONObject();
    }

    private String getAccountPath() {
        String[] parts = new String[]{
                RestConstants.API_ENDPOINT,
                RestConstants.API_VERSION,
                String.format(RestConstants.ACCOUNT_PATTERN, userId)
        };
        return StringUtils.join(parts, '/');
    }

    private BandwidthRestResponse request(final String path, HttpMethod method,
                                          final List<NameValuePair> paramList) throws IOException {

        HttpUriRequest request = setupRequest(path, method, paramList);

        HttpResponse response;
        try {
            response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            Header[] contentTypeHeaders = response.getHeaders("Content-Type");
            String responseBody = "";

            if (entity != null) {
                responseBody = EntityUtils.toString(entity);
            }

            StatusLine status = response.getStatusLine();
            int statusCode = status.getStatusCode();

            BandwidthRestResponse restResponse = new BandwidthRestResponse(request.getURI().toString(), responseBody, statusCode);

            // For now we only set the first content type seen
            for (Header h : contentTypeHeaders) {
                restResponse.setContentType(h.getValue());
                break;
            }

            return restResponse;

        } catch (final ClientProtocolException e1) {
            throw new RuntimeException(e1);
        } catch (final IOException e1) {
            throw new RuntimeException(e1);
        }
    }

    private HttpUriRequest setupRequest(String path, HttpMethod method, final List<NameValuePair> params) {
        HttpUriRequest request = buildMethod(method, path, params);

        request.addHeader(new BasicHeader("Accept", "application/json"));
        request.addHeader(new BasicHeader("Accept-Charset", "utf-8"));

        return request;
    }

    private HttpUriRequest buildMethod(HttpMethod method, final String path, final List<NameValuePair> params) {
        switch (method) {
            case GET:
                return generateGetRequest(path, params);
            case POST:
                return generatePostRequest(path, params);
            case PUT:
                return generatePutRequest(path, params);
            case DELETE:
                return generateDeleteRequest(path, params);
            default:
                throw new RuntimeException("Must not be here.");
        }
    }

    private HttpGet generateGetRequest(final String path, final List<NameValuePair> params) {
        URI uri = buildUri(path, params);
        return new HttpGet(uri);
    }

    private HttpPost generatePostRequest(final String path, final List<NameValuePair> params) {
        URI uri = buildUri(path);

        UrlEncodedFormEntity entity = buildEntityBody(params);

        HttpPost post = new HttpPost(uri);
        post.setEntity(entity);

        return post;
    }

    private HttpPut generatePutRequest(final String path, final List<NameValuePair> params) {
        URI uri = buildUri(path);

        UrlEncodedFormEntity entity = buildEntityBody(params);

        HttpPut put = new HttpPut(uri);
        put.setEntity(entity);

        return put;
    }

    private HttpDelete generateDeleteRequest(final String path, final List<NameValuePair> params) {
        URI uri = buildUri(path);
        return new HttpDelete(uri);
    }

    private UrlEncodedFormEntity buildEntityBody(final List<NameValuePair> params) {
        UrlEncodedFormEntity entity;
        try {
            entity = new UrlEncodedFormEntity(params, "UTF-8");
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return entity;
    }

    private URI buildUri(final String path) {
        return buildUri(path, null);
    }

    private URI buildUri(final String path, final List<NameValuePair> queryStringParams) {
        StringBuilder sb = new StringBuilder();
        sb.append(path);

        if (queryStringParams != null && queryStringParams.size() > 0) {
            sb.append("?");
            sb.append(URLEncodedUtils.format(queryStringParams, "UTF-8"));
        }

        URI uri;
        try {
            uri = new URI(sb.toString());
        } catch (final URISyntaxException e) {
            throw new IllegalStateException("Invalid uri", e);
        }

        return uri;
    }
}
