package com.example.caelum.cadastroalunos;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import com.example.caelum.cadastroalunos.fragment.DetalhesProvaFragment;
import com.example.caelum.cadastroalunos.fragment.ListaProvasFragment;
import com.example.caelum.cadastroalunos.modelo.Prova;

/**
 * Created by android5519 on 13/01/16.
 */
public class ProvasActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (isTable()){
            transaction
                    .replace(R.id.provas_lista, new ListaProvasFragment())
                    .replace(R.id.provas_detalhe, new DetalhesProvaFragment());
        } else {
            transaction.replace(R.id.provas_view, new ListaProvasFragment());
        }
        transaction.commit();

    }

    public boolean isTable(){
        return getResources().getBoolean(R.bool.isTablet);
    }

    public void selecionarProva(Prova prova){
        FragmentManager manager = getSupportFragmentManager();

        if (isTable()){
            DetalhesProvaFragment detalhesProvaFragment = (DetalhesProvaFragment)
                    manager.findFragmentById(R.id.provas_detalhe);
            detalhesProvaFragment.popularCamposComDados(prova);
        } else {
            Bundle arguments = new Bundle();
            arguments.putSerializable("prova", prova);

            DetalhesProvaFragment detalhesProvaFragment = new DetalhesProvaFragment();
            detalhesProvaFragment.setArguments(arguments);

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.provas_view, detalhesProvaFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
