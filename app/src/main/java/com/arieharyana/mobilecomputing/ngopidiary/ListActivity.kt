package com.arieharyana.mobilecomputing.ngopidiary

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_list_chat.*
import com.androidnetworking.error.ANError
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.interfaces.OkHttpResponseAndStringRequestListener
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import okhttp3.Response
import java.io.IOException
import java.util.*

class ListActivity : AppCompatActivity(){

    private val TAG = ListActivity::class.java.simpleName
    private lateinit var adapter: ItemAdapter
    private val mNotificationTime = Calendar.getInstance().timeInMillis + 5000 //Set after 5 seconds from the current time.
    private var mNotified = false



    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_chat)

        FirebaseApp.initializeApp(this)
        initView()

        val intent = intent
        val message = intent.getStringExtra("message")
        if(!message.isNullOrEmpty()) {
            AlertDialog.Builder(this)
                    .setTitle("Notification")
                    .setMessage(message)
                    .setPositiveButton("Ok", { dialog, which -> }).show()
        }

        rv_list?.layoutManager = LinearLayoutManager(this)
        rv_list?.setHasFixedSize(true)
        rv_list?.isNestedScrollingEnabled = false
        adapter = ItemAdapter(this)
        rv_list?.adapter = adapter

        pb_frame?.visibility = View.VISIBLE
        rv_list.visibility = View.GONE

        AndroidNetworking.get("https://api.flickr.com/services/feeds/photos_public.gne")
                .addQueryParameter("tags", "cappucino")
                .addQueryParameter("format", "json")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsOkHttpResponseAndString(object : OkHttpResponseAndStringRequestListener {
                    override fun onResponse(okHttpResponse: Response?, response: String?) {
                        pb_frame.visibility = View.GONE
                        rv_list.visibility = View.VISIBLE

                        Log.e("string", response.toString())
                        val val1 = response.toString().replace("jsonFlickrFeed(", "")
                        val result = val1.replace(val1[val1.length - 1].toString(), "")
                        Log.e("result", result)
                        val check = Gson().fromJson<DataModel>(result, DataModel::class.java)
                        title_text?.text = check.title
                        adapter.update(check.items!!)
                        /*val val1 = t.toString().replace("jsonFlickrFeed(", "")
                        val result = val1.replace(val1[val1.length - 1].toString(), "")
                        Log.e("result", result)
                        val check = Gson().fromJson<DataModel>(result, DataModel::class.java)
                        title_text?.text = check.title
                        adapter.update(check.items!!)*/

                        if (!mNotified) {
                            NotificationUtils().setNotification(mNotificationTime, this@ListActivity)
                        }
                    }

                    override fun onError(error: ANError?) {
                        if (error?.errorCode !== 0) {
                            // received error from server
                            // error.getErrorCode() - the error code from server
                            // error.getErrorBody() - the error body from server
                            // error.getErrorDetail() - just an error detail
                            Log.d(TAG, "onError errorCode : " + error?.errorCode)
                            Log.d(TAG, "onError errorBody : " + error?.errorBody)
                            Log.d(TAG, "onError errorDetail : " + error?.errorDetail)
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d(TAG, "onError errorDetail : " + error.errorDetail)
                        }
                        pb_frame.visibility = View.GONE
                        rv_list.visibility = View.VISIBLE
                    }

                })
                /*.getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(t: JSONObject?) {
                        pb_frame.visibility = View.GONE
                        rv_list.visibility = View.VISIBLE

                        Log.e("string", t.toString())
                        val val1 = t.toString().replace("jsonFlickrFeed(", "")
                        val result = val1.replace(val1[val1.length - 1].toString(), "")
                        Log.e("result", result)
                        val check = Gson().fromJson<DataModel>(result, DataModel::class.java)
                        title_text?.text = check.title
                        adapter.update(check.items!!)
                    }

                    override fun onError(error: ANError) {
                        if (error.errorCode !== 0) {
                            // received error from server
                            // error.getErrorCode() - the error code from server
                            // error.getErrorBody() - the error body from server
                            // error.getErrorDetail() - just an error detail
                            Log.d(TAG, "onError errorCode : " + error.errorCode)
                            Log.d(TAG, "onError errorBody : " + error.errorBody)
                            Log.d(TAG, "onError errorDetail : " + error.errorDetail)
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d(TAG, "onError errorDetail : " + error.errorDetail)
                        }
                        pb_frame.visibility = View.GONE
                        rv_list.visibility = View.VISIBLE
                    }
                })*/

        /*Rx2AndroidNetworking.get("https://api.flickr.com/services/feeds/photos_public.gne?tags=kitten&format=json")
                .build()
                .jsonObjectObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : io.reactivex.Observer<JSONObject> {
                    override fun onComplete() {
                        pb_frame.visibility = View.GONE
                        rv_list.visibility = View.VISIBLE
                    }

                    override fun onSubscribe(d: Disposable) {
                        pb_frame?.visibility = View.VISIBLE
                        rv_list.visibility = View.GONE
                    }

                    override fun onNext(t: JSONObject) {
                        Log.e("string", t.toString())
                        val val1 = t.toString().replace("jsonFlickrFeed(", "")
                        val result = val1.replace(val1[val1.length - 1].toString(), "")
                        Log.e("result", result)
                        val check = Gson().fromJson<DataModel>(result, DataModel::class.java)
                        title_text?.text = check.title
                        adapter.update(check.items!!)

                    }

                    override fun onError(e: Throwable) {
                        pb_frame.visibility = View.GONE
                        rv_list.visibility = View.VISIBLE
                    }

                })*/
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