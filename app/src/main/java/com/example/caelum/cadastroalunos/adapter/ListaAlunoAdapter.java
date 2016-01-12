package com.example.caelum.cadastroalunos.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.caelum.cadastroalunos.R;
import com.example.caelum.cadastroalunos.modelo.Aluno;

import java.util.List;

/**
 * Created by android5519 on 12/01/16.
 */
public class ListaAlunoAdapter extends BaseAdapter {
    private List<Aluno> alunos;
    private Activity activity;

    public ListaAlunoAdapter(Activity activity, List<Aluno> alunos) {
        this.alunos = alunos;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return this.alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.item_lista, parent, false);

        Aluno aluno = (Aluno) getItem(position);

        ImageView foto = (ImageView) view.findViewById(R.id.item_foto);
        Bitmap bitmap;

        if (aluno.getCaminhoFoto() != null){
            bitmap = BitmapFactory.decodeFile(aluno.getCaminhoFoto());
        } else {
            bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_no_image);
        }

        bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
        foto.setImageBitmap(bitmap);

        TextView nome = (TextView) view.findViewById(R.id.item_nome);
        nome.setText(aluno.getNome());

        if(position % 2 == 0){
            view.setBackgroundColor(activity.getResources().getColor(R.color.linha_par));
        } else {
            view.setBackgroundColor(activity.getResources().getColor(R.color.linha_impar));
        }

        return view;
    }
}
