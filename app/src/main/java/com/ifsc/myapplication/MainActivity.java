package com.ifsc.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase bd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ContentValues cv = new ContentValues();
        //cv.put("txt", "gijghjkgh");
        //bd.insert("notas", null, cv);

        EditText editText = findViewById(R.id.editTextText);
        Button button = findViewById(R.id.button);
        ListView listView = findViewById(R.id.listView);
        bd=openOrCreateDatabase("notinhas", MODE_PRIVATE, null);

        bd.execSQL("CREATE TABLE IF NOT EXISTS notas (id INTEGER PRIMARY KEY AUTOINCREMENT, txt VARCHAR)");

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put("txt", editText.getText().toString());
                if (bd.insert("notas", null, cv)>0){
                    Toast.makeText(getApplicationContext(), "Nota Inserida", Toast.LENGTH_LONG);
                    listagem();
                };
            }

            public void listagem (){
                Cursor cursor = bd.rawQuery("SELECT * FROM notas", null);
                cursor.moveToFirst();
                ArrayList<String> notas = new ArrayList<>();
                while (!cursor.isAfterLast()){
                    notas.add(cursor.getString(cursor.getColumnIndex("txt")));
                    cursor.moveToNext();

                }
                ArrayAdapter<String>adapter = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        notas);
                listView.setAdapter(adapter);
            }
        });






    }

}