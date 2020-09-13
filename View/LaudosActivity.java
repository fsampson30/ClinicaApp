package br.com.sonus.sonuscliente.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.sonus.sonuscliente.Controller.TarefaRecebeListaLaudos;
import br.com.sonus.sonuscliente.Controller.RecyclerViewLaudosAdapter;
import br.com.sonus.sonuscliente.R;

public class LaudosActivity extends AppCompatActivity {

    private String cpfPaciente;
    private android.support.v7.widget.Toolbar toolbar;
    private RecyclerView recyclerView;
    public ProgressBar barraCircular;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laudos);
        cpfPaciente = getIntent().getExtras().getString("CPF");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewLaudos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

         toolbar = (Toolbar) findViewById(R.id.toolBarLaudosActivity);
         setSupportActionBar(toolbar);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         getSupportActionBar().setHomeButtonEnabled(true);

         barraCircular = (ProgressBar) findViewById(R.id.circularBarraLaudos);
         barraCircular.setVisibility(View.GONE);

         new TarefaRecebeListaLaudos(LaudosActivity.this).execute(cpfPaciente);
    }

    public void retornoTarefaExterna(HashMap<String, ArrayList<String>> strings){
        RecyclerViewLaudosAdapter adapter = new RecyclerViewLaudosAdapter(strings, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mensagens, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuMensagensAtualiza: {
                new TarefaRecebeListaLaudos(LaudosActivity.this).execute(cpfPaciente);
                break;
            }
            case 16908332: {
                onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
