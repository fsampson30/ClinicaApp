package br.com.sonus.sonuscliente.View;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import br.com.sonus.sonuscliente.Controller.TarefaInsereMensagemPaciente;
import br.com.sonus.sonuscliente.Controller.MontaData;
import br.com.sonus.sonuscliente.Controller.RecyclerViewMensagensAdapter;
import br.com.sonus.sonuscliente.Controller.TarefaSelecionaMensagensPaciente;

import br.com.sonus.sonuscliente.Model.Mensagem;
import br.com.sonus.sonuscliente.R;

public class MensagemActivity extends AppCompatActivity {

    private String cpfPaciente;
    private android.support.v7.widget.Toolbar toolbar;
    private RecyclerView recyclerView;
    public ProgressBar barraCircular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensagem);

        final EditText edtResposta = (EditText) findViewById(R.id.txtRespostaMensagem);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMensagens);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        barraCircular = (ProgressBar) findViewById(R.id.circularBarraMensagem);
        barraCircular.setVisibility(View.GONE);

        ImageView btnEnvia = (ImageView) findViewById(R.id.btnEnviaResposta);
        edtResposta.setText("");
        edtResposta.clearFocus();

        toolbar = (Toolbar) findViewById(R.id.toolBarMensagemActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        cpfPaciente = getIntent().getExtras().getString("CPF");
        new TarefaSelecionaMensagensPaciente(MensagemActivity.this).execute(cpfPaciente);

        btnEnvia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtResposta.getText().toString().equals("")) {
                    Toast.makeText(MensagemActivity.this, "Digite a mensagem.", Toast.LENGTH_SHORT).show();
                } else {
                    Mensagem mensagem = new Mensagem();
                    Calendar cal = Calendar.getInstance();
                    MontaData mt = new MontaData();
                    mensagem.setCpf(cpfPaciente);
                    mensagem.setTexto(edtResposta.getText().toString());
                    mensagem.setData_envio(mt.retornaDataMontada(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR)));
                    new TarefaInsereMensagemPaciente(MensagemActivity.this).execute(mensagem);
                    edtResposta.setText("");
                    view = getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }
        });

    }

    public void retornoTarefaExterna(HashMap<String, ArrayList<String>> msg) {
        RecyclerViewMensagensAdapter adapter = new RecyclerViewMensagensAdapter(msg);
        recyclerView.setAdapter(adapter);
    }

    public void retornoTarefaExternaEnvio(String retorno) {
        switch (retorno) {
            case "0": {
                Toast.makeText(this, "Erro.", Toast.LENGTH_SHORT).show();
                break;
            }
            case "1": {
                Toast.makeText(this, "Mensagens enviada.", Toast.LENGTH_SHORT).show();
                new TarefaSelecionaMensagensPaciente(MensagemActivity.this).execute(cpfPaciente);
                break;
            }
        }
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
                new TarefaSelecionaMensagensPaciente(MensagemActivity.this).execute(cpfPaciente);
                break;
            }
            case 16908332: {
                onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}


