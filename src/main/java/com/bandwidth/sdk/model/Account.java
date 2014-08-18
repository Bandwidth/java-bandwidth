package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.driver.IRestDriver;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author vpotapenko
 */
public class Account extends BaseModelObject {

    public Account(IRestDriver driver, String parentUri) {
        super(driver, parentUri, null);
    }

    public AccountInfo getAccountInfo() throws IOException {
        String uri = getUri();
        JSONObject jsonObject = driver.getObject(uri);
        return new AccountInfo(driver, uri, jsonObject);
    }

    @Override
    public String getUri() {
        return StringUtils.join(new String[]{
                parentUri,
                "account"
        }, '/');
    }

    public TransactionsQueryBuilder queryTransactionsBuilder() {
        return new TransactionsQueryBuilder();
    }

    private List<AccountTransaction> getTransactions(Map<String, Object> params) throws IOException {
        String transactionsUri = getAccountTransactionsUri();
        JSONArray array = driver.getArray(transactionsUri, params);

        List<AccountTransaction> transactions = new ArrayList<AccountTransaction>();
        for (Object obj : array) {
            transactions.add(new AccountTransaction(driver, transactionsUri, (JSONObject) obj));
        }
        return transactions;
    }

    public String getAccountTransactionsUri() {
        return StringUtils.join(new String[]{
                getUri(),
                "transactions"
        }, '/');
    }

    public class TransactionsQueryBuilder {

        private Integer maxItems;
        private Date fromDate;
        private Date toDate;
        private String type;
        private Integer page;
        private Integer size;

        public TransactionsQueryBuilder maxItems(int maxItems) {
            this.maxItems = maxItems;
            return this;
        }

        public TransactionsQueryBuilder fromDate(Date fromDate) {
            this.fromDate = fromDate;
            return this;
        }

        public TransactionsQueryBuilder toDate(Date toDate) {
            this.toDate = toDate;
            return this;
        }

        public TransactionsQueryBuilder type(String type) {
            this.type = type;
            return this;
        }

        public TransactionsQueryBuilder page(int page) {
            this.page = page;
            return this;
        }

        public TransactionsQueryBuilder size(int size) {
            this.size = size;
            return this;
        }

        public List<AccountTransaction> list() throws IOException {
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
