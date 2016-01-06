package com.hack.xapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hack.xapp.R;
import com.hack.xapp.model.BookedItem;
import com.hack.xapp.model.ServiceItem;
import com.hack.xapp.util.Util;

import java.util.List;

/**
 * Created by tejom_000 on 1/6/2016.
 */
public class BookedHistoryListAdapter extends RecyclerView.Adapter<BookedHistoryListAdapter.RVViewHolder> {

    private static final String TAG = "BookedHistoryListAdapter";

    Context mContext;
    List<BookedItem> items;

    public BookedHistoryListAdapter(Context ctx, List<BookedItem> items) {
        mContext = ctx;
        this.items = items;
        Log.i(TAG, "sizeee " + items.size());
    }

    @Override
    public BookedHistoryListAdapter.RVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_booked_list_item, parent, false);
        return new RVViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RVViewHolder holder, int position) {
        Log.d(TAG, "size " + items.size() + " " + position);
        final BookedItem item = items.get(position);
        holder.id = item.id;
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClassName(mContext, "com.hack.xapp.activity.MaidProfile");
                i.putExtra(Util.FROM_HISTORY, true);
                i.putExtra(Util.EXTRA_MAID, item.bookedMaid);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
            }
        });
        holder.title.setText(item.bookedMaid.name);
        holder.amt.setText("" + item.salary);
        if (item.bookedMaid.photo != null) {
            holder.img.setBackground(new BitmapDrawable(mContext.getResources(), item.bookedMaid.photo));
        } else {
            holder.img.setImageResource(R.drawable.person);
        }

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(60, 60, 0, 0, 0);

        View v;
        for (String st : item.bookedMaid.services) {
            v = LayoutInflater.from(mContext).inflate(R.layout.image_view_layout, null, false);
            v.setBackground(mContext.getResources().getDrawable(ServiceItem.getServiceResource(st)));
            holder.ll.addView(v, params);
        }
        holder.duration.setText(item.numberOfDays + " Days");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class RVViewHolder extends RecyclerView.ViewHolder {
        long id;
        RelativeLayout rl;
        TextView title;
        TextView amt;
        ImageView img;
        LinearLayout ll;
        TextView duration;

        public RVViewHolder(View itemView) {
            super(itemView);
            rl = (RelativeLayout) itemView.findViewById(R.id.card_booked_list_item);
            ll = (LinearLayout) itemView.findViewById(R.id.maid_services_layout);
            img = (ImageView) itemView.findViewById(R.id.maid_photo);
            title = (TextView) itemView.findViewById(R.id.maid_name);
            amt = (TextView) itemView.findViewById(R.id.maid_salary);
            duration = (TextView) itemView.findViewById(R.id.maid_duration);
        }
    }
}
