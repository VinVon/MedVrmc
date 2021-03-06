package com.medvision.vrmed.proxy;



import com.medvision.vrmed.base.BasePresenter;
import com.medvision.vrmed.base.BaseView;
import com.medvision.vrmed.inject.InjectPresenter;
import com.medvision.vrmed.utils.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hcDarren on 2018/1/6.
 * 实现
 */
public class MvpProxyImpl<V extends BaseView> implements IMvpProxy{
    private String TAG = "MvpProxyImpl";
    private V mView;
    private List<BasePresenter> mPresenters;
    public MvpProxyImpl(V view){
        // 做一下判断 是不是 NULL
        Logger.e(TAG,view+"----------");
        this.mView = view;
        mPresenters = new ArrayList<>();
    }
    @Override
    public void bindAndCreatePresenter() {
        // 这个地方要去注入 Presenter 通过反射 (Dagger) soEasy
        Field[] fields = mView.getClass().getDeclaredFields();
        for (Field field : fields) {
            InjectPresenter injectPresenter = field.getAnnotation(InjectPresenter.class);
            if(injectPresenter != null){
                // 创建注入
                Class<? extends BasePresenter> presenterClazz = null;
                Class<BasePresenter> basePresenterClass = BasePresenter.class;
                Class<? extends Field> aClass = field.getClass();
                // 自己去判断一下类型？ 获取继承的父类，如果不是 继承 BasePresenter 抛异常
                  if (!BasePresenter.class.isAssignableFrom(field.getType())){
                      throw new RuntimeException(mView.getClass().getName()+"   No support inject presenter type " + field.getType().getName());
                  }else{
                      try {
                          // 创建 Presenter 对象
                          BasePresenter basePresenter = (BasePresenter) field.getType().newInstance();
                          // 并没有解绑，还是会有问题，这个怎么办？1 2
                          basePresenter.attach(mView);
                          // 设置
                          field.setAccessible(true);
                          field.set(mView,basePresenter);
                          mPresenters.add(basePresenter);
                      } catch (Exception e) {
                          e.printStackTrace();
                      }
                  }

            }
        }
    }

    @Override
    public void unbindPresenter() {
        // 一定要解绑
        for (BasePresenter presenter : mPresenters) {
            presenter.detach();
        }
        mView = null;
    }
}
