@file:Suppress("UNCHECKED_CAST")
@file:JvmName("SharedPreferencesTools")
@file:JvmMultifileClass

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.normal.zbase.utils.extend.gson
import com.normal.zbase.utils.tools.ApplicationUtils
import java.lang.Exception

/**
 * Created by zsf on 2021/1/4 20:03
 * ******************************************
 * * 所有保存的数据统一处理
 * ******************************************
 */
//所有的Bean KEY 统一处理
val SharedPreferences.BASE_ArticleData: String get() = "ArticleData"  //基类的Key


//初始化延迟加载，config文件名字
val sharedPreferences: SharedPreferences by lazy {
    ApplicationUtils.context().getSharedPreferences(
        "config",
        AppCompatActivity.MODE_PRIVATE or AppCompatActivity.MODE_MULTI_PROCESS
    )
}

/**
 *  普通保存参数示例
 *  sharedPreferences.run {
 *      putString("key","")
 *  }
 */

/**
 *  保存基类示例
 *  sharedPreferences.run {
 *      saveBean(BASE_ArticleData, ArticleData())
 *      getBean<ArticleData>(BASE_ArticleData)
 *  }
 */
fun <T> SharedPreferences.pushBean(key: String, articleData: T) {
    sharedPreferences.edit().putString(key, gson.toJson(articleData))
}

inline fun <reified T> SharedPreferences.pullBean(key: String): T? {
    var jsonString = sharedPreferences.getString(key, "");
    try {
        return gson.fromJson(jsonString, T::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}




