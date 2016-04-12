package com.example.javier.examen_u2_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class Principal extends AppCompatActivity implements Button.OnClickListener{

    private double a, b, c, x1, x2;
    private EditText edtTxtA, edtTxtB, edtTxtC, edtTxtX1, edtTxtX2;
    private DatePicker datePicker;
    private Button btnResolver;
    private static final String VALORA="A",VALORB="B",VALORC="C",VALORX1="X1",VALORX2="x2",ANO="ano",DIA="dia",MES="mes";
    private SharedPreferences pref;
    private SharedPreferences.Editor edit;
    public static final int PRIVADO=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        edtTxtA = (EditText) findViewById(R.id.edtTxtA);
        edtTxtB = (EditText) findViewById(R.id.edtTxtB);
        edtTxtC = (EditText) findViewById(R.id.edtTxtC);
        edtTxtX1 = (EditText) findViewById(R.id.edtTxtX1);
        edtTxtX2 = (EditText) findViewById(R.id.edtTxtX2);
        btnResolver = (Button) findViewById(R.id.btnResolver);
        datePicker=(DatePicker)findViewById(R.id.datePicker);
        btnResolver.setOnClickListener(this);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        pref=getPreferences(PRIVADO);
        edit=pref.edit();
        cargarPreferencias();
    }

    @Override
    public void onClick(View v) {

        if (edtTxtA.getText().toString().isEmpty() ||
                edtTxtB.getText().toString().isEmpty() ||
                edtTxtC.getText().toString().isEmpty()) {
            Toast.makeText(this,"Ingresar Valores", Toast.LENGTH_SHORT).show();
        }
        else {
            a = Double.parseDouble(edtTxtA.getText().toString());
            b = Double.parseDouble(edtTxtB.getText().toString());
            c = Double.parseDouble(edtTxtC.getText().toString());

            double raiz = Math.sqrt(Math.pow(b,2)-4*a*c);

            if (a==0){
                Toast.makeText(this,"No se puede realizar a es igual a 0",Toast.LENGTH_SHORT).show();
                edtTxtA.setText("");
            }else{
                if (raiz>=0) {
                    x1 = (-b + raiz) / (2 * a);
                    x2 = (-b - raiz) / (2 * a);
                    edtTxtX1.setText(""+x1);
                    edtTxtX2.setText(""+x2);
                    Toast.makeText(this,"Exito... Raíz Resuelta :D", Toast.LENGTH_SHORT).show();
                    guardarPreferencias();

                } else {
                    Toast.makeText(this,"Raiz Negativa D:", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this,"Intenta Con Otros Valores", Toast.LENGTH_SHORT).show();

                    edtTxtX1.setText("");
                    edtTxtX2.setText("");

                    edtTxtA.setText("");
                    edtTxtB.setText("");
                    edtTxtC.setText("");
                }
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void guardarPreferencias(){

        if (edtTxtA.getText().toString().isEmpty()||edtTxtB.getText().toString().isEmpty()||edtTxtC.getText().toString().isEmpty()){
            cargarPreferencias();
        }else{
            edit.putFloat(VALORA,Float.parseFloat(edtTxtA.getText().toString()));
            edit.putFloat(VALORB,Float.parseFloat(edtTxtB.getText().toString()));
            edit.putFloat(VALORC,Float.parseFloat(edtTxtC.getText().toString()));
            edit.putFloat(VALORX1,Float.parseFloat(edtTxtX1.getText().toString()));
            edit.putFloat(VALORX2,Float.parseFloat(edtTxtX2.getText().toString()));
            edit.putInt(ANO,datePicker.getYear());
            edit.putInt(MES,datePicker.getMonth());
            edit.putInt(DIA,datePicker.getDayOfMonth());
            edit.commit();
        }

    }
    public void cargarPreferencias(){
        edtTxtA.setText(""+pref.getFloat(VALORA,1.0f));
        edtTxtB.setText(""+pref.getFloat(VALORB,2.0f));
        edtTxtC.setText(""+pref.getFloat(VALORC,5.0f));
        edtTxtX1.setText(""+pref.getFloat(VALORX1,0.0f));
        edtTxtX2.setText(""+pref.getFloat(VALORX2,0.0f));
        datePicker.updateDate(
                pref.getInt(ANO,2016),
                pref.getInt(MES,04),
                pref.getInt(DIA,13)
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        guardarPreferencias();
    }
}
