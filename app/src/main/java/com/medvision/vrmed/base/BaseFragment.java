package com.medvision.vrmed.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.medvision.vrmed.proxy.FragmentMvpProxtImp;
import com.medvision.vrmed.proxy.FragmentMvpProxy;
import com.medvision.vrmed.utils.Functions;
import com.medvision.vrmed.utils.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by raytine on 2018/1/8.
 */

public abstract class BaseFragment<P extends BasePresenter>  extends Fragment implements BaseView{
    Unbinder unbinder;
    protected FuncBaseActivity mBaseActivity;
    /** * 函数的集合 */
    protected Functions mFunctions;

    /** * activity调用此方法进行设置Functions
     * @param functions */
    public void setFunctions(Functions functions){
        this.mFunctions = functions;
    }
    private P mPresenter;
    private FragmentMvpProxy mMvpProxy;
    private KProgressHUD pd;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        mPresenter = createPresenter();
        mPresenter.attach(this);
        mMvpProxy = createMvpProxy();
        initView(view);
        initData();
        return view;
    }
    /**
     * 创建 Mvp 的代理  自己去写 Fragment
     * @return
     */
    private FragmentMvpProxy createMvpProxy() {
        if(mMvpProxy == null){
            Logger.e("---------baseFragment","mMvpProxy == null");
            mMvpProxy = new FragmentMvpProxtImp<>(this);
        }
        mMvpProxy.bindAndCreatePresenter();
        return mMvpProxy;
    }

    protected abstract P createPresenter();

    protected abstract void initData();

    protected abstract void initView(View v);
    //获取布局文件ID
    protected abstract int getLayoutId();

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mMvpProxy.unbindPresenter();
        mMvpProxy = null;
        mPresenter.detach();
    }

    public void initProgressDialog() {
        if (pd == null) {
            pd = KProgressHUD.create(getActivity())
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

