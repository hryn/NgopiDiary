package com.arieharyana.mobilecomputing.ngopidiary

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val data = intent.getSerializableExtra("data") as ItemModel

        val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
        Glide.with(this).load(data.media?.m)
                .apply(requestOptions).into(img)

        txt_name?.text = data.title
        txt_tags?.text = data.tags
        txt_author?.text = data.author

        wv?.settings!!.javaScriptEnabled = true
        wv?.settings!!.javaScriptCanOpenWindowsAutomatically = true
        wv?.loadData(data.description,"text/html; charset=UTF-8", null)
    }


}