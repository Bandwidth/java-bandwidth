package com.bandwidth.sdk.model;

import com.bandwidth.sdk.MockClient;
import com.bandwidth.sdk.TestsHelper;
import org.junit.Before;

/**
 * Created by sbarstow on 9/30/14.
 */
public abstract class BaseModelTest {
    protected MockClient mockClient;

    @Before
    public void setUp(){
        mockClient = TestsHelper.getClient();
    }
}
