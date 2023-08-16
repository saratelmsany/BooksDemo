package com.sara.booksdemo

import android.app.Application
import android.content.Context

class App : Application() {

     companion object {
        lateinit var mContext: Context

         fun getContext(): Context {
             return mContext
         }
    }
    override fun onCreate() {
        super.onCreate()
        mContext = this
    }

}