package com.med.vrmc.tablet.adapter;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.med.vrmc.tablet.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by raytine on 2017/8/23.
 */

public class HeaderBottomAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //item类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_BOTTOM = 2;
    //模拟数据
    //模拟数据
    private List<T> newsInfo = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private int mHeaderCount = 0;//头部View个数
    private int mBottomCount = 0;//底部View个数
    List images;

    public HeaderBottomAdapter(Context context, List images) {
        mContext = context;
        this.images = images;
    }

    public void setImages(List images) {
        this.images = images;
        notifyDataSetChanged();
    }

    public HeaderBottomAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }


    //内容长度
    public int getContentItemCount() {
        return 20;
    }

    //判断当前item是否是HeadView
    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }

    //判断当前item是否是FooterView
    public boolean isBottomView(int position) {
        return mBottomCount != 0 && position >= (mHeaderCount + getContentItemCount());
    }

    //判断当前item类型
    @Override
    public int getItemViewType(int position) {
        int dataItemCount = getContentItemCount();
        if (mHeaderCount != 0 && position < mHeaderCount) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else if (mBottomCount != 0 && position >= (mHeaderCount + dataItemCount)) {
            //底部View
            return ITEM_TYPE_BOTTOM;
        } else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }

    public void setNewsInfo(List<T> newsInfo) {
        this.newsInfo = newsInfo;
        notifyDataSetChanged();
    }

    //内容 ViewHolder
    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView title;
        TextView dsc;
        TextView date;

        public ContentViewHolder(View itemView) {
            super(itemView);


        }
    }

    //头部 ViewHolder
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView myPatient;
        TextView myIdea;
        TextView myArea;

        public HeaderViewHolder(View itemView) {//声明控件
            super(itemView);

        }
    }

    //底部 ViewHolder
    public static class BottomViewHolder extends RecyclerView.ViewHolder {
        public BottomViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            return new HeaderViewHolder(LayoutInflater.from(mContext).inflate(R.layout.fragment_main_headview, parent, false));
        } else if (viewType == ITEM_TYPE_CONTENT) {
            return new ContentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_expert, parent, false));
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            return new BottomViewHolder(null);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {

        } else if (holder instanceof ContentViewHolder) {

        } else if (holder instanceof BottomViewHolder) {
        }
    }

    @Override
    public int getItemCount() {
        return mHeaderCount + getContentItemCount() + mBottomCount;
    }
}
