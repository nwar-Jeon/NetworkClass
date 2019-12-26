package com.dsm.unlimited.networkclass

class ChattingContent : ChattingEntity {
    var content: String
    var isMyChat: Boolean = false

    constructor(content: String) {
        this.content = content
        isMyChat = false
    }

    constructor(content: String, myChat: Boolean) {
        this.content = content
        this.isMyChat = myChat
    }

    override fun toString(): String = content

    override fun isMine(): Boolean = isMyChat
}
