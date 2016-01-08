package com.example.caelum.cadastroalunos;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.caelum.cadastroalunos.dao.AlunoDAO;
import com.example.caelum.cadastroalunos.helper.FormularioHelper;
import com.example.caelum.cadastroalunos.modelo.Aluno;


public class FormularioActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
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
        FormularioHelper helper = new FormularioHelper(this);

        Aluno aluno = helper.pegaAlunoDoFormulario();

        if (helper.temNome()){
            AlunoDAO alunoDAO = new AlunoDAO(this);
            alunoDAO.insere(aluno);
            alunoDAO.close();
            finish();
        } else {
            helper.mostrarErro();
        }
    }


}
