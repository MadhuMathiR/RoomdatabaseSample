package com.roomdatabasesample.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity()
class Student {
    @PrimaryKey
    @ColumnInfo(name = "student_id")
    var id: Int = 0

    var firstName: String?=null

    var lastName: String? = null

    var age: Int = 0
}