package com.example.iartes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    public EditText login;

    public EditText senha;

    public TextView usuarioInvalido;

    private CheckBox checkboxSalvarDados;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String tema = prefs.getString("tema", "claro");

        if (tema.equals("escuro")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_cadastro_musica);
        setContentView(R.layout.activity_main);

        login = (EditText) findViewById(R.id.etLogin);
        senha = (EditText) findViewById(R.id.etSenha);
        usuarioInvalido = (TextView) findViewById(R.id.tvInvalido);
        checkboxSalvarDados = findViewById(R.id.checkboxSalvarDados);


        boolean preencherLogin = prefs.getBoolean("showLogin", true);

        if (preencherLogin) {
            String loginSalvo = prefs.getString("login", "");
            String senhaSalva = prefs.getString("password", "");
            login.setText(loginSalvo);
            senha.setText(senhaSalva);
            checkboxSalvarDados.setChecked(true);
        } else {
            // Não preencher os campos
            checkboxSalvarDados.setChecked(false);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void fazLogin(View view) {
        String msg = "O login é " +
                login.getText().toString() +
                " e a senha é " +
                senha.getText().toString();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String loginPadrao = sharedPreferences.getString("login", "admin");
        String senhaPadrao = sharedPreferences.getString("password", "admin");

//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("senha_padrao", "senha2");
//        editor.apply();

        Log.i("Debug", "A senha padrão é: " + senhaPadrao);
        Log.i("Debug", "A login padrão é: " + loginPadrao);


        if(
                login.getText().toString().equals(loginPadrao)
                        && senha.getText().toString().equals(senhaPadrao)
        ){

            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (checkboxSalvarDados.isChecked()) {
                // Salvar login e senha
                editor.putString("login", login.getText().toString());
                editor.putString("password", senha.getText().toString());
            } else {
                // Remover login e senha salvos
                editor.remove("login");
                editor.remove("password");
            }

            editor.apply();

            Toast notif = Toast.makeText(this,
                    msg,
                    Toast.LENGTH_LONG);
            notif.show();
            //acesso uma activity nova através da Intent
            Intent intent = new Intent(MainActivity.this, tela_logada.class);
            intent.putExtra("usuario", login.getText().toString());

            startActivity(intent);

            // Se quiserem matar essa activity
            // finish();
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

    public boolean loginSalvo() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String login = prefs.getString("login", "");
        String senha = prefs.getString("password", "");
        boolean showLogin = prefs.getBoolean("showLogin", false);
        return showLogin && !login.isEmpty() && !senha.isEmpty();
    }
}