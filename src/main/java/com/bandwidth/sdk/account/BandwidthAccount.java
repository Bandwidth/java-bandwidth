package com.bandwidth.sdk.account;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthRestClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author vpotapenko
 */
public class BandwidthAccount {

    private final BandwidthRestClient client;

    public BandwidthAccount(BandwidthRestClient client) {
        this.client = client;
    }

    public BandwidthAccountInfo getAccountInfo() throws IOException {
        JSONObject jsonObject = client.getRestDriver().requestAccountInfo();
        return BandwidthAccountInfo.from(jsonObject);
    }

    public TransactionsBuilder getTransactions() {
        return new TransactionsBuilder();
    }

    private List<BandwidthAccountTransaction> getTransactions(Map<String, Object> params) throws IOException {
        JSONArray array = client.getRestDriver().requestAccountTransactions(params);

        List<BandwidthAccountTransaction> transactions = new ArrayList<BandwidthAccountTransaction>();
        for (Object obj : array) {
            transactions.add(BandwidthAccountTransaction.from((JSONObject) obj));
        }
        return transactions;
    }

    public class TransactionsBuilder {

        private Integer maxItems;
        private Date fromDate;
        private Date toDate;
        private String type;
        private Integer page;
        private Integer size;

        public TransactionsBuilder maxItems(int maxItems) {
            this.maxItems = maxItems;
            return this;
        }

        public TransactionsBuilder fromDate(Date fromDate) {
            this.fromDate = fromDate;
            return this;
        }

        public TransactionsBuilder toDate(Date toDate) {
            this.toDate = toDate;
            return this;
        }

        public TransactionsBuilder type(String type) {
            this.type = type;
            return this;
        }

        public TransactionsBuilder page(int page) {
            this.page = page;
            return this;
        }

        public TransactionsBuilder size(int size) {
            this.size = size;
            return this;
        }

        public List<BandwidthAccountTransaction> get() throws IOException {
            Map<String, Object> params = new HashMap<String, Object>();

            SimpleDateFormat simpleDateFormat = null;
            if (fromDate != null) {
                simpleDateFormat = new SimpleDateFormat(BandwidthConstants.TRANSACTION_DATE_TIME_PATTERN);
                params.put("fromDate", simpleDateFormat.format(fromDate));
            }

            if (toDate != null) {
                if (simpleDateFormat == null)
                    simpleDateFormat = new SimpleDateFormat(BandwidthConstants.TRANSACTION_DATE_TIME_PATTERN);
                params.put("toDate", simpleDateFormat.format(toDate));
            }

            if (maxItems != null) params.put("maxItems", String.valueOf(maxItems));
            if (type != null) params.put("type", type);
            if (page != null) params.put("page", String.valueOf(page));
            if (size != null) params.put("size", String.valueOf(size));

            return getTransactions(params);
        }
    }
}
