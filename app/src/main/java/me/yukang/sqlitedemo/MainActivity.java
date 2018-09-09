package me.yukang.sqlitedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);

        Button createDatabase = findViewById(R.id.create_database);
        Button addData = findViewById(R.id.add_data);
        Button updateData = findViewById(R.id.update_data);
        Button deleteData = findViewById(R.id.delete_data);
        Button queryData = findViewById(R.id.query_data);

        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.getWritableDatabase();
                Toast.makeText(MainActivity.this, "Create Success!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
//                ContentValues values = new ContentValues();
//                // 开始组装第一条数据
//                values.put("name", "The Da Vinci Code");
//                values.put("author", "Dan Brown");
//                values.put("pages", 454);
//                values.put("price", 16.96);
//                db.insert("Book", null, values); // 插入第一条数据
//                values.clear();
//
//                // 开始组装第二条数据
//                values.put("name", "The Lost Symbol");
//                values.put("author", "Dan Brown");
//                values.put("pages", 510);
//                values.put("price", 19.95);
//                db.insert("Book", null, values);

                // sql语句方式
                db.execSQL("insert into Book(name, author, pages, price) values(?, ?, ?, ?)",
                        new String[]{"The Da Vinci Code", "Dan Brown", "454", "16.96"});
                db.execSQL("insert into Book(name, author, pages, price) values(?, ?, ?, ?)",
                        new String[]{"The Lost Symbol", "Dan Brown", "510", "19.95"});
            }
        });

        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
//                ContentValues values = new ContentValues();
//                values.put("price", 10.99);
//                db.update("Book", values, "name = ?", new String[]{"The Da Vince " +
//                        "Code"});

                // sql语句方式
                db.execSQL("update Book set price = ? where name = ?", new String[]{"10.99",
                        "The Da Vinci Code"});
            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
//                db.delete("Book", "pages > ?", new String[]{"500"});

                // sql语句方式
                db.execSQL("delete from Book where pages > ?", new String[]{"500"});
            }
        });

        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
//                Cursor cursor = db.query("Book", null, null, null, null,
//                        null, null);

                // sql语句方式
                Cursor cursor = db.rawQuery("select * from Book", null);

                if (cursor.moveToFirst()) {
                    do {
                        // 遍历 Cursor 对象，取出数据并打印
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity", "book name is " + name);
                        Log.d("MainActivity", "book author is " + author);
                        Log.d("MainActivity", "book pages is " + pages);
                        Log.d("MainActivity", "book price is " + price);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }
}
