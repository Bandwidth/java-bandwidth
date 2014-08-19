package com.bandwidth.sdk.driver;

import com.bandwidth.sdk.BandwidthConstants;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author vpotapenko
 */
public class HttpRestDriver implements IRestDriver {

    private final String token;
    private final String secret;

    private HttpClient httpClient;

    public HttpRestDriver(String token, String secret) {
        this.token = token;
        this.secret = secret;

        httpClient = new DefaultHttpClient();
    }

    /**
     * For testing
     */
    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public JSONArray getArray(String uri, Map<String, Object> params) throws IOException {
        String path = getPath(uri);
        RestResponse response = request(path, HttpMethod.GET, params);
        if (response.isError()) throw new IOException(response.getResponseText());

        if (response.isJson()) {
            try {
                return (JSONArray) new JSONParser().parse(response.getResponseText());
            } catch (org.json.simple.parser.ParseException e) {
                throw new IOException(e);
            }
        } else {
            throw new IOException("Response is not a JSON format.");
        }
    }

    @Override
    public JSONObject getObject(String uri) throws IOException {
        String path = getPath(uri);
        RestResponse response = request(path, HttpMethod.GET);
        if (response.isError()) throw new IOException(response.getResponseText());

        if (response.isJson()) {
            try {
                return (JSONObject) new JSONParser().parse(response.getResponseText());
            } catch (org.json.simple.parser.ParseException e) {
                throw new IOException(e);
            }
        } else {
            throw new IOException("Response is not a JSON format.");
        }
    }

    @Override
    public JSONObject create(String uri, Map<String, Object> params) throws IOException {
        String path = getPath(uri);
        RestResponse response = request(path, HttpMethod.POST, params);
        if (response.isError()) throw new IOException(response.getResponseText());

        String location = response.getLocation();
        if (location != null) {
            response = request(location, HttpMethod.GET);
            if (response.isError()) throw new IOException(response.getResponseText());

            if (response.isJson()) {
                try {
                    return (JSONObject) new JSONParser().parse(response.getResponseText());
                } catch (org.json.simple.parser.ParseException e) {
                    throw new IOException(e);
                }
            } else {
                throw new IOException("Response is not a JSON format.");
            }
        } else {
            throw new IOException("There is no location of new application.");
        }
    }

    @Override
    public void post(String uri, Map<String, Object> params) throws IOException {
        String path = getPath(uri);
        RestResponse response = request(path, HttpMethod.POST, params);
        if (response.isError()) throw new IOException(response.getResponseText());
    }

    @Override
    public void delete(String uri) throws IOException {
        String path = getPath(uri);
        RestResponse response = request(path, HttpMethod.DELETE);
        if (response.isError()) throw new IOException(response.getResponseText());
    }

    @Override
    public void uploadFile(String uri, String filePath, String contentType) throws IOException {
        String path = getPath(uri);

        HttpPut request = (HttpPut) setupRequest(path, HttpMethod.PUT, null);
        File file = new File(filePath);
        request.setEntity(contentType == null ? new FileEntity(file) : new FileEntity(file, ContentType.parse(contentType)));

        performRequest(request);
    }

    @Override
    public void downloadFileTo(String uri, String filePath) throws IOException {
        String path = getPath(uri);

        HttpGet request = (HttpGet) setupRequest(path, HttpMethod.GET, Collections.<String, Object>emptyMap());
        HttpResponse response;

        OutputStream outputStream = null;
        try {
            response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            StatusLine status = response.getStatusLine();
            int statusCode = status.getStatusCode();
            if (statusCode >= 400) throw new IOException(EntityUtils.toString(entity));

            outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
            entity.writeTo(outputStream);
        } catch (final ClientProtocolException e1) {
            throw new IOException(e1);
        } catch (final IOException e1) {
            throw new IOException(e1);
        } finally {
            try {
                if (outputStream != null) outputStream.close();
            } catch (IOException ignore) {
            }
        }
    }

    private String getPath(String uri) {
        String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                uri,
        };
        return StringUtils.join(parts, '/');
    }


    private RestResponse request(final String path, HttpMethod method) throws IOException {
        return request(path, method, null);
    }

    private RestResponse request(final String path, HttpMethod method,
                                 Map<String, Object> paramList) throws IOException {
        if (paramList == null) paramList = Collections.emptyMap();

        HttpUriRequest request = setupRequest(path, method, paramList);
        return performRequest(request);
    }

    private RestResponse performRequest(HttpUriRequest request) throws IOException {
        HttpResponse response;
        try {
            response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            String responseBody = "";

            if (entity != null) {
                responseBody = EntityUtils.toString(entity);
            }

            StatusLine status = response.getStatusLine();
            int statusCode = status.getStatusCode();

            RestResponse restResponse = new RestResponse(request.getURI().toString(), responseBody, statusCode);

            Header[] headers = response.getHeaders("Content-Type");
            if (headers.length > 0) {
                restResponse.setContentType(headers[0].getValue());
            }

            headers = response.getHeaders("Location");
            if (headers.length > 0) {
                restResponse.setLocation(headers[0].getValue());
            }

            return restResponse;

        } catch (final ClientProtocolException e1) {
            throw new IOException(e1);
        } catch (final IOException e1) {
            throw new IOException(e1);
        }
    }

    public HttpUriRequest setupRequest(String path, HttpMethod method, final Map<String, Object> params) {
        HttpUriRequest request = buildMethod(method, path, params);

        request.addHeader(new BasicHeader("Accept", "application/json"));
        request.addHeader(new BasicHeader("Accept-Charset", "utf-8"));
        String s = token + ":" + secret;
        String auth = new String(Base64.encodeBase64(s.getBytes()));
        request.setHeader(new BasicHeader("Authorization", "Basic " + auth));

        return request;
    }

    private HttpUriRequest buildMethod(HttpMethod method, final String path, final Map<String, Object> params) {
        switch (method) {
            case GET:
                return generateGetRequest(path, params);
            case POST:
                return generatePostRequest(path, params);
            case PUT:
                return generatePutRequest(path, params);
            case DELETE:
                return generateDeleteRequest(path);
            default:
                throw new RuntimeException("Must not be here.");
        }
    }

    private HttpGet generateGetRequest(final String path, final Map<String, Object> paramMap) {
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (String key : paramMap.keySet()) {
            pairs.add(new BasicNameValuePair(key, paramMap.get(key).toString()));
        }
        URI uri = buildUri(path, pairs);
        return new HttpGet(uri);
    }

    private HttpPost generatePostRequest(final String path, final Map<String, Object> paramMap) {
        URI uri = buildUri(path);

        String s = JSONObject.toJSONString(paramMap);

        HttpPost post = new HttpPost(uri);
        post.setEntity(new StringEntity(s, ContentType.APPLICATION_JSON));

        return post;
    }

    private HttpPut generatePutRequest(final String path, final Map<String, Object> paramMap) {
        URI uri = buildUri(path);

        HttpPut put = new HttpPut(uri);
        if (paramMap != null) {
            String s = JSONObject.toJSONString(paramMap);
            put.setEntity(new StringEntity(s, ContentType.APPLICATION_JSON));
        }

        return put;
    }

    private HttpDelete generateDeleteRequest(final String path) {
        URI uri = buildUri(path);
        return new HttpDelete(uri);
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
