package com.bandwidth.sdk.model;

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
public class Account {

    private final BandwidthRestClient client;

    public Account(BandwidthRestClient client) {
        this.client = client;
    }

    public AccountInfo getAccountInfo() throws IOException {
        JSONObject jsonObject = client.getRestDriver().requestAccountInfo();
        return AccountInfo.from(jsonObject);
    }

    public TransactionsBuilder getTransactions() {
        return new TransactionsBuilder();
    }

    private List<AccountTransaction> getTransactions(Map<String, Object> params) throws IOException {
        JSONArray array = client.getRestDriver().requestAccountTransactions(params);

        List<AccountTransaction> transactions = new ArrayList<AccountTransaction>();
        for (Object obj : array) {
            transactions.add(AccountTransaction.from((JSONObject) obj));
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

        public List<AccountTransaction> get() throws IOException {
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
