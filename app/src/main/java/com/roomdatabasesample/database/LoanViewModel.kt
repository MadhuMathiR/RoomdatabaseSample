package com.roomdatabasesample.database

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.roomdatabasesample.model.Loan
import com.roomdatabasesample.model.LoanWithStudentAndBook
import com.roomdatabasesample.model.Student


class LoanViewModel(application: Application): AndroidViewModel(application) {

    private var mRepository: LoanRepository? = null
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private var mAllLoans: LiveData<List<Loan>>? = null
    private var mAllLoansAndBook: LiveData<List<LoanWithStudentAndBook>>? = null

    init {
        mRepository = LoanRepository(application)
        mAllLoans = mRepository!!.getAllLoans()
        mAllLoansAndBook = mRepository!!.getAllLoansAndBook()
    }

    internal fun getAllLoans(): LiveData<List<Loan>> {
        return mAllLoans!!
    }
    internal fun getAllLoansAndBook(): LiveData<List<LoanWithStudentAndBook>> {
        return mAllLoansAndBook!!
    }

    fun insert(word: Loan) {
        mRepository!!.insert(word)
    }
}