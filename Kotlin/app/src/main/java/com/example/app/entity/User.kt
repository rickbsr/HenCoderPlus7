package com.example.app.entity

// Kotlin 的 Any 對應 Java 的 Unit
//class User constructor(var username: String?, var password: String?, var code: String?) /*: Any*/ {

// constructor 可以省略
data class User(var username: String?, var password: String?, var code: String?) {
    constructor() : this(null, null, null)

//    var username: String? = null
//    var password: String? = null
//    var code: String? = null

    // 不能在 setter/getter 內使用 this，會造成無限循環；要使用內部屬性 field，此外，默認就會有 setter/getter，因此可以省略。
//    var username: String? = null
//        set(value) {
//            field = value
//        }
//        get() {
//            return field
//        }

//    var password: String? = null

//    @JvmField // 藉由 @JvmField 生成公開成員變量，而不會生成默認的 setter/getter。
//    var code: String? = null

//    constructor() {}

//    constructor(username: String?, password: String?, code: String?) : this() {
//        this.username = username
//        this.password = password
//        this.code = code
//    }
}