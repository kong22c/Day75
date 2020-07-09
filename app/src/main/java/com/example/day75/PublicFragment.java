package com.example.day75;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.day75.adapter.PublicAdapter;
import com.example.day75.base.BaseFragment;
import com.example.day75.bean.PublicBean;
import com.example.day75.presenter.PublicPresenter;
import com.example.day75.view.PublicView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.EventListener;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PublicFragment extends BaseFragment<PublicPresenter> implements PublicView, OnRefreshLoadMoreListener, PublicAdapter.OnItemLongClickLister {


    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.sel)
    SmartRefreshLayout sel;
    private int cid;
    private int page=1;
    private ArrayList<PublicBean.DataBean.DatasBean> list;
    private PublicAdapter adapter;

    public PublicFragment(int id) {
        // Required empty public constructor
        cid=id;
    }


    @Override
    protected void initPresenter() {
       mPresnter=new PublicPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_public;
    }

    @Override
    protected void initLister() {

    }

    @Override
    protected void initView() {
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        list = new ArrayList<>();
        adapter = new PublicAdapter(getActivity(), list);
        rv.setAdapter(adapter);
        sel.setOnRefreshLoadMoreListener(this);
        adapter.setOnItemLongClickLister(this);
    }

    @Override
    protected void initData() {
mPresnter.getData(cid,page);
    }

    @Override
    public void setData(PublicBean publicBean) {
        list.addAll(publicBean.getData().getDatas());
        adapter.notifyDataSetChanged();
        sel.finishRefresh();
        sel.finishLoadMore();
    }

    @Override
    public void showToast(String str) {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        initData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
page=1;
list.clear();
initData();
    }

    @Override
    public void onItemLongClick(int position) {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        EventBus.getDefault().postSticky(list.get(position).getLink());
        startActivity(intent);
    }
}
