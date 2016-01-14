package com.yunfangdata.fgg.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.yunfangdata.fgg.R;

/**
 * Fragment帮助
 * 2015年12月20日 18:19:55 --zjt
 */
public class FragmentHelper {
	/**
	 * 添加fragment
	 * @param fragment
	 * @param fragmentTag
	 */
	public static void addFragment (FragmentManager fMgr, Fragment fragment, String fragmentTag ,int Layout) {
		
		if (fMgr != null) {
			FragmentTransaction ft = fMgr.beginTransaction();
			if (fragment != null) {
				ft.add(Layout, fragment, fragmentTag);
				ft.commit();
			}
		}
	}

	
	/**
	 * 显示fragment
	 * @param fragment
	 */
	public static void showFragment (FragmentManager fMgr, Fragment fragment) {
		
		FragmentTransaction ft = fMgr.beginTransaction();
		if (fragment != null) {
			ft.show(fragment);
			ft.commit();
		}
	}
	
	
	/**
	 * 移除一个fragment
	 * @param fMgr
	 * @param fragment
	 */
	public static void removeFragment(FragmentManager fMgr, Fragment fragment) {
		
		if (fragment != null) {
			FragmentTransaction ft = fMgr.beginTransaction();
			
			ft.remove(fragment);
			ft.commit();
		}
	}

	/**
	 * 返回事件
	 * @param fragment_remove
	 */
	public static void backToBaseFragment(FragmentActivity activity,Fragment fragment_remove) {
		FragmentManager fMgr = activity.getSupportFragmentManager();
		FragmentHelper.removeFragment(fMgr, fragment_remove);

		Fragment fragment_settings = fMgr.findFragmentByTag(FragmentTag.SETTING_BASE_FRAGMENT);
		if (fragment_settings != null) {
			FragmentHelper.hideAllFragment(fMgr);
			FragmentHelper.showFragment(fMgr, fragment_settings);
		} else {
			FragmentHelper.hideAllFragment(fMgr);
			fragment_settings = new SettingBaseFragment();
			FragmentHelper.addFragment(fMgr, fragment_settings,FragmentTag.SETTING_BASE_FRAGMENT, R.id.setting_contain);
		}
	}
	

	/**
	 * 隐藏所有fragment
	 */
	public static void hideAllFragment (FragmentManager fMgr) {
		
		FragmentTransaction ft = fMgr.beginTransaction();
		
		Fragment setting_base_fragment = fMgr.findFragmentByTag(FragmentTag.SETTING_BASE_FRAGMENT);
		if (setting_base_fragment != null) {
			ft.hide(setting_base_fragment);
		}

		Fragment setting_about_us_fragment = fMgr.findFragmentByTag(FragmentTag.SETTING_ABOUT_US_FRAGMENT);
		if (setting_about_us_fragment != null) {
			ft.hide(setting_about_us_fragment);
		}

		Fragment setting_contact_fragment = fMgr.findFragmentByTag(FragmentTag.SETTING_CONTACT_FRAGMENT);
		if (setting_contact_fragment != null) {
			ft.hide(setting_contact_fragment);
		}
		Fragment setting_statement_fragment = fMgr.findFragmentByTag(FragmentTag.SETTING_STATEMENT_FRAGMENT);
		if (setting_statement_fragment != null) {
			ft.hide(setting_statement_fragment);
		}
		ft.commit();
	}



}
