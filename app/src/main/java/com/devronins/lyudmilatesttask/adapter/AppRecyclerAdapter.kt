package com.devronins.lyudmilatesttask.adapter

import com.devronins.lyudmilatesttask.listener.AdapterListener
import java.util.ArrayList

class AppRecyclerAdapter<T>() : AppRecyclerBaseAdapter() {

    private var items: ArrayList<T>? = ArrayList()
    private var resourceId: Int = 0
    private var recyclerListener: AdapterListener? = null

    constructor(_resourceId: Int,
                _items: ArrayList<T>?,
                _recyclerListener: AdapterListener?) : this() {
        this.items = _items
        this.resourceId = _resourceId
        this.recyclerListener = _recyclerListener
    }

    override fun getItemCount(): Int = this.items?.size ?: 0

    override fun getObjForPosition(position: Int): Any = items!![position] as Any

    override fun getLayoutIdForPosition(position: Int): Int = resourceId

    override fun getRecyclerListener(): AdapterListener? = recyclerListener

}