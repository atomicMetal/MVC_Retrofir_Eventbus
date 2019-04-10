package com.metalsack.retrobus.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.metalsack.retrobus.network.response.model.ExpertInfo;

import java.util.ArrayList;

/**
 * Created by android2 on 3/3/17.
 */

public class ExpertListResponse extends AbstractApiResponse {

    @SerializedName("info")
    @Expose
    ArrayList<ExpertInfo> expertInfo;

    public ArrayList<ExpertInfo> getExpertInfo() {
        return expertInfo;
    }

    public void setExpertInfo(ArrayList<ExpertInfo> expertInfo) {
        this.expertInfo = expertInfo;
    }
}
