package com.dsm.unlimited.networkclass

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatListAdapter() : RecyclerView.Adapter<ChatListViewHolder>() {

    val list = ArrayList<ChattingEntity>()

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder
        = ChatListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_chatting,parent,false))

    fun addItem(item : ChattingEntity?) {
        if(item==null) return
        list.add(item)
        notifyItemInserted(list.size-1)
    }
}

class ChatListViewHolder(val v : View) : RecyclerView.ViewHolder(v) {
    fun bind(item : ChattingEntity) {
        if(item.isMine()) {
            moveToRight(v)
            backGroundYellow(v)
        } else {
            moveToLeft(v)
            backGroundWhite(v)
        }
        setText(v, item.toString())
    }

    fun setText(v : View, str : String) {
        v.findViewById<TextView>(R.id.chat_chatting_tv).text = str
    }

    fun moveToRight(v : View) {
        v.findViewById<LinearLayout>(R.id.chat_background).gravity = Gravity.RIGHT
    }

    fun moveToLeft(v : View) {
        v.findViewById<LinearLayout>(R.id.chat_background).gravity = Gravity.LEFT
    }

    fun backGroundYellow(v : View) {
        v.findViewById<TextView>(R.id.chat_chatting_tv).backgroundTintList = ColorStateList.valueOf(Color.YELLOW)
    }

    fun backGroundWhite(v : View) {
        v.findViewById<TextView>(R.id.chat_chatting_tv).backgroundTintList = ColorStateList.valueOf(Color.WHITE)
    }
}