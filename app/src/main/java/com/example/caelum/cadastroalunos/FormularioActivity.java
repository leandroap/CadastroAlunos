package com.example.caelum.cadastroalunos;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.caelum.cadastroalunos.dao.AlunoDAO;
import com.example.caelum.cadastroalunos.helper.FormularioHelper;
import com.example.caelum.cadastroalunos.modelo.Aluno;

import java.io.File;


public class FormularioActivity extends ActionBarActivity {

    FormularioHelper helper;
    private String localArquivoFoto;
    private static  int TIRAR_FOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("alunoSelecionado");
        helper = new FormularioHelper(this);

        if (aluno != null){
            helper.insereDadosNoFormulario(aluno);
        }

        Button foto = helper.getFotoButton();
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localArquivoFoto = getExternalFilesDir(null) +
                        "/" + System.currentTimeMillis() + ".jpg";

                Uri uriFoto = Uri.fromFile(new File(localArquivoFoto));
                Intent tirarFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                tirarFoto.putExtra(MediaStore.EXTRA_OUTPUT, uriFoto);

                startActivityForResult(tirarFoto, TIRAR_FOTO);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.formulario_menu_ok:
                salvarAluno();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void salvarAluno(){
        Aluno aluno = helper.pegaAlunoDoFormulario();

        if (helper.temNome()){
            AlunoDAO alunoDAO = new AlunoDAO(this);

            if(aluno.getId() == null) {
                alunoDAO.insere(aluno);
            } else {
                alunoDAO.update(aluno);
            }

            alunoDAO.close();
            finish();
        } else {
            helper.mostrarErro();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TIRAR_FOTO){
            if (resultCode == Activity.RESULT_OK){
                helper.carregaImagem(this.localArquivoFoto);

            } else {
                this.localArquivoFoto = null;
            }
        }
    }
}
