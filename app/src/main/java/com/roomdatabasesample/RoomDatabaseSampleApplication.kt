package com.roomdatabasesample

import android.app.Application


class RoomDatabaseSampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

    }


    companion object {
        var instance: RoomDatabaseSampleApplication? = null
    }


}