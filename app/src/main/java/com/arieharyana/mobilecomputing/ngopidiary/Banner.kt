package com.arieharyana.mobilecomputing.ngopidiary

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide

class Banner : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_banner, container, false)

        Glide.with(getActivity()!!.getApplicationContext())
                .load("https://images.cdn1.stockunlimited.net/preview1300/coffee-shop-banner_1535155.jpg")
                .into(view!!.findViewById(R.id.imageView))

        return view
    }
}
