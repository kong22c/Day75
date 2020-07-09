package com.example.day75.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.day75.R;
import com.example.day75.bean.PublicBean;


import java.util.ArrayList;

public class PublicAdapter extends RecyclerView.Adapter<PublicAdapter.ViewHoldle> {
    private Context context;
    private ArrayList<PublicBean.DataBean.DatasBean>list;
    private OnItemLongClickLister onItemLongClickLister;

    public void setOnItemLongClickLister(OnItemLongClickLister onItemLongClickLister) {
        this.onItemLongClickLister = onItemLongClickLister;
    }

    public PublicAdapter(Context context, ArrayList<PublicBean.DataBean.DatasBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHoldle onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_pub, null);
        return new ViewHoldle(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoldle holder, final int position) {
        Glide.with(context).load(list.get(position).getEnvelopePic()).into(holder.iv_pic);
        holder.tv_desc.setText(list.get(position).getDesc());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemLongClickLister.onItemLongClick(position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoldle extends RecyclerView.ViewHolder {
        ImageView iv_pic;
        TextView tv_desc;
        public ViewHoldle(@NonNull View itemView) {
            super(itemView);
            iv_pic=itemView.findViewById(R.id.iv_pic);
            tv_desc=itemView.findViewById(R.id.tv_desc);
        }
    }
    public interface OnItemLongClickLister{
        void onItemLongClick(int position);
    }
}
