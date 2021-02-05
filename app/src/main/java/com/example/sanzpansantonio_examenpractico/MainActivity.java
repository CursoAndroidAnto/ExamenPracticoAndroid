package com.example.sanzpansantonio_examenpractico;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLInput;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db = null;
    Button btnSearch;
    EditText inDeptNo;
    TextView lbResultDept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch = findViewById(R.id.btnSearch);
        inDeptNo = findViewById(R.id.inDeptNo);
        lbResultDept = findViewById(R.id.lbResultDept);

        createDB();
    }

    private void createDB() {
        BaseDatosHelper deptbd = new BaseDatosHelper(this, "DEPT", null, 1);
        db = deptbd.getWritableDatabase();
        insertBBDD();
    }

    private void insertBBDD() {
        BaseDatosHelper deptdb = new BaseDatosHelper(this, "DEPT", null, 1);
        db = deptdb.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT DEPT_NO FROM DEPT", null);
        if (c.moveToFirst()) {
            return;
        } else {
            db.execSQL("insert into DEPT values(10,'CONTABILIDAD','SEVILLA');");
            db.execSQL("insert into DEPT values(20,'INVESTIGACIÓN','MADRID');");
            db.execSQL("insert into DEPT values(30,'VENTAS','BARCELONA');");
            db.execSQL("insert into DEPT values(40,'PRODUCCIÓN','BILBAO');");
        }
    }

    public void onClickDeptNo(View v) {
        getData();
    }

    private void getData() {
        try {
            String[] args = new String[]{inDeptNo.getText().toString()};

            Cursor c = db.rawQuery("SELECT DNOMBRE, LOC FROM DEPT WHERE DEPT_NO=?", args);
            if (c.moveToFirst()) {
                do {
                    lbResultDept.setText(c.getString(0) + ", " + c.getString(1));
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }
}