package com.arieharyana.mobilecomputing.ngopidiary

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_login2.*
import java.io.IOException

class LoginActivity : AppCompatActivity() {
    private val TAG = "MyFirebaseToken"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        FirebaseApp.initializeApp(this)
        initView()

        val defaultEmail: String = "hello@utama.ac.id"
        val defaultPassword: String = "1234"

        btn_login.setOnClickListener{
            val isEmailValid: Boolean = defaultEmail == ed_email.text.toString()
            val isPasswordValid: Boolean = defaultPassword == ed_passsword.text.toString()
            val isAuthValid = isEmailValid && isPasswordValid

            val message: String = if(isAuthValid) {
                "Success"
            } else {
                "Failed"
            }
            val snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
            val sbView = snackbar.view

            val respColor = if(isAuthValid){
                R.color.green200
            }else{
                R.color.red800
            }


            sbView.setBackgroundColor(ContextCompat.getColor(this, respColor))
            val textView = sbView.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView
            textView.setTextColor(ContextCompat.getColor(this, R.color.white))
            snackbar.show()

            if(isAuthValid) {
                val myIntent = Intent(baseContext, ListActivity::class.java)
                startActivity(myIntent)
            }

        }

        btn_help_signin.setOnClickListener {

        }

        btn_signup.setOnClickListener {

        }
    }


    private fun initView() {
        //This method will use for fetching Token
        Thread(Runnable {
            try {
                Log.i(TAG, FirebaseInstanceId.getInstance().getToken(getString(R.string.SENDER_ID), "FCM"))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }).start()
    }
}
