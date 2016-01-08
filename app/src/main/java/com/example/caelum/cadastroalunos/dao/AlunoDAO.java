package com.example.caelum.cadastroalunos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.caelum.cadastroalunos.modelo.Aluno;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android5519 on 06/01/16.
 */
public class AlunoDAO extends SQLiteOpenHelper {

    private static final int VERSAO = 1;
    private static final String TABELA = "Alunos";
    private static final String DATABASE = "CadastroAluno";
    private static final String CREATE_DATABASE =
            "CREATE TABLE ALUNOS (id INTEGER PRIMARY KEY, " +
            "nome TEXT NOT NULL, telefone TEXT, " +
            "endereco TEXT, site TEXT, nota REAL);";


    public AlunoDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXIST " + TABELA;
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Aluno aluno){
        ContentValues values = new ContentValues();

        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getTelefone());
        values.put("endereco", aluno.getEndereco());
        values.put("site", aluno.getSite());
        values.put("nota", aluno.getNota());

        getWritableDatabase().insert(TABELA, null, values);
    }

    public List<Aluno> getListaAlunos(){
        final String sql = "SELECT * FROM " + TABELA + ";";

        List<Aluno> alunos = new ArrayList<Aluno>();

        Cursor c = getReadableDatabase().rawQuery(sql, null);

        while (c.moveToNext()){
            Aluno aluno = new Aluno();

            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));

            alunos.add(aluno);
        }

        c.close();
        return alunos;
    }
}