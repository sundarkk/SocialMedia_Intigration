package com.sundar.task

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.DisplayNameSources.EMAIL
import android.util.Log
import android.widget.Toast
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import java.util.*

class LoginActivity : AppBaseActivity() {

    private val activity = this@LoginActivity
    private val TAG = "LoginActivityTag"
    private var RC_SIGN_IN: Int = 100
    lateinit var mGoogleSignInClient: GoogleSignInClient

    private var callbackManager: CallbackManager? = null
    var EMAIL = "email"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Todo Google login
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
        val account = GoogleSignIn.getLastSignedInAccount(activity)

        init()
    }

    private fun init() {

        otp_layout.setOnClickListener {
            startActivity(Intent(activity, OtpLoginActivity::class.java))
            finish()
        }

        google_layout.setOnClickListener {
            signIn()
        }

        callbackManager = CallbackManager.Factory.create()
        fb_login_button.setReadPermissions(Arrays.asList<String>("email", "public_profile"))
        checkLoginStatus()
        fb_login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {

                startActivity(Intent(activity, HomeActivity::class.java))
                finish()

            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException) {

            }
        })

        facebook_layout.setOnClickListener {
            fb_login_button.performClick()
        }

    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val acct = GoogleSignIn.getLastSignedInAccount(activity)
            if (acct != null) {
                val personName = acct.displayName
                val personGivenName = acct.givenName
                val personFamilyName = acct.familyName
                val personEmail = acct.email
                val personId = acct.id
                val personPhoto: Uri? = acct.photoUrl

                Log.d(TAG, "lg_personName" + personName.toString())
                Log.d(TAG, "lg_personEmail: " + personEmail.toString())
                Log.d(TAG, "lg_personId" + personId.toString())
                val email = personEmail

                Toast.makeText(
                    activity,
                    getString(R.string.google_login_succesfull),
                    Toast.LENGTH_SHORT
                ).show()
            }
            startActivity(Intent(activity, HomeActivity::class.java))
            finish()

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            // updateUI(null)
        }
    }

    internal var tokenTracker: AccessTokenTracker = object : AccessTokenTracker() {
        override fun onCurrentAccessTokenChanged(
            oldAccessToken: AccessToken?,
            currentAccessToken: AccessToken?
        ) {
            if (currentAccessToken == null) {
//                profile_name.setText("")
//                profile_email.setText("")
//                profile_pic.setImageResource(0)
                Toast.makeText(this@LoginActivity, "User Logged out", Toast.LENGTH_LONG).show()
            } else
                loadUserProfile(currentAccessToken)
        }
    }

    private fun loadUserProfile(newAccessToken: AccessToken?) {

        val request = GraphRequest.newMeRequest(
            newAccessToken
        ) { `object`, response ->
            try {
                val first_name = `object`.getString("first_name")
                val last_name = `object`.getString("last_name")
                val email = `object`.getString("email")
                EMAIL = `object`.getString("email")
                val id = `object`.getString("id")
                val image_url = "https://graph.facebook.com/$id/picture?type=normal"
                Log.e(TAG, "facebook Data------>$first_name,$last_name,$email$id")

                Log.d(TAG, "facebook_name: " + first_name)
                Log.d(TAG, "facebook_email: " + EMAIL)
                Log.d(TAG, "facebook_id: " + id)

                Toast.makeText(
                    activity,
                    getString(R.string.google_facebook_succesfull),
                    Toast.LENGTH_SHORT
                ).show()

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        val parameters = Bundle()
        parameters.putString("fields", "first_name,last_name,email,id")
        request.parameters = parameters
        request.executeAsync()

    }

    private fun checkLoginStatus() {
        if (AccessToken.getCurrentAccessToken() != null) {
            loadUserProfile(AccessToken.getCurrentAccessToken())
        }
    }

}