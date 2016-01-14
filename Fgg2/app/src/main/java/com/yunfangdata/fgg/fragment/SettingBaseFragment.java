package com.yunfangdata.fgg.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunfang.framework.base.BaseWorkerFragment;
import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.helper.IntentHelper;
import com.yunfangdata.fgg.logic.SettingOperator;
import com.yunfangdata.fgg.model.ResultInfo;
import com.yunfangdata.fgg.model.UpdataBeen;
import com.yunfangdata.fgg.utils.DataCleanManager;
import com.yunfangdata.fgg.utils.FileUitls;
import com.yunfangdata.fgg.utils.VersionUtil;

/**
 * 设置的基础页面
 * 2015年12月20日 18:47:54  -- zjt
 */
public class SettingBaseFragment extends BaseWorkerFragment {
    /**
     * 更新跳转地址
     */
    public static final String updata_url = "http://www.pgyer.com/ziaM";
    /**
     * FragmentManager管理者
     */
    private FragmentManager fMgr;

    /**
     * 检查更新
     */
    private final int HANDLE_GET_SEARCH_NAME = 120;
    /**
     * 当前版本
     */
    private String versionName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seting_base, container, false);


        //初始化界面
        assignViews(view);

        //初始化数据
        initData();


        return view;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //获取缓存大小
        String autoFileOrFilesSize = FileUitls.getAutoFileOrFilesSize(getActivity().getCacheDir().getPath());
        if (autoFileOrFilesSize != null) {
            sbTvCacheValue.setText("缓存大小 " + autoFileOrFilesSize);
        }

        //获取当前版本号
        versionName = VersionUtil.getVersionName(getActivity());
        if (versionName != null){
            sbTvUpdataValue.setText("当前版本 v" + versionName);
        }


    }

    @Override
    protected void handlerBackgroundHandler(Message msg) {
        Message resultMsg = new Message();
        Bundle bundle = msg.getData();
        resultMsg.what = msg.what;
        switch (msg.what) {
            case HANDLE_GET_SEARCH_NAME:// 版本更新
                resultMsg.obj = SettingOperator.getUpdate();
                break;
        }
        mUiHandler.sendMessage(resultMsg);
    }


    @SuppressWarnings("unchecked")
    @Override
    protected void handUiMessage(Message msg) {
        super.handUiMessage(msg);
        switch (msg.what) {
            case HANDLE_GET_SEARCH_NAME:// 获取模糊搜索
                sbRlUpdata.setClickable(true);
                ResultInfo<UpdataBeen> result = (ResultInfo<UpdataBeen>) msg.obj;
                if (result.Success) {
                    if (result.Data != null) {
                        UpdataBeen updataBeen = result.Data;
                        String version = updataBeen.getVersion();
                        if (versionName!= null&& version!= null){
                            if (!versionName.equals(version)){
                                showUpdataDialog(version);
                            }else {
                                showToast("当前已经是最新版本");
                            }
                        }
                    } else {
                        showToast(result.Message);
                    }
                } else {
                    showToast(result.Message);
                }
                break;
        }
    }





    private RelativeLayout sbRlCache;
    private TextView sbTvCacheValue;
    private RelativeLayout sbRlUpdata;
    private TextView sbTvUpdataValue;
    private RelativeLayout sbRlAboutUs;
    private RelativeLayout sbRlSay;
    private RelativeLayout sbRlLineMe;

    /**
     * 初始化界面
     *
     * @param view
     */
    private void assignViews(View view) {
        sbRlCache = (RelativeLayout) view.findViewById(R.id.sb_rl_cache);
        sbRlUpdata = (RelativeLayout) view.findViewById(R.id.sb_rl_updata);
        sbRlAboutUs = (RelativeLayout) view.findViewById(R.id.sb_rl_about_us);
        sbRlSay = (RelativeLayout) view.findViewById(R.id.sb_rl_say);
        sbRlLineMe = (RelativeLayout) view.findViewById(R.id.sb_rl_line_me);
        sbTvCacheValue = (TextView) view.findViewById(R.id.sb_tv_cache_value);
        sbTvUpdataValue = (TextView) view.findViewById(R.id.sb_tv_updata_value);


        sbRlCache.setOnClickListener(myBaseOnClickListener);
        sbRlUpdata.setOnClickListener(myBaseOnClickListener);
        sbRlAboutUs.setOnClickListener(myBaseOnClickListener);
        sbRlSay.setOnClickListener(myBaseOnClickListener);
        sbRlLineMe.setOnClickListener(myBaseOnClickListener);
    }

    /**
     * 按钮的点击事件
     */
    private View.OnClickListener myBaseOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sb_rl_cache: //清理缓存
                    showCachaDelete();
                    break;
                case R.id.sb_rl_updata: // 更新
                    actionClickUpdata();
                    break;
                case R.id.sb_rl_about_us: //关于
                    aboutUsFragment();
                    break;
                case R.id.sb_rl_say: // 声明
                    statementFragment();
                    break;
                case R.id.sb_rl_line_me: //联系我们
                    contactFragment();
                    break;
                default:
                    break;
            }
        }

    };

    /**
     * 点击更新
     */
    private void actionClickUpdata() {
        sbRlUpdata.setClickable(false);
        Message get_detail = new Message();
        get_detail.what = HANDLE_GET_SEARCH_NAME;
        mBackgroundHandler.sendMessage(get_detail);

    }

    /**
     * 删除缓存
     */
    private void showCachaDelete() {
        showDialog();
    }

    /**
     * 展示对话框
     */
    private void showDialog() {

        AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.setTitle("提示");
        dialog.setMessage("是否清除缓存?");
        // 确定
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                DataCleanManager.cleanInternalCache(getActivity());
                Toast.makeText(getActivity(), "缓存清理成功!", Toast.LENGTH_SHORT).show();
                initData();
                dialog.dismiss();
            }
        });
        // 确定
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // 显示对话框
        dialog.show();

    }
    /**
     * 展示升级对话框
     * @param version
     */
    private void showUpdataDialog(String version) {

        AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.setTitle("提示");
        dialog.setMessage("发现新版本 v" + version + "\n是否升级?");
        // 确定
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //跳转到浏览器
                IntentHelper.intentToWebUpdata(getActivity(), updata_url);
                dialog.dismiss();
            }
        });
        // 确定
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        // 显示对话框
        dialog.show();
    }



    /**
     * 添加关于我们的Fragment
     */
    private void aboutUsFragment() {
        if (fMgr == null) {
            fMgr = getActivity().getSupportFragmentManager();
        }
        Fragment fragment_aboutus = fMgr.findFragmentByTag(FragmentTag.SETTING_ABOUT_US_FRAGMENT);
        FragmentHelper.hideAllFragment(fMgr);
        if (fragment_aboutus != null) {
            FragmentHelper.showFragment(fMgr, fragment_aboutus);
        } else {
            fragment_aboutus = new SettingAboutUsFragment();
            FragmentHelper.addFragment(fMgr, fragment_aboutus, FragmentTag.SETTING_ABOUT_US_FRAGMENT, R.id.setting_contain);
        }
    }

    /**
     * 添加免责声明的Fragment
     */
    private void statementFragment() {
        if (fMgr == null) {
            fMgr = getActivity().getSupportFragmentManager();
        }
        Fragment fragment_statemen = fMgr.findFragmentByTag(FragmentTag.SETTING_STATEMENT_FRAGMENT);
        FragmentHelper.hideAllFragment(fMgr);
        if (fragment_statemen != null) {
            FragmentHelper.showFragment(fMgr, fragment_statemen);
        } else {
            fragment_statemen = new SettingStatementFragment();
            FragmentHelper.addFragment(fMgr, fragment_statemen, FragmentTag.SETTING_STATEMENT_FRAGMENT, R.id.setting_contain);
        }
    }

    /**
     * 添加联系我们的Fragment
     */
    private void contactFragment() {
        if (fMgr == null) {
            fMgr = getActivity().getSupportFragmentManager();
        }
        Fragment fragment_contact = fMgr.findFragmentByTag(FragmentTag.SETTING_CONTACT_FRAGMENT);
        FragmentHelper.hideAllFragment(fMgr);
        if (fragment_contact != null) {
            FragmentHelper.showFragment(fMgr, fragment_contact);
        } else {
            fragment_contact = new SettingContactFragment();
            FragmentHelper.addFragment(fMgr, fragment_contact, FragmentTag.SETTING_CONTACT_FRAGMENT, R.id.setting_contain);
        }
    }

}
