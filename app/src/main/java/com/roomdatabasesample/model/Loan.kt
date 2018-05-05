package com.roomdatabasesample.model

import android.arch.persistence.room.*
import com.roomdatabasesample.utils.DateConverter
import java.util.*

@Entity(foreignKeys = arrayOf(
        ForeignKey(entity = Book::class, parentColumns = arrayOf("book_id")
        , childColumns = arrayOf("book_id")), ForeignKey(entity = Student::class, parentColumns = arrayOf("student_id"), childColumns = arrayOf("student_id"))))

@TypeConverters(DateConverter::class)
class Loan {

    // Fields can be public or private with getters and setters.
    @PrimaryKey
    @ColumnInfo(name = "loan_id")
    var id: Int = 0

    var startTime: Date? = null

    var endTime: Date? = null

    @ColumnInfo(name = "book_id")
    var bookId: String? = null

    @ColumnInfo(name = "student_id")
    var studentId: String? = null
}