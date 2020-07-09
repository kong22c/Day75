package com.example.day75.net;



import com.example.day75.bean.ProcBean;
import com.example.day75.bean.PublicBean;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    String BASS="https://www.wanandroid.com/";
    @GET("project/tree/json")
    Flowable<ProcBean> getData();
    @GET("project/list/{page}/json")
    Flowable<PublicBean>getData1(@Path("page") int page, @Query("cid") int cid);
}
