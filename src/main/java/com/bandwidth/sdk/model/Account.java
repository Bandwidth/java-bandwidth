package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthRestClient;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * Point for <code>/v1/users/{userId}/account</code>
 *
 * @author vpotapenko
 */
public class Account extends BaseModelObject {

    public Account(BandwidthRestClient client){
        super(client, null);
    }

    /**
     * Gets your current account information.
     *
     * @return information account information
     * @throws IOException
     */
    public AccountInfo getAccountInfo() throws IOException {
        JSONObject jsonObject = client.getObject(getUri());
        return new AccountInfo(client, jsonObject);
    }

    /**
     * Creates builder for getting transactions of the account.
     * <br>Example:<br>
     * <code>List<AccountTransaction> list = account.queryTransactionsBuilder().maxItems(5).type("charge").list();</code>
     *
     * @return builder for getting transactions
     */
    public TransactionsQueryBuilder queryTransactionsBuilder() {
        return new TransactionsQueryBuilder();
    }

    private List<AccountTransaction> getTransactions(Map<String, Object> params) throws IOException {
        String transactionsUri = getAccountTransactionsUri();
        JSONArray array = client.getArray(transactionsUri, params);

        List<AccountTransaction> transactions = new ArrayList<AccountTransaction>();
        for (Object obj : array) {
            transactions.add(new AccountTransaction(client, (JSONObject) obj));
        }
        return transactions;
    }

    private String getAccountTransactionsUri() {
        return StringUtils.join(new String[]{
                getUri(),
                "transactions"
        }, '/');
    }

    @Override
    protected String getUri() {
        return client.getUserResourceUri(BandwidthConstants.ACCOUNT_URI_PATH);
//        return StringUtils.join(new String[]{
//                parentUri,
//                "account"
//        }, '/');
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
