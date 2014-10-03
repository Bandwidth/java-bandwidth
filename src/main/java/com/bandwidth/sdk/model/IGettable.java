package com.bandwidth.sdk.model;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by sbarstow on 10/3/14.
 */
public interface IGettable {
    public IGettable get(String id) throws Exception;
    public List<?> list(Map<String, Object> query) throws Exception;
    public String create(Map<String, Object> params) throws IOException;
}
