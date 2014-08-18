package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.IRestDriver;
import org.json.simple.JSONObject;

import java.util.Date;

/**
 * @author vpotapenko
 */
public class ConferenceMember extends BaseModelObject {

    public ConferenceMember(IRestDriver driver, String parentUri, JSONObject jsonObject) {
        super(driver, parentUri, jsonObject);
    }

    public String getCall() {
        return getPropertyAsString("call");
    }

    public String getState() {
        return getPropertyAsString("state");
    }

    public boolean isHold() {
        return getPropertyAsBoolean("hold");
    }

    public boolean isMute() {
        return getPropertyAsBoolean("mute");
    }

    public boolean isJoinTone() {
        return getPropertyAsBoolean("joinTone");
    }

    public boolean isLeavingTone() {
        return getPropertyAsBoolean("leavingTone");
    }

    public Date getAddedTime() {
        return getPropertyAsDate("addedTime");
    }

    @Override
    public String toString() {
        return "ConferenceMember{" +
                "id='" + getId() + '\'' +
                ", call='" + getCall() + '\'' +
                ", state='" + getState() + '\'' +
                ", hold=" + isHold() +
                ", mute=" + isMute() +
                ", joinTone=" + isJoinTone() +
                ", leavingTone=" + isLeavingTone() +
                ", addedTime=" + getAddedTime() +
                '}';
    }
}
