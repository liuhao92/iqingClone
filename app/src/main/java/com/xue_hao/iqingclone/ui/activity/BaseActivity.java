package com.xue_hao.iqingclone.ui.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.basestonedata.credit.R;
import com.basestonedata.credit.ui.BaseApplication;
import com.basestonedata.credit.util.GlobalUtil;
import com.basestonedata.credit.util.LogUtils;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.umeng.analytics.MobclickAgent;

/**
 * activity的基类,含有一些基本的通用方法
 * Created by haox on 2015/9/6.
 */
public class BaseActivity extends FragmentActivity {
    private boolean useBack;
    private boolean useTitleBar; // 是否使用titleBar
    private FragmentManager mFragMgr;
    private FragmentTransaction trans;
    protected Context mContext;
    protected BaseApplication application;
    protected SVProgressHUD mSVProgressHUD;

    /**
     * onCreat方法的回调
     *
     * @param savedInstanceState
     */
    protected void onCreateCallBack(Bundle savedInstanceState) {
    }

    /**
     * onResume方法的回调
     */
    protected void onResumeCallBack() {
    }

    /**
     * onPause回调
     */
    protected void onPauseCallBack() {

    }

    /**
     * onDestroy的方法回调
     */
    protected void onDestroyCallBack() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            GlobalUtil.getCurrentUser();
            mFragMgr = getSupportFragmentManager();
            mContext = this;
            useBack = false;
            application = (BaseApplication) getApplication();
            application.addActivity(this);
            mSVProgressHUD = new SVProgressHUD(this);

            onCreateCallBack(savedInstanceState);
        } catch (Throwable throwable) {
            LogUtils.LOGD_EXCEPTION(throwable);
            MobclickAgent.reportError(mContext, throwable);
        }
    }

    @Override
    protected void onResume() {
        try {
            super.onResume();
            MobclickAgent.onResume(mContext);
            /**
             * 设置为竖屏
             */
            if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            onResumeCallBack();
        } catch (Throwable throwable) {
            LogUtils.LOGD_EXCEPTION(throwable);
            MobclickAgent.reportError(mContext, throwable);
        }
    }

    @Override
    protected void onPause() {
        try {
            super.onPause();
            MobclickAgent.onPause(this);
            onPauseCallBack();
        } catch (Throwable throwable) {
            LogUtils.LOGD_EXCEPTION(throwable);
            MobclickAgent.reportError(mContext, throwable);
        }
    }

    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
            onDestroyCallBack();
        } catch (Throwable throwable) {
            LogUtils.LOGD_EXCEPTION(throwable);
            MobclickAgent.reportError(mContext, throwable);
        }
    }

    @Override
    public void finish() {
        application.popActivity(this);
        super.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBack();
            return true;
        }
        return false;
    }

//    @Override
//    public void setContentView(int layoutResID) {
//        View v= View.inflate(this,layoutResID,null);
//        v.setBackgroundResource(R.color.app_theme_color);//把所有的背景换成白色
//        setContentView(v);
//    }

    /**
     * 切换至下一个fragment
     *
     * @param needback
     * @param frag
     */
    public void nextFragment(boolean needback, Fragment frag) {
        trans = mFragMgr.beginTransaction();
        trans.replace(R.id.fragment_contener, frag, frag.getClass().getName());
        if (needback)
            trans.addToBackStack(frag.getClass().getName());
        trans.commit();
    }

    /**
     * 返回的促发函数
     */
    public void onBack() {
        if (useBack) {
            if (mFragMgr.getBackStackEntryCount() == 1) {
                finish();
            } else {
                mFragMgr.popBackStack();
            }
        } else {
            super.onBackPressed();
        }
    }

    protected boolean isUseBack() {
        return useBack;
    }

    /**
     * 是否使用自定义返回
     *
     * @param userBack
     */
    protected void setUseBack(boolean userBack) {
        this.useBack = userBack;
    }

    public SVProgressHUD getSVProgressHUD() {
        return mSVProgressHUD;
    }
}
