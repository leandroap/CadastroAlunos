package com.example.caelum.cadastroalunos.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.caelum.cadastroalunos.dao.AlunoDAO;
import com.example.caelum.cadastroalunos.modelo.Aluno;
import com.example.caelum.cadastroalunos.service.WebClient;
import com.example.caelum.cadastroalunos.util.AlunoConverter;

import java.util.List;

/**
 * Created by android5519 on 13/01/16.
 */
public class EnviaAlunosTask extends AsyncTask<Object, Object, String> {

    private Context context;
    private ProgressDialog dialog;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }


    @Override
    protected String doInBackground(Object... params) {
        AlunoDAO alunoDAO = new AlunoDAO(context);
        List<Aluno> alunos = alunoDAO.getListaAlunos();
        alunoDAO.close();

        String json = new AlunoConverter().toJSON(alunos);

        WebClient client = new WebClient();
        String retorno = client.post(json);

        return retorno;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "Aguarda...", "Enviando...", true, true);
    }

    @Override
    protected void onPostExecute(String retorno) {
        super.onPostExecute(retorno);

        dialog.dismiss();
        Toast.makeText(context, retorno, Toast.LENGTH_LONG).show();
    }
}
