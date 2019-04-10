package com.metalsack.retrobus.network.requests;

import android.content.Context;


import com.metalsack.retrobus.R;
import com.metalsack.retrobus.RetroBusApp;

import com.metalsack.retrobus.network.APIService;
import com.metalsack.retrobus.network.response.AbstractApiCallback;
import com.metalsack.retrobus.network.response.ExpertListResponse;
import com.metalsack.retrobus.utils.Helper;

/**
 * Created by android2 on 3/3/17.
 */

public class ExpertLIstRequest extends AbstractApiRequest {
    private static final String LOGTAG = ExpertLIstRequest.class.getSimpleName();

    private static Context mContext;
    private String userId;
    private AbstractApiCallback<ExpertListResponse> expertListCallback;

    /**
     * Initialize the request with the passed values.
     *  @param APIService The {@link APIService} used for executing the calls.
     * @param tag        Identifies the request.
     */

    public ExpertLIstRequest(APIService APIService, String tag,
                             String user_id ) {
        super(APIService, tag);
        this.userId = user_id;
        mContext= RetroBusApp.getAppContext();
    }

    public void execute() {
        expertListCallback = new AbstractApiCallback(tag);

        if (!isInternetActive()) {
            expertListCallback.postUnexpectedError(mContext.getString(R.string.error_no_internet));
            return;
        }

        apiService.get_recommended_users().enqueue(expertListCallback);

    }

    @Override
    public void cancel() {
        expertListCallback.invalidate();
    }

    @Override
    public boolean isInternetActive() {
        return Helper.isInternetActive(mContext);
    }
}
