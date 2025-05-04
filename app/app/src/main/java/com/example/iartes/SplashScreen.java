package com.example.iartes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.preference.PreferenceManager;

import com.example.iartes.model.Database;

public class SplashScreen extends AppCompatActivity {

    public int TEMPO_SPLASH_SCREEN = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Aplica o tema salvo
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String tema = prefs.getString("tema", "claro");

        if (tema.equals("escuro")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_splash_screen);

        // Inicia o banco de dados
        inicializarBancoDados();

        // Delay para manter splash e decidir para onde ir
        new Handler().postDelayed(this::irParaAProximaActivity, TEMPO_SPLASH_SCREEN);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void irParaAProximaActivity() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String loginSalvo = prefs.getString("login", "");
        String senhaSalva = prefs.getString("password", "");
        boolean showLogin = prefs.getBoolean("showLogin", false);

        if (showLogin && !loginSalvo.isEmpty() && !senhaSalva.isEmpty()) {
            Log.i("Debug", "Login automático com usuário: " + loginSalvo);
            Intent intent = new Intent(SplashScreen.this, tela_logada.class);
            intent.putExtra("usuario", loginSalvo);
            startActivity(intent);
        } else {
            Log.i("Debug", "Redirecionando para tela de login");
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
        }

        finish();
    }

    private void inicializarBancoDados() {
        try {
            Database dbHelper = new Database(this);
            dbHelper.getWritableDatabase();
            Log.i("Debug", "Banco de dados inicializado");
        } catch (Exception e) {
            Log.e("Debug", "Erro ao inicializar banco: " + e.getMessage());
        }
    }
}
