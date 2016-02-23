package com.deal70.mapp.deal70;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.deal70.mapp.deal70.db.Deal70DbHelper;

public class DbHelperActivity extends AppCompatActivity {

    // Common attributes
    protected static SQLiteOpenHelper dbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // 01 close database
        if(dbHelper != null) {
            dbHelper.close();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 01 Open database
        if(dbHelper == null) {
            dbHelper = new Deal70DbHelper(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 01 close database
        if(dbHelper != null) {
            dbHelper.close();
        }
    }

}
