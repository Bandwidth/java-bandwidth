package com.bandwidth.sdk.applications;

import com.bandwidth.sdk.BandwidthRestClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vpotapenko
 */
public class BandwidthApplications {

    private final BandwidthRestClient client;

    public BandwidthApplications(BandwidthRestClient client) {
        this.client = client;
    }

    public ApplicationsBuilder getApplications() {
        return new ApplicationsBuilder(this);
    }

    private List<BandwidthApplication> getApplications(Map<String, String> params) throws IOException {
        JSONArray array = client.getRestDriver().requestApplications(params);

        List<BandwidthApplication> applications = new ArrayList<BandwidthApplication>();
        for (Object obj : array) {
            applications.add(BandwidthApplication.from(client, (JSONObject) obj));
        }
        return applications;
    }

    public static class ApplicationsBuilder {

        private final BandwidthApplications applications;

        private Integer page;
        private Integer size;

        public ApplicationsBuilder(BandwidthApplications applications) {
            this.applications = applications;
        }

        public ApplicationsBuilder page(Integer page) {
            this.page = page;
            return this;
        }

        public ApplicationsBuilder size(Integer size) {
            this.size = size;
            return this;
        }

        public List<BandwidthApplication> get() throws IOException {
            Map<String, String> params = new HashMap<String, String>();

            if (page != null) params.put("page", String.valueOf(page));
            if (size != null) params.put("size", String.valueOf(size));

            return applications.getApplications(params);
        }
    }
}
