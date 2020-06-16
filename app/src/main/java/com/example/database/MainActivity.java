package com.example.database;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView mainView;
    TextView newData;
    TextView errorData;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint({"SetTextI18n", "Recycle"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainView = (TextView) findViewById(R.id.mainView);
        newData = (TextView) findViewById(R.id.newData);
        errorData = (TextView) findViewById(R.id.errData);

        SQLiteOpenHelper dbHelper = new DatabaseHelper(getApplicationContext());

        try{
            Cursor cursor2;
            try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
                Cursor cursor = db.query("DRINK",
                        new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"},
                        "NAME = ? ",
                        new String[]{"Latte"},
                        null, null, null);
                if (cursor.moveToFirst()) {
                    mainView.setText("Latte's description is: " + cursor.getString(1));
                }

                // for new record
                cursor2 = db.query("DRINK",
                        new String[]{"NAME", "DESCRIPTION"},
                        null, null, null, null, null);
            }
            if (cursor2.moveToLast()) {
                newData.setText("The New Record is: " + cursor2.getString(1));
            }
        } catch(SQLiteException e) {
            errorData.setText("SQL error happened:\n" + e.toString());
        }

    }
}