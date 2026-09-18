package com.mbp.eng.framework.common.util.http;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClient {
    private static Logger logger = LoggerFactory.getLogger(HttpClient.class);

    private static Gson gson = new Gson();

    public static String requireHttpPost(URI uri, List<NameValuePair> postPairs) {
        try {
            final HttpPost post = new HttpPost(uri);
            post.setEntity(new UrlEncodedFormEntity(postPairs, StandardCharsets.UTF_8));
            final String json = HttpManager.post(post);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String requireHttpGet(URI uri) {
        try {
            final HttpGet get = new HttpGet(uri);
            final String json = HttpManager.get(get);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String doHttpGet(String url, List<NameValuePair> paramPairs) {
        try {
            final URI uri = new URIBuilder(url).addParameters(paramPairs)
                    .build();
            final HttpGet get = new HttpGet(uri);
            final String json = HttpManager.get(get);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String doHttpGet(String url, List<NameValuePair> paramPairs, List<NameValuePair> headers) {
        try {
            final URI uri = new URIBuilder(url).addParameters(paramPairs)
                    .build();
            final HttpGet get = new HttpGet(uri);
            if (headers != null) {
                for (NameValuePair header : headers) {
                    get.addHeader(header.getName(), header.getValue());
                }
            }
            final String json = HttpManager.get(get);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String doHttpPost(String url, List<NameValuePair> paramPairs) {
        try {
            final HttpPost post = new HttpPost(url);
            post.setEntity(new UrlEncodedFormEntity(paramPairs, StandardCharsets.UTF_8));
            final String json = HttpManager.post(post);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String doHttpPut(String url, List<NameValuePair> paramPairs) {
        try {
            final URI uri = new URIBuilder(url).addParameters(paramPairs)
                    .build();
            final HttpPut putc = new HttpPut(uri);
            final String json = HttpManager.put(putc);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String doHttpDelete(String url, List<NameValuePair> paramPairs) {
        try {
            final URI uri = new URIBuilder(url).addParameters(paramPairs)
                    .build();
            final HttpDelete deletec = new HttpDelete(uri);
            final String json = HttpManager.delete(deletec);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static HttpResponse doHttpGetWithResponse(String url, List<NameValuePair> paramPairs) {
        try {
            final URI uri = new URIBuilder(url).addParameters(paramPairs)
                    .build();
            final HttpGet get = new HttpGet(uri);
            final HttpResponse response = HttpManager.doGet(get);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static HttpResponse doHttpPostWithResponse(String url, List<NameValuePair> paramPairs) {
        try {
            final HttpPost post = new HttpPost(url);
            post.setEntity(new UrlEncodedFormEntity(paramPairs, StandardCharsets.UTF_8)); //解决中文乱码
            final HttpResponse response = HttpManager.doPost(post);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static HttpResponse doHttpPutWithResponse(String url, List<NameValuePair> paramPairs) {
        try {
            final URI uri = new URIBuilder(url).addParameters(paramPairs)
                    .build();
            final HttpPut putc = new HttpPut(uri);
            final HttpResponse response = HttpManager.doPut(putc);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static HttpResponse doHttpDeleteWithResponse(String url, List<NameValuePair> paramPairs) {
        try {
            final URI uri = new URIBuilder(url).addParameters(paramPairs)
                    .build();
            final HttpDelete deletec = new HttpDelete(uri);
            final HttpResponse response = HttpManager.doDelete(deletec);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String doHttpRequest(String url, List<NameValuePair> paramPairs, RequestMethod requestMethod) throws Exception {
        HttpResponse response;
        switch (requestMethod) {
            case POST:
                response = HttpClient.doHttpPostWithResponse(url, paramPairs);
                break;
            case PUT:
                response = HttpClient.doHttpPutWithResponse(url, paramPairs);
                break;
            case DELETE:
                response = HttpClient.doHttpDeleteWithResponse(url, paramPairs);
                break;
            default:
                response = HttpClient.doHttpGetWithResponse(url, paramPairs);
        }

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        } else {
            throw new Exception(response.getStatusLine().toString());
        }
    }

    public static String doHttpRawPost(String url, Map<String, ?> paramMaps) {
        return doHttpRawPost(url, gson.toJson(paramMaps), null);
    }

    public static String doHttpRawPost(String url, Map<String, ?> paramMaps, Map<String, String> headerMaps) {
        return doHttpRawPost(url, gson.toJson(paramMaps), headerMaps);
    }

    public static String doHttpRawPost(String url, String paramMapsJsonStr, Map<String, String> headerMaps) {
        try {
            final HttpPost post = new HttpPost(url);
            if (headerMaps != null && headerMaps.size() > 0) {
                for (String key : headerMaps.keySet()) {
                    post.addHeader(key, headerMaps.get(key));
                }
            }
            post.setEntity(new StringEntity(paramMapsJsonStr, StandardCharsets.UTF_8)); //解决中文乱码
            final String json = HttpManager.post(post);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static HttpResponse doHttpRawPostWithResponse(String url, Map<String, ?> paramMaps) {
        return doHttpRawPostWithResponse(url, gson.toJson(paramMaps), null);
    }

    public static HttpResponse doHttpRawPostWithResponse(String url, Map<String, ?> paramMaps, Map<String, String> headerMaps) {
        return doHttpRawPostWithResponse(url, gson.toJson(paramMaps), headerMaps);
    }

    public static HttpResponse doHttpRawPostWithResponse(String url, String paramMapJsonStr, Map<String, String> headerMaps) {
        try {
            final HttpPost post = new HttpPost(url);
            if (headerMaps != null && headerMaps.size() > 0) {
                for (String key : headerMaps.keySet()) {
                    post.addHeader(key, headerMaps.get(key));
                }
            }
            post.setEntity(new StringEntity(paramMapJsonStr, StandardCharsets.UTF_8)); //解决中文乱码
            final HttpResponse response = HttpManager.doPost(post);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static Map<String, String> getHeaderMapsForSchedulerWeb() {
        Map<String, String> paramParis = new HashMap<>();
        paramParis.put("Content-type", "application/json; charset=utf-8");
        paramParis.put("Accept", "application/json");
        return paramParis;
    }

    public static Map<String, String> paramPairsToPramsMap(List<NameValuePair> paramPairs) {
        Map<String, String> paramMap = new HashMap<>();
        for (NameValuePair one : paramPairs) {
            paramMap.put(one.getName(), one.getValue());
        }
        return paramMap;
    }

    public static void main(String[] args) {
        System.out.println(doHttpRawPost("http://idata-sch-web-inner.jcloud.com/scheduler-web/task/create",
                "{\"taskName\":\"test005\",\"taskLabel\":\"dts\",\"userName\":\"datajingdo_m\",\"taskId\":\"test005\"}", getHeaderMapsForSchedulerWeb()));
    }
}