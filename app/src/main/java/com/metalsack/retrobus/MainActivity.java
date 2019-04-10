package com.metalsack.retrobus;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.metalsack.retrobus.events.ApiErrorEvent;
import com.metalsack.retrobus.events.ApiErrorWithMessageEvent;
import com.metalsack.retrobus.network.response.ExpertListResponse;
import com.metalsack.retrobus.network.response.model.ExpertInfo;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private static final String LOGTAG = ".MainActivity";
    private static final String CHATLISTING = LOGTAG + ".Chatlistingrequest";
    private RecyclerView recyclerChatListing;
    private ArrayList<ExpertInfo> expertsList;
    private TextView txtNoChatlisting;
    private ChatLIstingAdapter chatLIstingAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerChatListing=(RecyclerView)findViewById(R.id.recyclerChatListing);
        recyclerChatListing.setLayoutManager(new LinearLayoutManager(this));
        txtNoChatlisting=findViewById(R.id.txtNoChatlisting);

        if(!mApiClient.isRequestRunning(CHATLISTING))
        mApiClient.get_recommended_users(CHATLISTING,"x");
    }

    /*
     * Networking methods that receive response,error message and handle unexpected errors
     * this methods are eventbus subscibers
     */

    @Subscribe
    public void onEventMainThread(ExpertListResponse response) {
        switch (response.getRequestTag()) {
            case CHATLISTING:
                expertsList=new ArrayList<>();
                recyclerChatListing.setVisibility(View.VISIBLE);
                txtNoChatlisting.setVisibility(View.GONE);
                expertsList.addAll(response.getExpertInfo());
                chatLIstingAdapter = new ChatLIstingAdapter(expertsList, this);
                recyclerChatListing.setAdapter(chatLIstingAdapter);
                break;
        }
    }


    /* *
     * EventBus listener. An API call failed. No error message was returned.
     *
     * @param event ApiErrorEvent
     */
    @Subscribe
    public void onEventMainThread(ApiErrorEvent event) {
        switch (event.getRequestTag()) {
            case CHATLISTING:
                dismissProgress();
                recyclerChatListing.setVisibility(View.GONE);
                txtNoChatlisting.setVisibility(View.VISIBLE);
                showMsg(getString(R.string.error_no_internet));
                break;

            default:
                break;
        }
    }

    /**
     * EventBus listener. An API call failed. An error message was returned.
     *
     * @param event ApiErrorWithMessageEvent Contains the error message.
     */
    @Subscribe
    public void onEventMainThread(ApiErrorWithMessageEvent event) {
        switch (event.getRequestTag()) {
            case CHATLISTING:
                dismissProgress();
                recyclerChatListing.setVisibility(View.GONE);
                txtNoChatlisting.setVisibility(View.VISIBLE);
                showMsg(event.getResultMsgUser());
                break;

            default:
                break;
        }
    }



}
