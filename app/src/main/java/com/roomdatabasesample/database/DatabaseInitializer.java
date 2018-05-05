/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.roomdatabasesample.database;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;


import com.roomdatabasesample.model.Book;
import com.roomdatabasesample.model.Loan;
import com.roomdatabasesample.model.Student;

import java.util.Calendar;
import java.util.Date;

public class DatabaseInitializer {

    // Simulate a blocking operation delaying each Loan insertion with a delay:
    private static final int DELAY_MILLIS = 500;

    public static void populateAsync(final StudentRoomDatabase db) {

        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final StudentRoomDatabase db) {
        populateWithTestData(db);
    }

    private static void addLoan(final StudentRoomDatabase db, final int id,
                                final Student user, final Book book, Date from, Date to) {
        Loan loan = new Loan();
        loan.setId(id);
        loan.setBookId(String.valueOf(book.getId()));
        loan.setStudentId(String.valueOf(user.getId()));
        loan.setStartTime(from);
        loan.setEndTime(to);
        db.loanModel().insertLoan(loan);
    }

    private static Book addBook(final StudentRoomDatabase db, final int id, final String title) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        db.bookModel().insertBook(book);
        return book;
    }

    private static Student addUser(final StudentRoomDatabase db, final int id, final String name,
                                final String lastName, final int age) {
        Student user = new Student();
        user.setId(id);
        user.setAge(age);
        user.setFirstName( name);
        user.setLastName(lastName);
        db.studentModel().insertStudent(user);
        return user;
    }

    private static void populateWithTestData(StudentRoomDatabase db) {
        db.loanModel().deleteAll();
        db.studentModel().deleteAll();
        db.bookModel().deleteAll();

        Student user1 = addUser(db, 1, "Jason", "Seaver", 40);
        Student user2 = addUser(db, 2, "Mike", "Seaver", 12);
        addUser(db, 3, "Carol", "Seaver", 15);

        Book book1 = addBook(db, 1, "Dune");
        Book book2 = addBook(db, 2, "1984");
        Book book3 = addBook(db, 3, "The War of the Worlds");
        Book book4 = addBook(db, 4, "Brave New World");
        addBook(db, 5, "Foundation");
        try {
            // Loans are added with a delay, to have time for the UI to react to changes.

            Date today = getTodayPlusDays(0);
            Date yesterday = getTodayPlusDays(-1);
            Date twoDaysAgo = getTodayPlusDays(-2);
            Date lastWeek = getTodayPlusDays(-7);
            Date twoWeeksAgo = getTodayPlusDays(-14);

            addLoan(db, 1, user1, book1, twoWeeksAgo, lastWeek);
            Thread.sleep(DELAY_MILLIS);
            addLoan(db, 2, user2, book1, lastWeek, yesterday);
            Thread.sleep(DELAY_MILLIS);
            addLoan(db, 3, user2, book2, lastWeek, today);
            Thread.sleep(DELAY_MILLIS);
            addLoan(db, 4, user2, book3, lastWeek, twoDaysAgo);
            Thread.sleep(DELAY_MILLIS);
            addLoan(db, 5, user2, book4, lastWeek, today);
            Log.d("DB", "Added loans");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Date getTodayPlusDays(int daysAgo) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, daysAgo);
        return calendar.getTime();
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final StudentRoomDatabase mDb;

        PopulateDbAsync(StudentRoomDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb);
            return null;
        }

    }
}
