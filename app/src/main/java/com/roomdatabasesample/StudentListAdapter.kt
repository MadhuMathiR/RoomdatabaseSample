package com.roomdatabasesample

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.roomdatabasesample.model.Loan
import com.roomdatabasesample.model.LoanWithStudentAndBook
import com.roomdatabasesample.model.Student


class StudentListAdapter(activity: Activity?) : RecyclerView.Adapter<StudentListAdapter.StudentViewHolder>() {

    private var mStudentList: List<LoanWithStudentAndBook>? = null
    var activity: Activity? = null

    init {
        this.activity = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = activity!!.layoutInflater.inflate(R.layout.student_list_item, parent, false)
        return StudentViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return if (mStudentList != null)
            mStudentList!!.size
        else
            0
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val current = mStudentList!!.get(position)
//        holder.tvStudentName.setText(current.firstName+current.lastName)
        holder.tvStudentName.setText(current.bookTitle + "\n"
                + current.userName + "\n"
                + current.startTime + "\n"
                + current.endTime)
    }


    inner class StudentViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvStudentName: TextView

        init {
            tvStudentName = itemView.findViewById(R.id.tvStudentName)
        }
    }

    fun setStudents(words: List<LoanWithStudentAndBook>) {
        mStudentList = words
        notifyDataSetChanged()
    }

}