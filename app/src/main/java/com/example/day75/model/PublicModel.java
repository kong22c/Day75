package com.example.day75.model;

import com.example.day75.base.BaseModel;
import com.example.day75.bean.PublicBean;
import com.example.day75.net.ApiService;
import com.example.day75.net.PublicCallBack;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PublicModel extends BaseModel {
    public void getData(int cid, int page, PublicCallBack<PublicBean> callBack){
        ResourceSubscriber<PublicBean> resourceSubscriber = new Retrofit.Builder()
                .baseUrl(ApiService.BASS)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService.class)
                .getData1(page, cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<PublicBean>() {
                    @Override
                    public void onNext(PublicBean publicBean) {
                        callBack.onSucess(publicBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                    callBack.onFain(t.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        addDisposable(resourceSubscriber);
    }
}
