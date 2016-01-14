package com.example.caelum.cadastroalunos.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.caelum.cadastroalunos.R;
import com.example.caelum.cadastroalunos.modelo.Prova;

import java.util.Arrays;
import java.util.List;

/**
 * Created by android5519 on 13/01/16.
 */
public class ListaProvasFragment extends Fragment {

    ListView listViewProvas;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layoutProvas =  inflater.inflate(R.layout.fragment_lista_provas, container, false);
        this.listViewProvas = (ListView) layoutProvas.findViewById(R.id.lista_provas_lisview);

        Prova prova1 = new Prova("20/07/2016", "Matemática");
        prova1.setTopicos(Arrays.asList("Algebra linear", "Cálculo", "Estatística"));

        Prova prova2 = new Prova("25/07/2016", "Portiguês");
        prova2.setTopicos(Arrays.asList("Complemento nominal", "Orações subordinadas", "Análise sintética"));

        List<Prova> provas = Arrays.asList(prova1, prova2);

        this.listViewProvas.setAdapter(new ArrayAdapter<Prova>(
                getActivity(), android.R.layout.simple_list_item_1, provas
        ));

        this.listViewProvas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Prova provaSelecinada = (Prova) adapter.getItemAtPosition(position);

                Toast.makeText(getActivity(), "Prova Selecionada: " + provaSelecinada, Toast.LENGTH_LONG).show();
            }
        });

        return layoutProvas;
    }
}
