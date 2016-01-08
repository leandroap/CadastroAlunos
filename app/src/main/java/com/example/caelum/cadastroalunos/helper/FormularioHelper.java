package com.example.caelum.cadastroalunos.helper;

import android.widget.EditText;
import android.widget.RatingBar;

import com.example.caelum.cadastroalunos.FormularioActivity;
import com.example.caelum.cadastroalunos.R;
import com.example.caelum.cadastroalunos.modelo.Aluno;

/**
 * Created by android5519 on 06/01/16.
 */
public class FormularioHelper {

    private EditText nome;
    private EditText site;
    private EditText endereco;
    private EditText telefone;
    private RatingBar nota;
    private Aluno aluno;


    public FormularioHelper(FormularioActivity activity) {

        nome = (EditText) activity.findViewById(R.id.formulario_nome);
        site = (EditText) activity.findViewById(R.id.formulario_site);
        endereco = (EditText) activity.findViewById(R.id.formulario_endereco);
        telefone = (EditText) activity.findViewById(R.id.formulario_telefone);
        nota = (RatingBar) activity.findViewById(R.id.formulario_nota);

        aluno = new Aluno();
    }

    public Aluno pegaAlunoDoFormulario(){
        aluno.setNome(nome.getText().toString());
        aluno.setSite(site.getText().toString());
        aluno.setEndereco(endereco.getText().toString());
        aluno.setTelefone(telefone.getText().toString());
        aluno.setNota(Double.valueOf(nota.getProgress()));

        /*if (this.aluno.getId() != null){
            aluno.setId(this.aluno.getId());
        }*/

        return aluno;
    }

    public boolean temNome(){
        return !nome.getText().toString().isEmpty();
    }

    public void mostrarErro(){
        nome.setError("Campo nome não pode ser vazio");
    }

    public void insereDadosNoFormulario(Aluno aluno){
        nome.setText(aluno.getNome());
        site.setText(aluno.getSite());
        endereco.setText(aluno.getEndereco());
        telefone.setText(aluno.getTelefone());
        nota.setProgress(aluno.getNota().intValue());

        this.aluno = aluno;
    }

}
