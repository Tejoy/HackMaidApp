package com.hack.xapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hack.xapp.R;
import com.hack.xapp.model.Maid;

import java.util.List;

public class MaidsListAdapter extends RecyclerView.Adapter<MaidsListAdapter.RVViewHolder> {

    private static final String TAG = "RVExpenseListAdapter";

    Context mContext;
    List<Maid> items;

    public MaidsListAdapter(Context ctx, List<Maid> items) {
        mContext = ctx;
        this.items = items;
    }

    @Override
    public RVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_maid_list_item, parent, false);
        return new RVViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RVViewHolder holder, int position) {
        Log.d(TAG, "size " + items.size() + " " + position);
        final Maid maid = items.get(position);
        holder.id = maid.id;
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClassName(mContext, "com.hack.xapp.activity.MaidProfile");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
            }
        });
        holder.title.setText(maid.name);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
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

        public RVViewHolder(View itemView) {
            super(itemView);
            rl = (RelativeLayout) itemView.findViewById(R.id.card_maid_list_item);
            title = (TextView) itemView.findViewById(R.id.maid_title);
            amt = (TextView) itemView.findViewById(R.id.maid_salary);
        }
    }

    public void add(Maid m) {
        Log.i(TAG, "adapter add call");
        items.add(m);
        notifyDataSetChanged();
    }

    public void set(List<Maid> l) {
        items = l;
        notifyDataSetChanged();
    }

}
