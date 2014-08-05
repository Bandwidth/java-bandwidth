package com.bandwidth.sdk.driver;

import org.json.simple.JSONObject;

import java.io.IOException;

/**
 * @author vpotapenko
 */
public interface IRestDriver {

    JSONObject requestAccountInfo() throws IOException;
}
