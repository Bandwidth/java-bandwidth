package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.IRestDriver;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
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

        private Map<String, Object> params = new HashMap<String, Object>();

        public TransactionsQueryBuilder maxItems(int maxItems) {
            params.put("maxItems", maxItems);
            return this;
        }

        public TransactionsQueryBuilder fromDate(Date fromDate) {
            params.put("fromDate", dateFormat.format(fromDate));
            return this;
        }

        public TransactionsQueryBuilder toDate(Date toDate) {
            params.put("toDate", dateFormat.format(toDate));
            return this;
        }

        public TransactionsQueryBuilder type(String type) {
            params.put("type", type);
            return this;
        }

        public TransactionsQueryBuilder page(int page) {
            params.put("page", page);
            return this;
        }

        public TransactionsQueryBuilder size(int size) {
            params.put("size", size);
            return this;
        }

        public List<AccountTransaction> list() throws IOException {
            return getTransactions(params);
        }
    }
}
