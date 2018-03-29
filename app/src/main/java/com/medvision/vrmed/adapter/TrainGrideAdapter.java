package com.medvision.vrmed.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medvision.vrmed.R;
import com.medvision.vrmed.activity.RecordActivity;
import com.medvision.vrmed.activity.TrainingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 向文圣 on 2018/3/20.
 */

public class TrainGrideAdapter extends RecyclerView.Adapter<TrainGrideAdapter.MyViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private List<String> datas = new ArrayList<>();
    private Context context;
    private int layout;
    public TrainGrideAdapter(Context context, int layout) {
        this.context = context;
        this.layout = layout;
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return TYPE_NORMAL;
        return TYPE_NORMAL;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(layout, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;
        TextView patient_record;
        TextView patient_train;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.patient_name);
            patient_record = (TextView) view.findViewById(R.id.patient_record);
            patient_train = (TextView) view.findViewById(R.id.patient_tran);
        }
    }

}

