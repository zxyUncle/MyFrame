package com.normal.zbase.arouter;

public class RouterConstants {

    public static final int INTERCEPT_CODE_LOGIN = 101;//登录拦截

    //路由路径
    public static class Path {

        //app start
        public static final String APP_MAIN = "/app/main";
        public static final String APP_LOGIN = "/app/login";
        //app end

        //base start
        public static final String BASE_CONTAINER = "/base/container";
        public static final String BASE_WEB = "/base/web";
        public static final String BASE_WEB_NO_TITLE = "/base/web_no_title";
        public static final String BASE_NO_PATH = "/base/no_path";
        public static final String BASE_NO_NETWORK = "/base/no_network";
        //base end

        //shop start
        public static final String SHOP = "/shop/shop";
        public static final String SHOP_GOODS_LIST = "/shop/goods_list";
        public static final String SHOP_ORDER = "/shop/order";
        public static final String SHOP_ORDER_LIST = "/shop/order_list";//订单列表
        public static final String SHOP_ORDER_DETAIL = "/shop/order_detail";//订单详情
        public static final String SHOP_PAY_TYPE = "/shop/pay_type";
        public static final String SHOP_PAY_CONFIRM = "/shop/pay_confirm";
        public static final String SHOP_PAY_COMPLETE = "/shop/pay_complete";
        public static final String SHOP_SELECT_ROOM_NUMBER = "/shop/select_room_number";//选择房间号

        //shop end

        //my start
        public static final String MY_LOGIN = "/my/login";
        public static final String MY_LOGIN_BOTTOM = "/my/login_bottom";
        public static final String MY_LOGIN_FACE = "/my/login_face";
        public static final String MY_LOGIN_CONFIRM = "/my/login_confirm";
        public static final String MY_ADDRESS_CHOOSE = "/my/address_choose";
        public static final String MY_ADDRESS_LIST = "/my/address_list";
        public static final String MY_APPLET = "/my/applet";
        //my end

    }

    //路由key--value
    public static class KV {
        public static final String PAGE_PATH = "page_path"; // 用于获取目标页面名称的path
        public static final String PAGE_TITLE = "page_title"; // 用于获取目标页面名称的key
        public static final String PAGE_HIDE_TOOLBAR = "page_hide_toolbar"; // 是否需要隐藏标题栏
        public static final String PAGE_SHOW_CLOSE = "page_show_close"; // 是否需要显示关闭按钮
        public static final String PAGE_URL = "page_url"; //webView的url
        public static final String PAGE_INDEX = "page_index";
        public static final String PAGE_INFO_LIST = "page_info_list";
        public static final String PAGE_INFO = "page_info";
        public static final String PAGE_TYPE = "page_type";
        public static final String PAGE_ORDER_DETAIL = "page_info_detail";//订单详情
    }

}