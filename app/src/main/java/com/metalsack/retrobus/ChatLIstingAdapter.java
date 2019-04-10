package com.metalsack.retrobus;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.metalsack.retrobus.network.response.model.ExpertInfo;
import com.metalsack.retrobus.utils.PermissionResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Fills my topics list with response data
 */

public class ChatLIstingAdapter extends RecyclerView.Adapter<ChatLIstingAdapter.ViewHolder> {
    private static final String TAG = ChatLIstingAdapter.class.getSimpleName();
    private List<ExpertInfo> experts;
    private Context context;
    private int KEY_PERMISSION = 0;
    private PermissionResult permissionResult;
    private String permissionsAsk[];
    int globalPosition;
    EventBus eventBus;
    boolean permissionGranted;

    public ChatLIstingAdapter() {
    }


    /*This method will unregister the eventbus upon recyclerview detach event*/
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        eventBus.unregister(this);
    }

    public ChatLIstingAdapter(List<ExpertInfo> expertsList, Context context) {
        this.experts = expertsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        globalPosition = holder.getAdapterPosition();
        holder.tvExpertName.setText(experts.get(position).getFullName());
    }


    /*This method will return the total item count of expert array*/

    @Override
    public int getItemCount() {
        return experts.size();
    }

    /*This method will return the  expert array*/

    public List<ExpertInfo> returnAllergenList() {
        return experts;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvExpertName;

        public ViewHolder(final View itemView) {
            super(itemView);
            tvExpertName = (TextView) itemView.findViewById(R.id.txtExperts);
        }

    }
}
