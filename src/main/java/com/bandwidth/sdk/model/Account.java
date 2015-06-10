package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthClient;
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
public class Account extends ResourceBase {
	
	/**
	 * Account factory method. Returns Account object
	 * 
	 * @return the account
	 */
	
	public static Account get() {
		final BandwidthClient client = BandwidthClient.getInstance();
		
		return new Account(client);
	}

    public Account(final BandwidthClient client){
        super(client, null);
    }

    @Override
    protected void setUp(final JSONObject jsonObject) {
    }


    /**
     * Gets your current account information.
     *
     * @return information account information
     * @throws IOException unexpected error.
     */
    public AccountInfo getAccountInfo() throws Exception {

        final JSONObject jsonObject = toJSONObject(client.get(getUri(), null));


        return new AccountInfo(client, jsonObject);
    }

    /**
     * Creates builder for getting transactions of the account.
     * <br>Example:<br>
     * <code>List{@literal <AccountTransaction>} list = account.queryTransactionsBuilder().maxItems(5).type("charge").list();</code>
     *
     * @return builder for getting transactions
     */
    public TransactionsQueryBuilder queryTransactionsBuilder() {
        return new TransactionsQueryBuilder();
    }

    private List<AccountTransaction> getTransactions(final Map<String, Object> params) throws Exception {
        final String transactionsUri = getAccountTransactionsUri();
        final JSONArray array = toJSONArray(client.get(transactionsUri, params));

        final List<AccountTransaction> transactions = new ArrayList<AccountTransaction>();
        for (final Object obj : array) {
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

    protected String getUri() {
        return client.getUserResourceUri(BandwidthConstants.ACCOUNT_URI_PATH);
    }

    public class TransactionsQueryBuilder {

        private final Map<String, Object> params = new HashMap<String, Object>();

        public TransactionsQueryBuilder maxItems(final int maxItems) {
            params.put("maxItems", maxItems);
            return this;
        }

        public TransactionsQueryBuilder fromDate(final Date fromDate) {
            params.put("fromDate", dateFormat.format(fromDate));
            return this;
        }

        public TransactionsQueryBuilder toDate(final Date toDate) {
            params.put("toDate", dateFormat.format(toDate));
            return this;
        }

        public TransactionsQueryBuilder type(final String type) {
            params.put("type", type);
            return this;
        }

        public TransactionsQueryBuilder page(final int page) {
            params.put("page", page);
            return this;
        }

        public TransactionsQueryBuilder size(final int size) {
            params.put("size", size);
            return this;
        }

        public List<AccountTransaction> list() throws Exception {
            return getTransactions(params);
        }
    }
}
