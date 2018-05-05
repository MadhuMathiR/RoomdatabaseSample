package com.roomdatabasesample.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Delete
import android.arch.persistence.room.OnConflictStrategy.IGNORE
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.roomdatabasesample.model.Student


@Dao
interface StudentDao {

    @Insert(onConflict = REPLACE)
    fun insert(student: Student)

    @Query("delete from student")
    fun deleteAll()

    @Query("select * from student")
    fun loadAllStudents():List<Student>

    @Query("select * from student where student_id= :id")
    fun loadStudentById(id : Int):Student

    @Query("select * from student where firstName= :firstName and lastName=:lastName")
    fun loadStudentByNames(firstName : String,lastName:String):List<Student>

   /* @Query("SELECT * from student_details_table ORDER BY name ASC")
    fun getAllStudents(): List<Student>*/


    @Query("select * from student ORDER BY firstName and lastName ASC")
    fun getAlphabetizedWords(): LiveData<List<Student>>


    @Insert(onConflict = IGNORE)
    fun insertStudent(user: Student)

    @Delete
    fun deleteStudent(user: Student)

    @Query("delete from student where firstName like :badName OR lastName like :badName")
    fun deleteStudentByName(badName: String): Int

    @Query("select * FROM student WHERE age =:age") // TODO: Fix this!
    abstract fun findStudentsInSameAge(age: Int): List<Student>

    @Query("select * FROM student WHERE age < :age")
    abstract fun findStudentsYoungerThanSolution(age: Int): List<Student>


}