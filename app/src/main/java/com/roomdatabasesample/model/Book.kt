package com.roomdatabasesample.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Book {
    @PrimaryKey
    @ColumnInfo(name="book_id")
    var id:Int=0

    var title:String ?=null
}