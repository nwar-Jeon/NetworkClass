package com.dsm.unlimited.networkclass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dsm.unlimited.networkclass.databinding.ActivityMainBinding
import io.reactivex.observers.DisposableObserver
import io.reactivex.subjects.PublishSubject
import java.net.URI

class MainActivity : AppCompatActivity() {

    private val binding by lazy { DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main) }

    private val adapter = ChatListAdapter()

    private val viewModel by lazy { ViewModelProviders.of(this)[MainViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.activity = this
        binding.vm = viewModel
        binding.lifecycleOwner = this
        binding.recyclerview initAdapter adapter initLM LinearLayoutManager(this)

        viewModel.message.observe(this, Observer {
            adapter.addItem(it)
            binding.recyclerview toLastItem adapter.itemCount-1
        })

        binding.button.setOnClickListener {
            viewModel.onCreate()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    fun sendMessage() {
        adapter.addItem(viewModel.sendTextAndReturn())
        binding.recyclerview toLastItem adapter.itemCount-1
    }
}

