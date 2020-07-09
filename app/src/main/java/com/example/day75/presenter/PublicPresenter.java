package com.example.day75.presenter;


import com.example.day75.base.BasePreseneter;
import com.example.day75.bean.PublicBean;
import com.example.day75.model.PublicModel;
import com.example.day75.net.PublicCallBack;
import com.example.day75.view.PublicView;

public class PublicPresenter extends BasePreseneter<PublicView> {

    private PublicModel publicModel;

    @Override
    protected void initModel() {
        publicModel = new PublicModel();
        addModel(publicModel);
    }
    public void getData(int cid,int page){
        publicModel.getData(cid, page, new PublicCallBack<PublicBean>() {
            @Override
            public void onSucess(PublicBean publicBean) {
                mView.setData(publicBean);
            }

            @Override
            public void onFain(String str) {
                mView.showToast(str);
            }
        });
    }
}
