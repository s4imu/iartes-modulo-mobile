package com.example.iartes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.preference.PreferenceManager;

import com.example.iartes.model.Musica;
import com.example.iartes.model.MusicaDAO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CadastroMusica extends AppCompatActivity {

    private static final int IMG_REQUEST = 1;

    private EditText editNome;
    private EditText editArtista;
    private EditText editAlbum;
    private ImageView imageView;
    private String img = "";

    private byte[] imagemBytes;

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

        editNome = findViewById(R.id.editNome);
        editArtista = findViewById(R.id.editArtista);
        editAlbum = findViewById(R.id.editAlbum);
        imageView = findViewById(R.id.imageView);

        // para fins de debug, verifica o que já existe
        // mais pra frente iremos implementar a tela de visualização de itens
        MusicaDAO musicaDAO = new MusicaDAO(this);
        ArrayList<Musica> listaMusicas = musicaDAO.getMusicas();

        Log.i("Debug", "Há " + listaMusicas.size() + " músicas no banco");
        for (Musica musica : listaMusicas) {
            Log.i("Debug", "Música: " + musica.getNome() + ", Artista: " + musica.getArtista());
        }
    }

    private byte[] getImagemBytes(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }

            return byteBuffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void escolherImagem(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            imageView.setImageURI(uri);
            imagemBytes = getImagemBytes(uri); // nova variável
        }
    }

    public void salvarMusica(View view) {
        String nome = editNome.getText().toString().trim();
        String artista = editArtista.getText().toString().trim();
        String album = editAlbum.getText().toString().trim();

        if (nome.isEmpty() || artista.isEmpty() || album.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        Musica musica = new Musica(nome, artista, album, imagemBytes);
        MusicaDAO dao = new MusicaDAO(this);

        if (dao.addMusica(musica)) {
            finish();
        }
    }

}