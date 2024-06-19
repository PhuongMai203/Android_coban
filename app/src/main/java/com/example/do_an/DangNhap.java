package com.example.do_an;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DangNhap extends AppCompatActivity {
    DPHelperDatabase dbh;
    SharedPreferences settings;
    TextView txtSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CheckBox ck;
        EditText txtusername,txtpw;
        Button btnlogin,btnthoat;
        CheckBox ckremember;
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.dangnhap);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ck = findViewById(R.id.ck);
        txtusername = findViewById(R.id.txtusername);
        txtpw = findViewById(R.id.txtpw);
        btnlogin = findViewById(R.id.btnlogin);
        txtSignUp = findViewById(R.id.txtsignup);
        btnthoat = findViewById(R.id.btnthoat);
        ckremember = findViewById(R.id.ck);
        dbh =new DPHelperDatabase(this);

        settings = getSharedPreferences("Login", 0);
        String username = settings.getString("user", "");
        String password = settings.getString("pass", "");
        if(username.isEmpty()){

        }else{
            Intent in = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(in);
        }
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u=txtusername.getText().toString();
                String p=txtpw.getText().toString();
                String sql="select * from login where username='"+u+"' and password='"+p+"'";
                SQLiteDatabase db=dbh.ketNoiDBRead();
                Cursor cs = db.rawQuery(sql, null);
                if(cs.moveToNext()){
                    if(ckremember.isChecked()){
                        settings.edit().putString("user", u).apply();
                        settings.edit().putString("pass", p).apply();
                    }
                    Intent in = new Intent(getApplicationContext(),QLNhanVien.class);
                    startActivity(in);
                }else{
                    Toast.makeText(getApplicationContext(),"Tài khoản chưa đúng", Toast.LENGTH_LONG).show();
                }
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),NewRegisterActivity.class);
                startActivity(in);
            }
        });
    }
}
