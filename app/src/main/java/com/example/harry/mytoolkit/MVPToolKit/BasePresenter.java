package com.example.harry.mytoolkit.MVPToolKit;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by Harry on 2018/5/3.
 */

public abstract class BasePresenter<T> {
    //View 介面類型的弱參照
    protected Reference<T> mViewRef;

    //建立關聯
    public void attachView(T view){
        mViewRef = new WeakReference<T>(view);
    }

    protected T getView(){
        return mViewRef.get();
    }

    //判斷是否建立關聯
    public boolean isViewAttached(){
        return mViewRef != null && mViewRef.get() != null;
    }

    //解除關聯
    public void detachView(){
        if(mViewRef != null){
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
