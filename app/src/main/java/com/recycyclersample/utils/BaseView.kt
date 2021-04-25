package com.recycyclersample.utils

/**
 * Created by KHEMRAJ on 25/11/15.
 */
interface BaseView {
    fun initView()
    fun showProgressBar()
    fun hideProgressBar()
    fun exception(e: Exception?)
}