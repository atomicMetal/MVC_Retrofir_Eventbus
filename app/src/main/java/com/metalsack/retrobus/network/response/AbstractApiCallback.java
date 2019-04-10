package com.metalsack.retrobus.network.response;

import android.text.TextUtils;

import com.metalsack.retrobus.events.ApiErrorEvent;
import com.metalsack.retrobus.events.ApiErrorWithMessageEvent;
import com.metalsack.retrobus.events.RequestFinishedEvent;
import com.metalsack.retrobus.network.ApiClient;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Use this class to have a callback which can be used for the api calls in {@link com.metalsack.retrobus.network.APIService}. Such
 * a callback can be invalidated to not notify its caller about the api response. Furthermore it
 * handles finishing the request after the caller has handled the response.
 */
public class AbstractApiCallback<T extends AbstractApiResponse> implements Callback<T> {

    /**
     * Indicates if the callback was invalidated.
     */
    private boolean isInvalidated;

    /**
     * The tag of the request which uses this callback.
     */
    private final String requestTag;

    /**
     * Creates an {@link AbstractApiCallback} with the passed request tag. The tag is used to finish
     * the request after the response has been handled. See {@link #finishRequest}.
     *
     * @param requestTag The tag of the request which uses this callback.
     */
    public AbstractApiCallback(String requestTag) {
        isInvalidated = false;
        this.requestTag = requestTag;
    }


    @Override
    public void onResponse(Call<T> call, Response<T> response) {
      if (isInvalidated || call.isCanceled()) {
            finishRequest();
            return;
        }

        T result = response.body();

        if (response.isSuccessful() && result != null) {

            if (!result.isSuccess()) {
                // Error occurred. Check for error message from api.
                String resultMsgUser = result.getMsg();

                if (!TextUtils.isEmpty(resultMsgUser)) {
                    EventBus.getDefault()
                            .post(new ApiErrorWithMessageEvent(requestTag, resultMsgUser));
                } else {
                    EventBus.getDefault().post(new ApiErrorEvent(requestTag));
                }


            } else {
                modifyResponseBeforeDelivery(result);
                result.setRequestTag(requestTag);
                EventBus.getDefault().post(result);
            }
        } else {

            try {
                AbstractApiResponse abstractApiResponse = (AbstractApiResponse) ApiClient.getRetrofit().responseBodyConverter(
                        AbstractApiResponse.class,
                        AbstractApiResponse.class.getAnnotations())
                        .convert(response.errorBody());
                // Do error handling here

                EventBus.getDefault()
                        .post(new ApiErrorWithMessageEvent(requestTag,
                                abstractApiResponse.getMsg().toString()));
                finishRequest();
                return;

            } catch (IOException e) {
                e.printStackTrace();
            }


//            EventBus.getDefault()
//                    .post(new ApiErrorWithMessageEvent(requestTag,
//                                                       response.errorBody().toString()));

            EventBus.getDefault()
                    .post(new ApiErrorWithMessageEvent(requestTag, response.message().toString()));


           /* try {
                EventBus.getDefault()
                        .post(new ApiErrorWithMessageEvent(requestTag,response.errorBody().string()));
            } catch (IOException e) {
                EventBus.getDefault()
                        .post(new ApiErrorWithMessageEvent(requestTag,"Error! Please try again."));
                e.printStackTrace();
            }*/
            // response.errorBody().message().toString()));
        }

        finishRequest();
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (!isInvalidated) {
            /*if(result.getStatus()==401 || error.getResponse().getStatus()==412)
            {

            }*/
            EventBus.getDefault().post(new ApiErrorEvent(requestTag, t));
        }

        finishRequest();
    }

    /**
     * Invalidates this callback. This means the caller doesn't want to be called back anymore.
     */
    public void invalidate() {
        isInvalidated = true;
    }

    /**
     * Posts a {@link com.metalsack.retrobus.events.RequestFinishedEvent} on the EventBus to tell the {@link
     * ApiClient} to remove the request from the list of running requests.
     */
    private void finishRequest() {
        EventBus.getDefault().post(new RequestFinishedEvent(requestTag));
    }

    /**
     * This is for callbacks which extend ApiCallback and want to modify the response before it is
     * delivered to the caller.
     *
     * @param result The api response.
     */
    @SuppressWarnings("UnusedParameters")
    protected void modifyResponseBeforeDelivery(T result) {
        // Do nothing here. Only for subclasses.
    }

    /**
     * Call this method if No internet connection or other use. See
     *
     * @param resultMsgUser User defined messages.
     */
    public void postUnexpectedError(String resultMsgUser) {
        EventBus.getDefault().post(new ApiErrorWithMessageEvent(requestTag, resultMsgUser));
        finishRequest();
    }
}
