package com.recycyclersample.callbacks

import android.view.View

/**
 * Created by KHEMRAJ on 10/17/2018.
 */
interface OnItemClickListener<Object> {
    fun onItemClick(view: View?, `object`: Object)
}