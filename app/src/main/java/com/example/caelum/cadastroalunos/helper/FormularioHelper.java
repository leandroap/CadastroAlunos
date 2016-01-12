package com.example.caelum.cadastroalunos.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private ImageView foto;
    private Button fotoButton;


    public FormularioHelper(FormularioActivity activity) {

        nome = (EditText) activity.findViewById(R.id.formulario_nome);
        site = (EditText) activity.findViewById(R.id.formulario_site);
        endereco = (EditText) activity.findViewById(R.id.formulario_endereco);
        telefone = (EditText) activity.findViewById(R.id.formulario_telefone);
        nota = (RatingBar) activity.findViewById(R.id.formulario_nota);
        foto = (ImageView) activity.findViewById(R.id.formulario_foto);
        fotoButton = (Button) activity.findViewById(R.id.formulario_foto_button);

        aluno = new Aluno();
    }

    public Aluno pegaAlunoDoFormulario(){
        aluno.setNome(nome.getText().toString());
        aluno.setSite(site.getText().toString());
        aluno.setEndereco(endereco.getText().toString());
        aluno.setTelefone(telefone.getText().toString());
        aluno.setNota(Double.valueOf(nota.getProgress()));
        aluno.setCaminhoFoto((String) foto.getTag());

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

        carregaImagem(aluno.getCaminhoFoto());

        this.aluno = aluno;
    }

    public Button getFotoButton(){
        return fotoButton;
    }

    public void carregaImagem(String localFoto){
        if (localFoto!= null  ) {
            Bitmap bitmap = BitmapFactory.decodeFile(localFoto);
            Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, 400, 300, true);

            foto.setImageBitmap(newBitmap);
            foto.setScaleType(ImageView.ScaleType.FIT_XY);
            foto.setTag(localFoto);
        }
    }
}
