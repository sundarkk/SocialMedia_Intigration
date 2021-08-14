package com.sundar.task

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.content.ContextCompat

class SplashActivity : AppBaseActivity() {

    private val TAG = "SSTag"
    private val activity = this@SplashActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

    }

    private fun init() {

        goToNextScreen(2000)
    }

    private fun goToNextScreen(delay: Long) {

        val intent = Intent(activity, LoginActivity::class.java)
        Handler().postDelayed({
            startActivity(intent)
            finish()
        }, delay)

    }

}