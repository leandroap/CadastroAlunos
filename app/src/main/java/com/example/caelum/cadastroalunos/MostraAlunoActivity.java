package com.example.caelum.cadastroalunos;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import com.example.caelum.cadastroalunos.fragment.MapaFragment;

/**
 * Created by android5519 on 15/01/16.
 */
public class MostraAlunoActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_mostra_alunos);

        MapaFragment mapaFragment = new MapaFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mostra_alunos_mapa, mapaFragment);
        transaction.commit();
    }
}
