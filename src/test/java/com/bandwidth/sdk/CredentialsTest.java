package com.bandwidth.sdk;

import com.bandwidth.sdk.exception.MissingCredentialsException;
import com.bandwidth.sdk.model.Account;
import com.bandwidth.sdk.model.BaseModelTest;
import com.bandwidth.sdk.model.Domain;
import com.bandwidth.sdk.model.Endpoint;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

public class CredentialsTest extends BaseModelTest {

    @Before
    public void setUp(){
        super.setUp();

        RestResponse response = new RestResponse();
        response.setStatus(HttpStatus.SC_UNAUTHORIZED);
        mockClient.setRestResponse(response);
    }

    @Test(expected = MissingCredentialsException.class)
    public void shouldFailGetAccountWithoutCredentials() throws Exception {
        new Account(mockClient).getAccountInfo();
    }

    @Test(expected = MissingCredentialsException.class)
    public void shouldFailCreateDomainWithoutCredentials() throws Exception {
        Domain.create(mockClient, "DomainName", "DomainDescription");
    }

    @Test(expected = MissingCredentialsException.class)
    public void shouldFailUpdateDomainWithoutCredentials() throws Exception {
        Domain.update(mockClient, "DomainId", "DomainDescription");
    }

    @Test(expected = MissingCredentialsException.class)
    public void shouldFailDeleteEndpointWithoutCredentials() throws Exception {
        Endpoint.delete(mockClient, "DomainId", "EndpointId");
    }
}
