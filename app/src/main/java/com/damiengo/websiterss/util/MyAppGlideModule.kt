package com.damiengo.websiterss.util

import android.content.Context
import android.util.Log
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.GlideBuilder

@GlideModule
class MyAppGlideModule : AppGlideModule() {

    /*override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setLogLevel(Log.VERBOSE)
    }*/

}
