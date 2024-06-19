package com.example.do_an;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class NewRegisterActivity extends AppCompatActivity {
    DPHelperDatabase dbh;
    Button btnSignUp;
    EditText txtusername, txtpassword, txtfullname;
    String valueBirth;

    Spinner spbirth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_register);

        dbh =new DPHelperDatabase(this);

        btnSignUp = findViewById(R.id.btnSignUp);
        txtusername = findViewById(R.id.txtnameR);
        txtpassword = findViewById(R.id.txtPassworkR);
        txtfullname = findViewById(R.id.txtFullNameR);
        spbirth = findViewById(R.id.spbirth);

        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 1980; i<= 2010; i++){
            arrayList.add(""+i);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spbirth.setAdapter(arrayAdapter);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String user = txtusername.getText().toString();
                    String pass = txtpassword.getText().toString();
                    String fullname = txtfullname.getText().toString();

                    String sql = "insert into login values('"+user+"','"+pass+"','"+fullname+"','"+valueBirth+"')";
                    SQLiteDatabase db = dbh.ketNoiDBWrite();
                    db.execSQL(sql);
                    Log.d("sql", "sql: "+sql);
                    finish();
                }catch (Exception e) {
                    Log.d("error", "error: " + e);
                }
                finish();
            }
        });

        spbirth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valueBirth = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}