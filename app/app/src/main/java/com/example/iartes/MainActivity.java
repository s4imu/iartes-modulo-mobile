package com.example.iartes;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
    public int contador = 0;
    public TextView numero;

    public EditText login;

    public EditText senha;

    public TextView usuarioInvalido;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //numero = (TextView) findViewById(R.id.numero);
        login = (EditText) findViewById(R.id.login);
        senha = (EditText) findViewById(R.id.senha);
        usuarioInvalido = (TextView) findViewById(R.id.invalido);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void imprime(View view) {
        Toast meuToast = null;
        meuToast = Toast.makeText(this, "O valor do contador é: " + contador, Toast.LENGTH_LONG);
        meuToast.show();
    }

    public void soma(View view) {
        contador  = contador + 1;
        numero.setText(Integer.toString(contador));
    }

    public void fazLogin(View view) {
        if(login.getText().toString().equals("admin") && senha.getText().toString().equals("admin")) {
            Toast notif = Toast.makeText(this,"O login é: " +  login.getText().toString() + " a senha é: " + senha.getText().toString(), Toast.LENGTH_SHORT);
            notif.show();
        } else {
            usuarioInvalido.setVisibility(View.VISIBLE);
        }

    }
}