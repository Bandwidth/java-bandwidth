package com.bandwidth.sdk.model;

import com.bandwidth.sdk.MockRestClient;
import com.bandwidth.sdk.TestsHelper;
import org.junit.Before;

/**
 * Created by sbarstow on 9/30/14.
 */
public abstract class BaseModelTest {
    protected MockRestClient mockRestClient;

    @Before
    public void setUp(){
        mockRestClient = TestsHelper.getClient();
    }
}
