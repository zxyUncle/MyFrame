package com.normal.zbase.subject;

import androidx.lifecycle.ViewModel;

/**
 * viewMoedel -> ViewModelStore >componentActivity->ViewModelStoreOwner
 * 1、 如果系统销毁或重新创建界面控制器，则存储在其中的任何瞬态界面相关
 * 2、确保系统在其销毁后清理这些调用以避免潜在的内存泄漏，造成资源的浪费(重新网络请求数据)
 * 3、让状态管理独立于 视图层(activity或fragment) 之外，保存状态数据，避免数据丢失导致的数据重建
 * 4、Activity 中的两个或更多 Fragment 需要相互通信,通过作用域可控的 共享ViewModel ，进行跨界面通信
 */
public class BaseVM extends ViewModel {

}
