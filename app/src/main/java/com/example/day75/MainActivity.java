package com.example.day75;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.day75.adapter.VpAdapter;
import com.example.day75.bean.ProcBean;
import com.example.day75.net.ApiService;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTl;
    private ViewPager mVp;
    private ArrayList<String> list;
    private ArrayList<Fragment> fragments;
    private VpAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initData();
    }

    private void initData() {
        new Retrofit.Builder()
                .baseUrl(ApiService.BASS)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService.class)
                .getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<ProcBean>() {
                    @Override
                    public void onNext(ProcBean procBean) {
                        List<ProcBean.DataBean> data = procBean.getData();
                        for (int i = 0; i <data.size() ; i++) {
                            list.add(data.get(i).getName());
                            fragments.add(new PublicFragment(data.get(i).getId()));
                        }
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.i("111", "onError: "+t.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initViews() {
        mTl = findViewById(R.id.tl);
        mVp = findViewById(R.id.vp);
        list = new ArrayList<>();
        fragments = new ArrayList<>();
        adapter = new VpAdapter(getSupportFragmentManager(), fragments, list);
        mTl.setupWithViewPager(mVp);
        mVp.setAdapter(adapter);
    }
}
