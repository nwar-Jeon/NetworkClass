package com.dsm.unlimited.networkclass

import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

infix fun WebSocketHandler.sendText(message: String?): String {
    this.send(message ?: "")
    return message ?: ""
}

infix fun <T> PublishSubject<T>.init(observer : DisposableObserver<T>) = this.apply {
    subscribeOn(Schedulers.io())
    observeOn(AndroidSchedulers.mainThread())
    subscribeWith(observer)
}

infix fun <T> PublishSubject<T>.next(func: (T) -> Unit): PublishSubject<T> =
    this.apply { subscribe(func) }

infix fun <T> PublishSubject<T>.error(func: (Throwable) -> Unit): PublishSubject<T> =
    this.apply { doOnError(func) }

infix fun RecyclerView.initAdapter(adapter: ChatListAdapter): RecyclerView =
    this.apply { this.adapter = adapter }

infix fun RecyclerView.initLM(lm: RecyclerView.LayoutManager): RecyclerView =
    this.apply { layoutManager = lm }

infix fun RecyclerView.toLastItem(position : Int) = this.scrollToPosition(position)