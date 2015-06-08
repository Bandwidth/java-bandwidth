package com.bandwidth.sdk;

import com.bandwidth.sdk.exception.MissingCredentialsException;
import com.bandwidth.sdk.model.Account;
import com.bandwidth.sdk.model.BaseModelTest;
import com.bandwidth.sdk.model.Domain;
import com.bandwidth.sdk.model.Endpoint;
import org.junit.Test;

/**
 * Test to validate that SDK throws specific exception for missing credentials
 *
 * This test class has one test for each HTTP operation (GET, POST, PUT, DELETE)
 */
public class CredentialsTest extends BaseModelTest {

    @Test(expected = MissingCredentialsException.class)
    public void testGetAccountWithoutCredentials() throws Exception {
        Account.get().getAccountInfo();
    }

    @Test(expected = MissingCredentialsException.class)
    public void testCreateDomainWithoutCredentials() throws Exception {
        Domain.create("TestDomain");
    }

    @Test(expected = MissingCredentialsException.class)
    public void testUpdateDomainWithoutCredentials() throws Exception {
        Domain.update("DomainId", "NewDescription");
    }

    @Test(expected = MissingCredentialsException.class)
    public void testDeleteEndpointWithoutCredentials() throws Exception {
        Endpoint.delete("111111", "222222");
    }
}
