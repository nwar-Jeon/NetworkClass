package com.dsm.unlimited.networkclass

import android.util.Log

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake

import java.net.URI

import io.reactivex.subjects.PublishSubject

class WebSocketHandler(serverUri: URI?) : WebSocketClient(serverUri) {

    private var publishSubject: PublishSubject<ChattingEntity>? = null

    fun setPublishSubject(publishSubject: PublishSubject<ChattingEntity>) {
        this.publishSubject = publishSubject
        publishSubject.subscribe()
    }

    override fun onOpen(handshakedata: ServerHandshake) {
        Log.e(this.javaClass.simpleName, "onOpen")
        send("JOIN")
    }

    override fun onMessage(message: String) {
        Log.e(this.javaClass.simpleName, "onMessage : $message")
        publishSubject!!.onNext(ChattingContent(message))
    }

    override fun onClose(code: Int, reason: String, remote: Boolean) {
        publishSubject!!.onComplete()
    }

    override fun onError(ex: Exception) {

    }

    private fun splitMessage(message: String): Array<String> {
        return message.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    }

    private fun messageListToPair(strings: Array<String>): Pair<Int, String> {
        val seatNum = Integer.valueOf(strings[0].trim { it <= ' ' })
        val message = strings[1].trim { it <= ' ' }
        return seatNum to message
    }
}
