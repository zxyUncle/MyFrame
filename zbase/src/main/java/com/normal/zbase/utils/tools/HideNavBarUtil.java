package com.normal.zbase.utils.tools;

import android.os.Build;
import android.view.View;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.Spinner;

import java.lang.reflect.Field;

public class HideNavBarUtil {
    public static void hideBottomUIMenu(View v) {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            v.setSystemUiVisibility(uiOptions);
        }

    }

    //调起输入法 用于隐藏输入法后隐藏导航栏
    public static void hideWhenSystemUiVisible(View v) {
        v.setOnSystemUiVisibilityChangeListener(visibility -> {
            if (visibility == View.SYSTEM_UI_FLAG_VISIBLE) {
                hideBottomUIMenu(v);
            }
        });
    }

    /**
     * spinner里也有popwindow 会调起导航栏
     *
     * @param spinner 尝试隐藏spinner弹出时的导航栏
     */
    public static void hideSpinnerSystemUi(Spinner spinner) {
        Field mPopup = null;
        try {
            mPopup = spinner.getClass().getDeclaredField("mPopup");
            mPopup.setAccessible(true);
            ListPopupWindow listPopupWindow = (ListPopupWindow) mPopup.get(spinner);
            Field mPopup1 = listPopupWindow.getClass().getSuperclass().getDeclaredField("mPopup");
            mPopup1.setAccessible(true);
            PopupWindow popupWindow = (PopupWindow) mPopup1.get(listPopupWindow);
            popupWindow.setFocusable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

