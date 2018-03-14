package com.med.vrmc.tablet.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.cs.common.utils.ActivityManager;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.med.vrmc.tablet.proxy.ActivityMvpProxy;
import com.med.vrmc.tablet.proxy.ActivityMvpProxyImpl;
import com.med.vrmc.tablet.utils.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * Created by hcDarren on 2018/1/1.
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {
    private P mPresenter;
    private ActivityMvpProxy mMvpProxy;
    private KProgressHUD pd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        ActivityManager.getInstance().attach(this);
        // 创建 P，创建只能交给 子类，每个 Activity 都不一样
        mPresenter = createPresenter();
        if (mPresenter != null){
            mPresenter.attach(this);
            mMvpProxy = createMvpProxy();
            mMvpProxy.bindAndCreatePresenter();
        }
        // 1. Activity ？ Fragment? 1,2  ViewGroup ? 抽离  工具类抽出去，或者把代码每个地方 copy 一份
        // 今天你可以抽离目前的一部分：注入代码 + 额外功能 + 还有一些其他地方又不一样
        initView();
        initData();
    }


    /**
     * 创建 Mvp 的代理  自己去写 Fragment
     *
     * @return
     */
    private ActivityMvpProxy createMvpProxy() {
        if (mMvpProxy == null) {
            Logger.e("---------baseFragment","BaseMvpActivity == null");
            mMvpProxy = new ActivityMvpProxyImpl<>(this);
        }
        return mMvpProxy;
    }

    // 由子类去实现创建
    protected abstract P createPresenter();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void setContentView();

    @Override
    public void onDestroy() {
        ActivityManager.getInstance().deAttach(this);
        super.onDestroy();
        mMvpProxy.unbindPresenter();
        mMvpProxy = null;
        mPresenter.detach();
    }

    public P getPresenter() {
        return mPresenter;
    }

    public void initProgressDialog() {
        if (pd == null) {
            pd = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setDimAmount(0.5f)
                    .setAnimationSpeed(2)
                    .setCancellable(true);

            if (true) {
                Class temp = pd.getClass();
                try {
                    Field field = temp.getDeclaredField("mProgressDialog");
                    field.setAccessible(true);
                    Object obj = field.get(pd);
                    Method m = obj.getClass().getSuperclass().getDeclaredMethod("setOnCancelListener", DialogInterface.OnCancelListener.class);
                    m.invoke(obj, new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            mPresenter.getView().onError();
                        }
                    });
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            if (!pd.isShowing()) {
                pd.show();
            }
        }
    }

    public void dismissProgressDialog() {
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
    }
}
