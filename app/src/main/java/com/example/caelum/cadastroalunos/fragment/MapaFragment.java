package com.example.caelum.cadastroalunos.fragment;

import com.example.caelum.cadastroalunos.dao.AlunoDAO;
import com.example.caelum.cadastroalunos.modelo.Aluno;
import com.example.caelum.cadastroalunos.util.AtualizadorDeLocalizacao;
import com.example.caelum.cadastroalunos.util.Localizador;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by android5519 on 15/01/16.
 */
public class MapaFragment extends SupportMapFragment {



    @Override
    public void onResume() {
        super.onResume();

        Localizador localizador = new Localizador(getActivity());
        AlunoDAO alunoDAO = new AlunoDAO(getActivity());

        GoogleMap mapa = getMap();
        LatLng localCaelum = localizador.getCoordenada("Rua Vergueiro 3185 Vila Mariana");
        mapa.addMarker( new MarkerOptions()
                .title("Caelum")
                .snippet("Ensino e Inovação")
                .position(localCaelum));

        this.centralizaNo(localCaelum);

        List<Aluno> alunos = alunoDAO.getListaAlunos();
        for (Aluno aluno : alunos){
            if (!aluno.getEndereco().isEmpty()) {
                LatLng localAluno = localizador.getCoordenada(aluno.getEndereco());
                if (localAluno != null){
                    mapa.addMarker(new MarkerOptions()
                            .title(aluno.getNome())
                            .position(localAluno));
                }
            }
        }

        AtualizadorDeLocalizacao atualizador = new AtualizadorDeLocalizacao(getActivity(), this);


    }

    public void centralizaNo(LatLng local){
        GoogleMap mapa = getMap();
        CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(local, 11);
        mapa.moveCamera(camera);
    }
}
