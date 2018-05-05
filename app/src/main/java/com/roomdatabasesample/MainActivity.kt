package com.roomdatabasesample

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.Toast
import com.roomdatabasesample.database.DatabaseInitializer
import com.roomdatabasesample.database.LoanViewModel
import com.roomdatabasesample.database.StudentRoomDatabase
import com.roomdatabasesample.model.Student
import com.roomdatabasesample.database.StudentViewModel
import com.roomdatabasesample.model.Loan
import com.roomdatabasesample.model.LoanWithStudentAndBook

class MainActivity : AppCompatActivity() {
    val NEW_WORD_ACTIVITY_REQUEST_CODE = 1


    private var mStudentViewModel: StudentViewModel? = null
    private var mLoanViewModel: LoanViewModel? = null
    private var recyclerview: RecyclerView? = null
    private var fab: FloatingActionButton? = null
    private var toolbar: Toolbar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         toolbar = findViewById(R.id.toolbar) as Toolbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSupportActionBar(toolbar!!)
        }
        val adapter = StudentListAdapter(this)
        recyclerview = findViewById(R.id.recyclerview) as RecyclerView
        fab = findViewById(R.id.fab) as FloatingActionButton
        recyclerview!!.setAdapter(adapter)
        recyclerview!!.setLayoutManager(LinearLayoutManager(this))

        // Get a new or existing ViewModel from the ViewModelProvider.
        mStudentViewModel = ViewModelProviders.of(this).get(StudentViewModel::class.java)
        mLoanViewModel = ViewModelProviders.of(this).get(LoanViewModel::class.java)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mLoanViewModel!!.getAllLoansAndBook().observe(this, object : Observer<List<LoanWithStudentAndBook>> {
            override fun onChanged(words: List<LoanWithStudentAndBook>?) {
                // Update the cached copy of the words in the adapter.
                adapter.setStudents(words!!)
            }
        })

        fab!!.setOnClickListener {
            val intent = Intent(this@MainActivity, AddStudentActivity::class.java)
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val word = Student()
            word.firstName = data.getStringExtra(AddStudentActivity.EXTRA_REPLY)
            mStudentViewModel!!.insert(word)
        } else {
            Toast.makeText(
                    applicationContext,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        StudentRoomDatabase.destroyInstance()
        super.onDestroy()
    }
}
