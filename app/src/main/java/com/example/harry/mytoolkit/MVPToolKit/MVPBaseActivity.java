package com.example.harry.mytoolkit.MVPToolKit;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Harry on 2018/5/3.
 * Activity 基底類別
 * 透過此基底類別  控制 Presenter 與 Activity 的關係
 */

public abstract class MVPBaseActivity<V , T extends BasePresenter<V>> extends Activity {
    protected T mPresenter ; //Presenter物件

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();  //建立Presenter
        mPresenter.attachView((V) this); //View 與 Presenter 建立關聯
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    protected abstract T createPresenter();
}
