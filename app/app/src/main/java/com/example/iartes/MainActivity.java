package com.example.iartes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.iartes.R;

public class MainActivity extends AppCompatActivity {

    public EditText login;

    public EditText senha;

    public TextView usuarioInvalido;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        login = (EditText) findViewById(R.id.etLogin);
        senha = (EditText) findViewById(R.id.etSenha);
        usuarioInvalido = (TextView) findViewById(R.id.tvInvalido);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void fazLogin(View view) {
        if(login.getText().toString().equals("admin") && senha.getText().toString().equals("admin")) {
            Toast notif = Toast.makeText(this,"O login é: " +  login.getText().toString() + " a senha é: " + senha.getText().toString(), Toast.LENGTH_SHORT);
            notif.show();
            Intent intent = new Intent(this, tela_logada.class);
            //put extra serve para comunicação entre activities
            intent.putExtra("usuario", login.getText().toString());
            startActivity(intent);
            finish();
        } else {
            usuarioInvalido.setVisibility(View.VISIBLE);

            // Usando Handler corretamente no Android 11+
            new Handler(Looper.getMainLooper()).postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            usuarioInvalido.setVisibility(View.INVISIBLE);
                        }
                    },
                    3000 // 3 segundos
            );
        }


    }

    protected void onResume(){
        super.onResume();
        //Log.i()
    }
}