package com.example.caelum.cadastroalunos.fragment;

import com.example.caelum.cadastroalunos.dao.AlunoDAO;
import com.example.caelum.cadastroalunos.modelo.Aluno;
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
        GoogleMap mapa = getMap();
        Localizador localizador = new Localizador(getActivity());
        AlunoDAO alunoDAO = new AlunoDAO(getActivity());

        LatLng localCaelum = localizador.getCoordenada("Rua Vergueiro 3185 Vila Mariana");
        mapa.addMarker( new MarkerOptions()
                .title("Caelum")
                .snippet("Ensino e Inovação")
                .position(localCaelum));

        mapa.moveCamera(this.centralizaNo(localCaelum));

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

    }

    private CameraUpdate centralizaNo(LatLng local){
        CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(local, 11);
        return camera;
    }
}
