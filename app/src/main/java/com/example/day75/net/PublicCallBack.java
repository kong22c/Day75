package com.example.day75.net;

public interface PublicCallBack<T> {
    void onSucess(T t);
    void onFain(String str);
}
