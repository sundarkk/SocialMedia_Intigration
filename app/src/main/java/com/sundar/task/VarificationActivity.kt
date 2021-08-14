package com.sundar.task

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_otp_login.*
import kotlinx.android.synthetic.main.activity_otp_login.btn_send
import kotlinx.android.synthetic.main.activity_varification.*

class VarificationActivity : AppBaseActivity() {

    private val activity = this@VarificationActivity
    private val TAG = "VarificationActivityTag"

    private var mVerificationId: String? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_varification)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        init()

    }

    private fun init() {
        val mobileNumber = intent.getStringExtra("mobileNumber")
        mobile_num.text = mobileNumber

        mAuth = FirebaseAuth.getInstance()

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+91$mobileNumber", // Phone number to verify
            60, // Timeout duration
            java.util.concurrent.TimeUnit.SECONDS, // Unit of timeout
            this, // Activity (for callback binding)
            callbacks
        ) //

        btn_send.setOnClickListener {
            validateData()
        }
        back.setOnClickListener {
          //  finish()
        }
    }

    private fun validateData() {

        val otp: String =
            (editOTP_Txt1.getText().toString() + editOTP_Txt2.getText()
                .toString() + editOTP_Txt3.getText()
                .toString()
                    + editOTP_Txt4.getText().toString())
        when {
            editOTP_Txt1.text!!.isEmpty() -> {

                Toast.makeText(activity, getString(R.string.enter_valid_otp), Toast.LENGTH_SHORT)
                    .show()
                editOTP_Txt1.requestFocus()
            }
            //Todo normal password validation
            editOTP_Txt2.text!!.isEmpty() -> {
                Toast.makeText(activity, getString(R.string.enter_valid_otp), Toast.LENGTH_SHORT)
                    .show()
                editOTP_Txt2.requestFocus()
            }
            editOTP_Txt3.text!!.isEmpty() -> {
                Toast.makeText(activity, getString(R.string.enter_valid_otp), Toast.LENGTH_SHORT)
                    .show()
                editOTP_Txt3.requestFocus()
            }
            editOTP_Txt4.text!!.isEmpty() -> {
                Toast.makeText(activity, getString(R.string.enter_valid_otp), Toast.LENGTH_SHORT)
                    .show()
                editOTP_Txt4.requestFocus()
            }
            else -> {


            }
        }


    }

    private fun verifyVerificationCode(code: String) {
        //creating the credential
        val credential = PhoneAuthProvider.getCredential(mVerificationId!!, code)

        //signing the user
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(
                this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        //verification successful we will start the profile activity
                        Toast.makeText(activity, getString(R.string.success), Toast.LENGTH_LONG)
                            .show()
                        startActivity(Intent(activity, HomeActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(activity, getString(R.string.failed), Toast.LENGTH_LONG)
                            .show()
                    }
                })
    }

    private val callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                val code = phoneAuthCredential.smsCode
                if (code != null) {
                    verifyVerificationCode(code)
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                if (e is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
                } else if (e is FirebaseTooManyRequestsException) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onCodeSent(
                s: String,
                forceResendingToken: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(s, forceResendingToken)
                mVerificationId = s
                //mResendToken = forceResendingToken
            }
        }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}