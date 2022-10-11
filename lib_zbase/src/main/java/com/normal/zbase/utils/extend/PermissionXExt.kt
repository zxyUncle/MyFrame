@file:JvmName("PermissionXExt")
@file:JvmMultifileClass

package com.normal.zbase.utils.extend

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.normal.zbase.R
import com.normal.zbase.utils.stringRes
import com.permissionx.guolindev.PermissionX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by zsf on 2020/11/19 14:51
 * ******************************************
 * * 权限统一处理，复制使用方法到Activity'
 * ******************************************
 */
/**
 * 使用方式
requestPermission(
mutableListOf(
Manifest.permission.READ_CONTACTS,
Manifest.permission.CAMERA,
Manifest.permission.CALL_PHONE
), {

}, {
Toast.makeText(this, "不同意的: $it", Toast.LENGTH_LONG).show()
})
 */
fun AppCompatActivity.requestPermission(permissions: MutableList<String>, onSuccess: () -> Unit, onFailed: (MutableList<String>) -> Unit) {
    launchMain {
        PermissionX.init(this)
                .permissions(permissions)
                .onExplainRequestReason { scope, deniedList ->
                    scope.showRequestReasonDialog(
                            deniedList,
                            stringRes(R.string.permission_hint),
                            stringRes(R.string.permission_confirm),
                            stringRes(R.string.permission_cancel)
                    )
                }
                .onForwardToSettings { scope, deniedList ->
                    scope.showForwardToSettingsDialog(
                            deniedList,
                            stringRes(R.string.permission_warn),
                            stringRes(R.string.permission_confirm),
                            stringRes(R.string.permission_cancel)
                    )
                }
                .request { allGranted, grantedList, deniedList ->
                    if (allGranted) {//全部同意
                        onSuccess()
                    } else {//
                        onFailed(deniedList)
                    }
                }
    }
}