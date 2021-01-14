package com.example.lesson.entity

internal class Lesson {
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

    private var date: String? = null
    private var content: String? = null
    private var state: State? = null

    constructor(date: String?, content: String?, state: State?) {
        this.date = date
        this.content = content
        this.state = state
    }

    fun getState(): State? {
        return state
    }

    fun setState(state: State?) {
        this.state = state
    }

    fun getDate(): String? {
        return date
    }

    fun setDate(date: String?) {
        this.date = date
    }

    fun getContent(): String? {
        return content
    }

    fun setContent(content: String?) {
        this.content = content
    }
}