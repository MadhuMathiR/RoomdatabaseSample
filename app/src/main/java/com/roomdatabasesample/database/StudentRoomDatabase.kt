package com.roomdatabasesample.database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.os.AsyncTask
import com.roomdatabasesample.dao.BookDao
import com.roomdatabasesample.dao.LoanDao
import com.roomdatabasesample.dao.StudentDao
import com.roomdatabasesample.model.Book
import com.roomdatabasesample.model.Loan
import com.roomdatabasesample.model.Student
import java.util.*


@Database(entities = arrayOf(Student::class, Loan::class, Book::class), version = 1, exportSchema = false)

public abstract class StudentRoomDatabase : RoomDatabase() {
    abstract fun studentModel(): StudentDao

    abstract fun bookModel(): BookDao

    abstract fun loanModel(): LoanDao

    companion object {
        private var INSTANCE: StudentRoomDatabase? = null

        fun getInMemoryDatabase(context: Context): StudentRoomDatabase {
            if (INSTANCE == null) {
                synchronized(StudentRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder<StudentRoomDatabase>(context.applicationContext,
                                StudentRoomDatabase::class.java!!, "student_database")
                                // Wipes and rebuilds instead of migrating if no Migration object.
                                // Migration is not part of this codelab.
                                .fallbackToDestructiveMigration()
                                .addCallback(sRoomDatabaseCallback)
                                .build()
                    }
                }
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }

        /**
         * Override the onOpen method to populate the database.
         * For this sample, we clear the database every time it is created or opened.
         */
        private val sRoomDatabaseCallback = object : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                PopulateDbAsync(INSTANCE!!).execute()
            }
        }

        fun getTodayPlusDays(daysAgo: Int): Date {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, daysAgo)
            return calendar.time
        }
    }

    private class PopulateDbAsync internal constructor(db: StudentRoomDatabase) : AsyncTask<Void, Void, Void>() {

        private val mDao: StudentDao

        private val loanDao: LoanDao
        private val bookDao: BookDao

        init {
            mDao = db.studentModel()
            loanDao = db.loanModel()
            bookDao = db.bookModel()
        }

        override fun doInBackground(vararg params: Void): Void? {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            mDao.deleteAll()
            loanDao.deleteAll()
            bookDao.deleteAll()

            var student1 = Student()
            student1.id = 1
            student1.firstName = "madhu"
            student1.lastName = "mathi"
            student1.age = 24
            mDao.insert(student1)
            var student2 = Student()
            student2.id = 2
            student2.firstName = "mathi"
            student2.lastName = "madhu"
            student2.age = 23
            mDao.insert(student2)

            var book = Book()
            book.id = 1
            book.title = "harry porter"
            bookDao.insertBook(book)
            var book1 = Book()
            book1.id = 2
            book1.title = "Series of harry porter"
            bookDao.insertBook(book1)


            val today = getTodayPlusDays(0)
            val yesterday = getTodayPlusDays(-1)
            val twoDaysAgo = getTodayPlusDays(-2)
            val lastWeek = getTodayPlusDays(-7)
            val twoWeeksAgo = getTodayPlusDays(-14)


            var loan = Loan()
            loan.bookId = "1"
            loan.id = 1
            loan.startTime = twoDaysAgo
            loan.endTime = today
            loan.studentId = "1"
            loanDao.insertLoan(loan)

            var loan1 = Loan()
            loan1.bookId = "2"
            loan1.id = 2
            loan1.startTime = lastWeek
            loan1.endTime = today
            loan1.studentId = "1"
            loanDao.insertLoan(loan1)

            return null
        }
    }


}