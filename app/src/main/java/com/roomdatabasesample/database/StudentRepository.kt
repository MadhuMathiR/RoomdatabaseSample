package com.roomdatabasesample.database

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.roomdatabasesample.dao.StudentDao
import com.roomdatabasesample.model.Student


class StudentRepository(application: Application) {
    private var mWordDao: StudentDao? = null
    private var mAllWords: LiveData<List<Student>>? = null

    init {
        val db = StudentRoomDatabase.getInMemoryDatabase(application)
        mWordDao = db.studentModel()
        mAllWords = mWordDao!!.getAlphabetizedWords()
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    fun getAllWords(): LiveData<List<Student>> {
        return mAllWords!!
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    fun insert(word: Student) {
        insertAsyncTask(mWordDao!!).execute(word)
    }

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: StudentDao) : AsyncTask<Student, Void, Void>() {

        override fun doInBackground(vararg params: Student): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }
}