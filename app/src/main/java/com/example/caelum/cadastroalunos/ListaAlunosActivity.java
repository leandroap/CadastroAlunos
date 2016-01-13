package com.example.caelum.cadastroalunos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.caelum.cadastroalunos.adapter.ListaAlunoAdapter;
import com.example.caelum.cadastroalunos.dao.AlunoDAO;
import com.example.caelum.cadastroalunos.modelo.Aluno;
import com.example.caelum.cadastroalunos.util.AlunoConverter;

import java.util.List;


public class ListaAlunosActivity extends ActionBarActivity {

    ListView listaAlunos;
    Aluno alunoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        this.listaAlunos = (ListView) findViewById(R.id.lista_alunos);

        registerForContextMenu(this.listaAlunos);

        this.listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Aluno alunoSelecionado = (Aluno) listaAlunos.getItemAtPosition(position);
                Intent editarAluno = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                editarAluno.putExtra("alunoSelecionado", alunoSelecionado);
                startActivity(editarAluno);

            }
        });

        this.listaAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Aluno aluno = (Aluno) parent.getItemAtPosition(position);
                return false;
            }
        });

        Button botaoAdicionar = (Button) findViewById(R.id.lista_alunos_floating_button);
        botaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_context_lista, menu);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        this.alunoSelecionado = (Aluno) this.listaAlunos.getAdapter().getItem(info.position);

        /**
        Criando menu sem utilizar o xml de menu
        MenuItem deletar = menu.add("Deletaaaar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                new AlertDialog.Builder(ListaAlunosActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Deletar")
                        .setMessage("Deseja mesmo deletar?")
                        .setPositiveButton("Quero",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                                        dao.deletar(alunoSelecionado);
                                        dao.close();
                                        carregaLista();
                                    }
                                }).setNegativeButton("Não", null).show();

                return false;
            }
        });*/

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_deletar:
                alertDeletarAluno();
                break;
            case R.id.menu_ligar:
                Intent intentLigar = new Intent(Intent.ACTION_CALL);
                intentLigar.setData(Uri.parse("tel:"+this.alunoSelecionado.getTelefone()));
                startActivity(intentLigar);
                break;
            case R.id.menu_sms:
                Intent intentSMS = new Intent(Intent.ACTION_VIEW);
                intentSMS.setData(Uri.parse("sms:"+this.alunoSelecionado.getTelefone()));
                intentSMS.putExtra("sms_body", "Ola aluno");
                startActivity(intentSMS);
                break;
            case R.id.menu_mapa:
                Intent intentMapa = new Intent(Intent.ACTION_VIEW);
                String endereco = alunoSelecionado.getEndereco();
                intentMapa.setData(Uri.parse("geo:0,0?z=14&q=" + Uri.encode(endereco)));
                startActivity(intentMapa);
                break;
            case R.id.menu_site:
                Intent intentSite = new Intent(Intent.ACTION_VIEW);
                if (this.alunoSelecionado.getSite() != null) {
                    String site = this.alunoSelecionado.getSite();
                    intentSite.setData(Uri.parse("http:" + site));
                    startActivity(intentSite);
                }
                break;
        }

        return super.onContextItemSelected(item);
    }

    public void alertDeletarAluno(){
        new AlertDialog.Builder(ListaAlunosActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Deletar")
                .setMessage("Deseja mesmo deletar?")
                .setPositiveButton("Quero",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                                dao.deletar(alunoSelecionado);
                                dao.close();
                                carregaLista();
                            }
                        }).setNegativeButton("Não", null).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.carregaLista();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.menu_lista_enviar_notas:
                AlunoDAO alunoDAO = new AlunoDAO(this);
                List<Aluno> alunos = alunoDAO.getListaAlunos();
                alunoDAO.close();

                String json = new AlunoConverter().toJSON(alunos);

                Toast.makeText(this, json, Toast.LENGTH_LONG).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void carregaLista(){

        AlunoDAO alunoDAO = new AlunoDAO(this);
        List<Aluno> alunos = alunoDAO.getListaAlunos();

        ListaAlunoAdapter adapter = new ListaAlunoAdapter(this, alunos);
        this.listaAlunos.setAdapter(adapter);
    }
}
