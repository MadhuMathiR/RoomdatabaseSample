package com.roomdatabasesample.database

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.roomdatabasesample.dao.LoanDao
import com.roomdatabasesample.dao.StudentDao
import com.roomdatabasesample.model.Loan
import com.roomdatabasesample.model.LoanWithStudentAndBook
import com.roomdatabasesample.model.Student


class LoanRepository(application: Application) {
    private var mLoanDao: LoanDao? = null
    private var mAllLoans: LiveData<List<Loan>>? = null
    private var mAllLoansAndBook: LiveData<List<LoanWithStudentAndBook>>? = null

    init {
        val db = StudentRoomDatabase.getInMemoryDatabase(application)
        mLoanDao = db.loanModel()
        mAllLoans = mLoanDao!!.findAllLoans()
        mAllLoansAndBook = mLoanDao!!.findAllWithStudentAndBook()
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    fun getAllLoans(): LiveData<List<Loan>> {
        return mAllLoans!!
    }fun getAllLoansAndBook(): LiveData<List<LoanWithStudentAndBook>> {
        return mAllLoansAndBook!!
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    fun insert(loan: Loan) {
        insertAsyncTask(mLoanDao!!).execute(loan)
    }

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: LoanDao) : AsyncTask<Loan, Void, Void>() {

        override fun doInBackground(vararg params: Loan): Void? {
            mAsyncTaskDao.insertLoan(params[0])
            return null
        }
    }
}