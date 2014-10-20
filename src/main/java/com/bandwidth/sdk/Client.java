package com.bandwidth.sdk;

import java.util.Map;

public interface Client {
    public RestResponse get(String uri, Map<String, Object> params) throws Exception;

}
