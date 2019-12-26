package com.dsm.unlimited.networkclass

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.observers.DisposableObserver
import io.reactivex.subjects.PublishSubject
import java.net.URI
import java.net.URISyntaxException

class MainViewModel() : ViewModel() {

    private val socket by lazy { createSocket(getUrl()) }
    val message = MutableLiveData<ChattingEntity>()
    val inputMessage = MutableLiveData<String>()
    private val publishSubject: PublishSubject<ChattingEntity> = PublishSubject.create()

    fun onCreate() {
        connectSocket(socket)
        publishSubject init Observer(message)
    }

    fun onDestroy() = closeSocket(socket)

    fun sendTextAndReturn(): ChattingEntity =
        ChattingContent(socket sendText inputMessage.value, true)

    private fun createSocket(url: URI?) =
        WebSocketHandler(url).apply { setPublishSubject(publishSubject) }

    private fun connectSocket(socket: WebSocketHandler) = socket.connect()

    private fun closeSocket(socket: WebSocketHandler) = socket.close()

    fun getUrl(): URI? =
        try {
            URI(protocol + ipAddress + port + socketAddress)
        } catch (e: URISyntaxException) {
            null
        }
    val protocol = "ws://"

    var ipAddress = "10.0.2.2"

    val port = ":8080"

    val socketAddress = "/ws/socket"
}

class Observer(val message: MutableLiveData<ChattingEntity>) :
    DisposableObserver<ChattingEntity>() {

    override fun onNext(t: ChattingEntity) = message.postValue(t)

    override fun onError(e: Throwable) = error(e)

    override fun onComplete() {
        dispose()
    }
}

