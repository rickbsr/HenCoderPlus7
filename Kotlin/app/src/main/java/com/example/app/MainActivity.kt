package com.example.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.app.entity.User
import com.example.app.widget.CodeView
import com.example.core.utils.CacheUtils
import com.example.core.utils.Utils
import com.example.lesson.LessonActivity

// 若擴展與原本函數衝突，則失效
fun Activity.setContentView(id: Int) {}

// 擴展是靜態解析，也就是說，在編譯時期就已經被決定
fun Activity.log(text: String) {
    Log.d("Activity", "log: $text")
}

fun Context.log(text: String) {
    Log.d("Context", "log: $text")
}

val ViewGroup.firstChild: View
    get() = getChildAt(0)

/*
 * 類別的繼承和實作：
 * In Java：用「extends」繼承，用「implements」實作。
 * In Kotlin：都是用「:」，以「,」隔開即可。
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val usernameKey = "username"
    private val passwordKey = "password"

    // 在 Kotlin 中，預設皆為「不可空類型」，對應 Java 的 @NonNull；而後面加上「?」代表「可空類型」，對應 Java 的 @Nullable
//    private var et_username: EditText? = null

    // 可以藉由 lateinit 修飾，讓該變量暫時不初始化，如此就能避免一開始必須強制初始的情況，也因此其不能修飾可空變數。
    private lateinit var et_username: EditText
    private lateinit var et_password: EditText
    private lateinit var et_code: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        (window.decorView as ViewGroup).firstChild

        findViews()

        // 安全調用：等於 Java 中的 if.nn，若為空指針，就會略過不執行。
//        et_username?.setText("HenCoder")

        // 強行調用，若為空指針，就會產生「NPE」
//        et_username!!.setText("HenCoder")

        et_username.setText(CacheUtils.get(usernameKey))
        et_username.setText(CacheUtils.get(passwordKey))

    }

    override fun onClick(v: View?) {
        // is 對應 Java 中的 instanceof
        if (v is CodeView) {
//            val codeView = v as CodeView // 在 Kotlin 中，強制轉型使用 as

            /*
             * 若已經過 if.is 判斷，則就不需要再次強轉，而是會自動轉換，稱為智能轉型
             */
            v.updateCode()
        } else if (v is Button) {
            login()
        }
    }

    private fun login() {
        val username = et_username.text.toString()
        val password = et_password.text.toString()
        val code = et_code.text.toString()

        val user = User(username, password, code)

        // 與 Java 不同，Kotlin 允與方法內部方法，但是它每次被調用的時候都會生成一個對象，很影響效能
        fun verify(): Boolean {
            user.username?.length ?: 0 < 4
//        if (user.username == null || user.username!!.length < 4) {
            if (user.username?.length ?: 0 < 4) {
                Utils.toast("用户名不合法")
                return false
            }

//        if (user.password == null || user.password!!.length < 4) {
            if (user.password?.length ?: 0 < 4) {
                Utils.toast("密码不合法")
                return false
            }

            return true
        }

        /*
         * EditText.setText("String") 無法簡化成 text = "String" 是因為 setter 參數為 Editable。
         */
//        et_username.setText("String")
//        et_username.text = Editable.Factory.getInstance().newEditable("Editable")

        if (verify()) {
            CacheUtils.save(usernameKey, username)
            CacheUtils.save(passwordKey, password)
        }

        // 在 Kotlin 中，藉由 ::class.java 來獲取 Java 的 Class。
        startActivity(Intent(this, LessonActivity::class.java))
    }

//    private fun verify(user: User): Boolean {
//        user.username?.length ?: 0 < 4
////        if (user.username == null || user.username!!.length < 4) {
//        if (user.username?.length ?: 0 < 4) {
//            Utils.toast("用户名不合法")
//            return false
//        }
//
////        if (user.password == null || user.password!!.length < 4) {
//        if (user.password?.length ?: 0 < 4) {
//            Utils.toast("密码不合法")
//            return false
//        }
//
//        return true
//    }

    private fun findViews() {
        et_username = findViewById(R.id.et_username)
        et_password = findViewById(R.id.et_password)
        et_code = findViewById(R.id.et_code)

        /*
         * 平台類型：為方便跨平台調用的設計，並不符合空指針管理原則，應該減少使用。
         * - 型別後面為「!」。
         * - 具可空特性，但視為「非空」來操作。
         * - 該類型無法自行聲明，須經由 IDE 添加。
         * - 若有 @Nonable、@NonNull、@NotNull 等註解，就不會產生平台類型。
         */
        val btn_login = findViewById<Button>(R.id.btn_login)
        val code_view = findViewById<CodeView>(R.id.code_view)
        btn_login.setOnClickListener(this)
        code_view.setOnClickListener(this)
    }
}