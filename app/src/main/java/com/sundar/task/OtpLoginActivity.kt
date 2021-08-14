package com.sundar.task

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_otp_login.*
import java.util.*

class OtpLoginActivity : AppBaseActivity() {

    private val activity = this@OtpLoginActivity
    private val TAG = "OtpLoginActivityTag"

    /*  private lateinit var auth: FirebaseAuth;
      auth = Firebase.auth
  */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_login)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        init()
    }

    private fun init() {

        spinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            CountryData.countryNames
        )

        btn_send.setOnClickListener {
            val code =
                CountryData.countryAreaCodes[spinner.selectedItemPosition]
            if (mobileNumber.text.count() == 10) {

                val intent = Intent(activity, VarificationActivity::class.java)
                intent.putExtra("mobileNumber", code + mobileNumber.text.toString())
                activity.startActivity(intent)
                finish()

            } else {
                Toast.makeText(activity, getString(R.string.ten_digit), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}