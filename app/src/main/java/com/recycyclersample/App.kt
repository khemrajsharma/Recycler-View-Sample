package com.recycyclersample

import android.app.Application
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.recycyclersample.retrofit.ApiClient
import com.recycyclersample.retrofit.ApiServices

class App : Application() {
    var apiServices: ApiServices? = null
        private set

    override fun onCreate() {
        super.onCreate()
        appRes = resources
        instance = this
        apiServices = ApiClient.getApiInterface(null)
    }

    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

    var handler: Handler? = null

    fun toast(msg: String?) {
        if (handler == null) handler = Handler(Looper.getMainLooper())
        handler!!.post { Toast.makeText(instance, msg, Toast.LENGTH_SHORT).show() }
    }

    companion object {
        @get:Synchronized
        var instance: App? = null
            private set
        val TAG = App::class.java.simpleName
        var appRes: Resources? = null
    }
}