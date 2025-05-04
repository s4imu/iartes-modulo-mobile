package com.example.iartes.model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class MusicaDAO {
    private Context context;
    private SQLiteDatabase database;

    public MusicaDAO(Context context) {
        this.context = context;
        this.database = (new Database(context)).getWritableDatabase();
    }

    public ArrayList<Musica> getMusicas() {
        ArrayList<Musica> result = new ArrayList<>();
        String sql = "SELECT * FROM musicas ORDER BY nome";
        Cursor cursor = database.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String nome = cursor.getString(1);
            String artista = cursor.getString(2);
            String album = cursor.getString(3);
            byte[] imagemBlob = cursor.getBlob(4); // pega como byte[]

            result.add(new Musica(id, nome, artista, album, imagemBlob));
            Log.i("Debug", "Música encontrada: " + nome + " - " + artista);
        }
        cursor.close();
        return result;
    }

    public boolean addMusica(Musica musica) {
        ContentValues values = new ContentValues();
        values.put("nome", musica.getNome());
        values.put("artista", musica.getArtista());
        values.put("album", musica.getAlbum());
        values.put("img", musica.getImagemBlob());

        try {
            database.insert("musicas", null, values);
            Toast.makeText(context, "Música salva com sucesso!", Toast.LENGTH_SHORT).show();
            return true;
        } catch (SQLException e) {
            Toast.makeText(context, "Erro ao salvar música: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public Musica getMusica(int id) {
        String sql = "SELECT * FROM musicas WHERE id=" + id;
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.moveToNext()) {
            String nome = cursor.getString(1);
            String artista = cursor.getString(2);
            String album = cursor.getString(3);
            byte[] imagemBlob = cursor.getBlob(4);
            cursor.close();
            return new Musica(id, nome, artista, album, imagemBlob);
        }
        cursor.close();
        return null;
    }

    public boolean updateMusica(Musica musica) {
        ContentValues values = new ContentValues();
        values.put("nome", musica.getNome());
        values.put("artista", musica.getArtista());
        values.put("album", musica.getAlbum());
        values.put("img", musica.getImagemBlob()); // campo BLOB

        try {
            int linhasAfetadas = database.update("musicas", values, "id=?", new String[]{String.valueOf(musica.getId())});
            if (linhasAfetadas > 0) {
                Toast.makeText(context, "Música atualizada!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "Nenhuma música foi atualizada.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Erro ao atualizar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
