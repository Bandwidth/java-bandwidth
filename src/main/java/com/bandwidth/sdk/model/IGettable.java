package com.bandwidth.sdk.model;

import java.util.List;
import java.util.Map;

/**
 * Created by sbarstow on 10/3/14.
 */
public interface IGettable {
    public IGettable get(String id) throws Exception;
    public List<?> list();
    public String create(Map<String, Object> params);
}
