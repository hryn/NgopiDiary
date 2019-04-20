package com.arieharyana.mobilecomputing.ngopidiary

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_list.view.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


class ItemAdapter(context: Context) : Adapter<ItemAdapter.Holder>() {


    private var models: ArrayList<ItemModel>? = null
    private var context: Context? = null

    init {
        this.context = context
        models = ArrayList()
    }

    fun update(models: ArrayList<ItemModel>) {
        this.models = models
        notifyDataSetChanged()
    }

    fun addAll(models: ArrayList<ItemModel>) {
        this.models?.clear()
        this.models?.addAll(models)
        notifyDataSetChanged()
    }

    fun add(model: ItemModel) {
        models!!.add(model)
        notifyDataSetChanged()
    }

    fun get(): ArrayList<ItemModel> = models ?: ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))

    //override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind(models!![position])

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(models!![position])
    }

    override fun getItemCount(): Int = models!!.size

    class Holder(superView: View) : RecyclerView.ViewHolder(superView) {

        @SuppressLint("SetTextI18n")
        fun bind(item: ItemModel) = with(itemView) {
            val requestOptions = RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
            Glide.with(this).load(item.media?.m)
                    .apply(requestOptions).into(img)
            txt_name.text = item.title
            txt_tags.text = item.tags

            itemView.setOnClickListener {
                val i = Intent(context, DetailActivity::class.java)
                i.putExtra("data", item)
                context.startActivity(i)
            }
        }
    }


}