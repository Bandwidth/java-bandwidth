package com.bandwidth.sdk.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Credentials {
    
    @JsonProperty(value = "realm")
    private String realm;
    
    @JsonProperty(value = "username")
    private String userName;
    
    /**
     * @return the realm
     */
    public String getRealm() {
        return realm;
    }
    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }
    /**
     * @param realm the realm to set
     */
    public void setRealm(final String realm) {
        this.realm = realm;
    }
    /**
     * @param userName the userName to set
     */
    public void setUserName(final String userName) {
        this.userName = userName;
    }
}
