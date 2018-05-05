package com.roomdatabasesample.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.TypeConverters
import com.roomdatabasesample.model.Loan
import com.roomdatabasesample.model.LoanWithStudentAndBook
import com.roomdatabasesample.utils.DateConverter
import java.util.Date
@Dao
@TypeConverters(DateConverter::class)
interface LoanDao {

    @Query("select * from loan")
    fun findAllLoans(): LiveData<List<Loan>>

    @Query("select Loan.loan_id, Book.title, Student.firstName, Loan.startTime, Loan.endTime from loan INNER JOIN book ON Loan.book_id = Book.book_id INNER JOIN student ON Loan.Student_id = Student.student_id ")
    fun findAllWithStudentAndBook(): LiveData<List<LoanWithStudentAndBook>>

    @Query("select Loan.loan_id, Book.title as title, Student.firstName as name, Loan.startTime, Loan.endTime from book INNER JOIN loan ON Loan.book_id = Book.book_id INNER JOIN student on student.student_id = Loan.student_id WHERE Student.firstName LIKE :StudentName AND Loan.endTime > :after ")
    fun findLoansByNameAfter(StudentName: String, after: Date): LiveData<List<LoanWithStudentAndBook>>

    @Insert
    fun insertLoan(loan: Loan)

    @Query("delete from loan")
    fun deleteAll()
}