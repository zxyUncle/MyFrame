package com.normal.zbase.arouter;

public class RouterConstants {

    public static final int INTERCEPT_CODE_LOGIN = 101;//登录拦截

    //路由路径
    public static class Path {

        //app start
        public static final String APP_MAIN = "/app/main";

    }

    //路由key--value
    public static class KV {
        public static final String PAGE_PATH = "page_path"; // 用于获取目标页面名称的path
        public static final String PAGE_HIDE_TOOLBAR = "page_hide_toolbar"; // 是否需要隐藏标题栏
        public static final String PAGE_SHOW_CLOSE = "page_show_close"; // 是否需要显示关闭按钮
    }

}