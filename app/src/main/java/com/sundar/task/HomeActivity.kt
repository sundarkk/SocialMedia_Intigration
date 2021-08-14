package com.sundar.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sundar.task.adapter.DetailsAdapter
import com.sundar.task.adapter.TittleAdapter
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val activity = this@HomeActivity
    private val TAG = "HomeActivityTag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        init()

    }

    private fun init() {

        tittle_recyclerview.adapter = TittleAdapter(activity)
        details_recyclerview.adapter = DetailsAdapter(activity)

    }
}