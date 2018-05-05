package com.roomdatabasesample.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.TypeConverters
import com.roomdatabasesample.utils.DateConverter
import java.util.*

@TypeConverters(DateConverter::class)
class LoanWithStudentAndBook {
    var id: String? = null

    @ColumnInfo(name = "title")
    var bookTitle: String? = null

    @ColumnInfo(name = "name")
    var userName: String? = null

    var startTime: Date? = null

    var endTime: Date? = null
}