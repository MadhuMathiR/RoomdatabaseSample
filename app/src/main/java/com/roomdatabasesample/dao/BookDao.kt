package com.roomdatabasesample.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.TypeConverters
import android.arch.persistence.room.Update

import java.util.Date

import android.arch.persistence.room.OnConflictStrategy.IGNORE
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.roomdatabasesample.model.Book
import com.roomdatabasesample.utils.DateConverter


@Dao
@TypeConverters(DateConverter::class)
interface BookDao {

    @Query("select * from book where book_id = :id")
    fun loadBookById(id: Int): Book

    @Query("select * from book inner join  loan  on loan.book_id=Book.book_id inner join student on student.student_id=loan.student_id where firstName=:firstName")
    fun findBooksBorrowedByStudentName(firstName: String): LiveData<List<Book>>?
    @Query("select * from book inner join  loan  on loan.book_id=Book.book_id inner join student on student.student_id=loan.student_id where firstName=:firstName and loan.endTime>:afterDate")
    fun findBooksBorrowedByStudentNameAfterDate(firstName: String,afterDate: Date): LiveData<List<Book>>

    @Query("SELECT * FROM book INNER JOIN Loan ON Loan.book_id = Book.book_id INNER JOIN student on student.student_id = loan.student_id WHERE firstName LIKE :userName")
    fun findBooksBorrowedByNameSync(userName: String): List<Book>

    @Query("SELECT * FROM book INNER JOIN Loan ON Loan.book_id LIKE Book.book_id WHERE loan.student_id LIKE :userId ")
    fun findBooksBorrowedByUser(userId: String): LiveData<List<Book>>

    @Query("SELECT * FROM book INNER JOIN Loan ON Loan.book_id LIKE Book.book_id WHERE loan.student_id LIKE :userId AND loan.endTime > :after ")
    fun findBooksBorrowedByUserAfter(userId: String, after: Date): LiveData<List<Book>>

    @Query("SELECT * FROM book INNER JOIN Loan ON Loan.book_id LIKE Book.book_id WHERE loan.student_id LIKE :userId ")
    fun findBooksBorrowedByUserSync(userId: String): List<Book>

    @Query("SELECT * FROM book")
    fun findAllBooks(): LiveData<List<Book>>


    @Query("SELECT * FROM book")
    fun findAllBooksSync(): List<Book>

    @Insert(onConflict = IGNORE)
    fun insertBook(book: Book)

    @Update(onConflict = REPLACE)
    fun updateBook(book: Book)

    @Query("DELETE FROM Book")
    fun deleteAll()
}

