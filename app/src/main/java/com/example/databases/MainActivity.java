package com.example.databases;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button save, read, del;
    private EditText et1, et2;
    private TextView tv1, tv2;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        save = findViewById(R.id.button);
        save.setOnClickListener(this);
        read = findViewById(R.id.button2);
        read.setOnClickListener(this);
        del = findViewById(R.id.button3);
        del.setOnClickListener(this);

        et1 = findViewById(R.id.editText);
        et2 = findViewById(R.id.editText2);
        tv1 = findViewById(R.id.textView);
        tv2 = findViewById(R.id.textView2);

        dbHelper = new DBHelper(this);

    }

    @Override
    public void onClick(View v) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        if (v == save) {
            String a = et1.getText().toString();
            String b = et2.getText().toString();
            tv1.setText(a);
            tv2.setText(b);
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.KEY_A, a);
            contentValues.put(DBHelper.KEY_B, b);

            Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_MYTABLE, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                int id = cursor.getInt(idIndex);
                sqLiteDatabase.update(DBHelper.TABLE_MYTABLE, contentValues, "_id = ?", new String[]{String.valueOf(id)});
            } else {
                sqLiteDatabase.insert(DBHelper.TABLE_MYTABLE, null, contentValues);
            }
            cursor.close();
        } else if (v == read) {
            Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_MYTABLE, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                int aIndex = cursor.getColumnIndex(DBHelper.KEY_A);
                int bIndex = cursor.getColumnIndex(DBHelper.KEY_B);
                tv1.setText(cursor.getString(aIndex));
                tv2.setText(cursor.getString(bIndex));
//                while (cursor.moveToNext()) --- если несколько строк
            } else {
                tv1.setText("БД пуста");
                tv2.setText("БД пуста");
            }
            cursor.close();
        } else if (v == del) {
            sqLiteDatabase.delete(DBHelper.TABLE_MYTABLE, null, null);
        }
        dbHelper.close();
    }
}