package com.roomdatabasesample.database

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.roomdatabasesample.model.Student


class StudentViewModel(application: Application): AndroidViewModel(application) {

    private var mRepository: StudentRepository? = null
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private var mAllWords: LiveData<List<Student>>? = null



    init {
        mRepository = StudentRepository(application)
        mAllWords = mRepository!!.getAllWords()
    }


    internal fun getAllWords(): LiveData<List<Student>> {
        return mAllWords!!
    }

    fun insert(word: Student) {
        mRepository!!.insert(word)
    }
}