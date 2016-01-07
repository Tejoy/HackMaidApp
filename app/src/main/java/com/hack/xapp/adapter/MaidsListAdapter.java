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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hack.xapp.R;
import com.hack.xapp.activity.MainActivity;
import com.hack.xapp.model.FilterData;
import com.hack.xapp.model.Maid;
import com.hack.xapp.model.ServiceItem;
import com.hack.xapp.util.Util;

import java.util.ArrayList;
import java.util.List;

public class MaidsListAdapter extends RecyclerView.Adapter<MaidsListAdapter.RVViewHolder> implements Filterable {

    private static final String TAG = "RVExpenseListAdapter";

    Context mContext;
    List<Maid> items;
    List<Maid> filteredItems;
    private CustomFilter myFilter = null;


    public MaidsListAdapter(Context ctx, List<Maid> items) {
        mContext = ctx;
        this.items = items;
        filteredItems = items;
    }

    @Override
    public RVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_maid_list_item, parent, false);
        return new RVViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RVViewHolder holder, int position) {
        Log.d(TAG, "size " + filteredItems.size() + " " + position);
        final Maid maid = filteredItems.get(position);
        holder.id = maid.id;
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MainActivity.getSearchView().isShown()) {
                    MainActivity.getSearchMenuItem().collapseActionView();
                    MainActivity.getSearchView().setQuery("", false);
                }


                Intent i = new Intent();
                i.setClassName(mContext, "com.hack.xapp.activity.MaidProfile");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(Util.INTENT_EXTRA_MAID, maid);
                mContext.startActivity(i);
            }
        });
        holder.title.setText(maid.name);
        holder.amt.setText(maid.salaryFrom + "-" + maid.salaryTo);
        if (maid.photo != null) {
            holder.img.setBackground(new BitmapDrawable(mContext.getResources(), maid.photo));
        } else {
            holder.img.setImageResource(R.drawable.person);
        }

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(60, 60, 0, 0, 0);
        holder.ll.removeAllViews();
        View v;
        for (String st : maid.services) {
            v = LayoutInflater.from(mContext).inflate(R.layout.image_view_layout, null, false);
            v.setBackground(mContext.getResources().getDrawable(ServiceItem.getServiceResource(st)));

            holder.ll.addView(v, params);
        }


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new CustomFilter();
        }
        return myFilter;
    }

    public static class RVViewHolder extends RecyclerView.ViewHolder {
        long id;
        LinearLayout ll;
        RelativeLayout rl;
        ImageView img;
        TextView title;
        TextView amt;

        public RVViewHolder(View itemView) {
            super(itemView);
            rl = (RelativeLayout) itemView.findViewById(R.id.card_maid_list_item);
            ll = (LinearLayout) itemView.findViewById(R.id.maid_services_layout);
            img = (ImageView) itemView.findViewById(R.id.maid_photo);
            title = (TextView) itemView.findViewById(R.id.maid_name);
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

    public class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.i(TAG, "performFiltering for " + constraint);
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                List<Maid> maidList = new ArrayList<Maid>();
                for (Maid m : items) {
                    if (m.name.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        maidList.add(m);
                    }
                }
                results.count = maidList.size();
                results.values = maidList;
            } else {
                results.count = items.size();
                results.values = items;
            }
            return results;
        }

        public FilterResults filteringWithData() {
            Log.i(TAG, "performFiltering for FilterData");
            FilterResults results = new FilterResults();
            FilterData mFilterData = Util.mFilterData;
            if (mFilterData != null) {
                List<Maid> maidList = new ArrayList<Maid>();
                for (Maid m : items) {
                    //TODO: filter the data
                }
                results.count = maidList.size();
                results.values = maidList;
            } else {
                results.count = items.size();
                results.values = items;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            Log.i(TAG, "publishResults for " + constraint);
            filteredItems = (List<Maid>) results.values;
            notifyDataSetChanged();
        }
    }

}
