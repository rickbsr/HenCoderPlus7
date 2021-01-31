package com.example.lesson.entity

/*
 * 主建構子可以直接加在 Class 後，此外，若在傳入參數前加入變數宣告，即可
 */
internal class Lesson constructor(var date: String?, var content: String?, var state: State?) {

    /*
     * 可以將變數宣告在建構子
     */
//    private var date: String? = date
//    private var content: String? = content
//    private var state: State? = state

    /*
     * 建構子可以直接賦值，就不需要 this.xxx = xxx;
     */
//    init {
//        this.date = date
//        this.content = content
//        this.state = state
//    }

    /*
     * Kotlin 會默認生成 Getter / Setter
     */
//    fun getState(): State? {
//        return state
//    }
//
//    fun setState(state: State?) {
//        this.state = state
//    }
//
//    fun getDate(): String? {
//        return date
//    }
//
//    fun setDate(date: String?) {
//        this.date = date
//    }
//
//    fun getContent(): String? {
//        return content
//    }
//
//    fun setContent(content: String?) {
//        this.content = content
//    }

    /*
     * 在 Java 中，Enum 宣告其實就是繼承 Enum 類的 Class。
     */
    enum class State {
        PLAYBACK {
            override fun stateName(): String {
                return "有回放"
            }
        },
        LIVE {
            override fun stateName(): String {
                return "正在直播"
            }
        },
        WAIT {
            override fun stateName(): String {
                return "等待中"
            }
        };

        abstract fun stateName(): String?
    }
}