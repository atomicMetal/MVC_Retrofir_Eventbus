package com.metalsack.retrobus.network.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.metalsack.retrobus.utils.Dumper;

import java.io.Serializable;


/**
 * The abstract base class for all possible API responses. It contains all elements which are common
 * to all API responses.
 */
public  class AbstractApiResponse implements Serializable {

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("active")
    @Expose
    private boolean status;

    @SerializedName("message")
    @Expose
    private String msg;
    /*@SerializedName("like_status")
    @Expose
    private boolean like_status;*/


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    /**
     * Identifies the request which was executed to receive this response. The tag is used to make
     * sure that a class which executes a requests only handles the response which is meant for it.
     * This implies that the tag is unique.
     */
    private String requestTag;

    public void setRequestTag(String requestTag) {
        this.requestTag = requestTag;
    }

    public String getRequestTag() {
        return requestTag;
    }

    @Override
    public String toString() {
        return Dumper.dump(this);
    }

}
