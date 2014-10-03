package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthRestClient;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author vpotapenko
 */
public abstract class BaseModelObject extends AbsModelObject {


    protected final BandwidthRestClient client;
    //protected final String parentUri;


    protected BaseModelObject(BandwidthRestClient client, JSONObject jsonObject){
        this.client = client;
        updateProperties(jsonObject);
    }

    protected BaseModelObject(BandwidthRestClient client, String parentUri, JSONObject jsonObject) {
        this.client = client;
        //this.parentUri = parentUri;

        updateProperties(jsonObject);
    }


    protected BandwidthRestClient getClient() {
    	return client;
    }

    protected abstract String getUri();
//        // default implementation of uri
//        return StringUtils.join(new String[]{
//                parentUri,
//                getId()
//        }, '/');

}
