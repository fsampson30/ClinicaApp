package br.com.sonus.sonuscliente.View;

import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;

import br.com.sonus.sonuscliente.Controller.CalendarioFragment;
import br.com.sonus.sonuscliente.Controller.TarefaEnviaFotoExterno;
import br.com.sonus.sonuscliente.Controller.TarefaInsereAgendamentoPaciente;
import br.com.sonus.sonuscliente.Controller.MontaData;
import br.com.sonus.sonuscliente.Model.Agendamento;
import br.com.sonus.sonuscliente.R;

public class AgendamentoActivity extends AppCompatActivity {

    public int ano = 0;
    private int mes = 0;
    private int dia = 0;
    private String localArquivoFoto = null;
    private String respostaTurno = "";
    private String respostaSecao = "";
    private Agendamento agendamento = new Agendamento();
    private String cpfPaciente;
    private Toolbar toolbar;
    public ProgressBar barraCircular;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamento);

        cpfPaciente = getIntent().getExtras().getString("CPF");

        barraCircular = (ProgressBar) findViewById(R.id.circularBarraAgendamento);
        barraCircular.setVisibility(View.GONE);

        toolbar = (Toolbar) findViewById(R.id.toolBarAgendamentoActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Button btnCalendario = (Button) findViewById(R.id.imgButtonAbreCalendario);
        Button btnFoto = (Button) findViewById(R.id.imgButtonFotografar);
        Button btnConfirma = (Button) findViewById(R.id.imgButtonEnviar);
        final RadioGroup rdoGroupTurno = (RadioGroup) findViewById(R.id.RadioTurno);
        final RadioGroup rdoGroupSecao = (RadioGroup) findViewById(R.id.RadioSecaoExame);


        final TextView textAno = (TextView) findViewById(R.id.txtDataEscolhida);
        this.localArquivoFoto = (getExternalFilesDir(null) + "/foto.jpg");

        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent foto = new Intent("android.media.action.IMAGE_CAPTURE");
                foto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri uri = FileProvider.getUriForFile(AgendamentoActivity.this, AgendamentoActivity.this.getApplicationContext().getPackageName() + ".provider", new File(AgendamentoActivity.this.localArquivoFoto));
                foto.putExtra("output", uri);
                AgendamentoActivity.this.startActivityForResult(foto, 123);
                Bundle caminho = new Bundle();
                caminho.putString("caminho", localArquivoFoto);
                Log.d("FOTO", localArquivoFoto);
            }
        });

        rdoGroupTurno.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton botao = (RadioButton) rdoGroupTurno.findViewById(i);
                respostaTurno = botao.getText().toString();
                respostaTurno = respostaTurno.substring(0, 1);
                agendamento.setTurno(respostaTurno);
            }
        });

        rdoGroupSecao.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton botao = (RadioButton) rdoGroupSecao.findViewById(i);
                respostaSecao = botao.getText().toString();
                switch (respostaSecao) {
                    case "Mamo": {
                        respostaSecao = "9";
                        break;
                    }
                    case "US": {
                        respostaSecao = "1";
                        break;
                    }
                    case "DO": {
                        respostaSecao = "2";
                        break;
                    }
                }
                agendamento.setCodigo_secao(respostaSecao);
            }
        });


        btnConfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agendamento.setData_exame(textAno.getText().toString());
                agendamento.setCpf_paciente(cpfPaciente);
                Calendar c = Calendar.getInstance();
                ano = c.get(Calendar.YEAR);
                mes = c.get(Calendar.MONTH) + 1;
                dia = c.get(Calendar.DAY_OF_MONTH);
                MontaData mt = new MontaData();
                String dataSolicitacao = mt.retornaDataMontada(dia, mes, ano);
                agendamento.setData_solicitacao(dataSolicitacao);
                Log.d("Caminho", localArquivoFoto);
                if (validaCamposTela()) {
                    new TarefaInsereAgendamentoPaciente(AgendamentoActivity.this).execute(agendamento);
                }
            }
        });
    }

    public void showDatePickerDialog(View view) {
        DialogFragment newFragment = new CalendarioFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void recebeData(int ano, int mes, int dia) {
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;

    }

    public void retornoTarefaExterna(String retorno) {
        switch (retorno) {
            case "0": {
                Toast.makeText(this, "Erro.", Toast.LENGTH_LONG).show();
                break;
            }
            case "1": {
                Toast.makeText(this, "Confirmada sua solicitação do agendamento, aguarde o retorno.", Toast.LENGTH_LONG).show();
                new TarefaEnviaFotoExterno(AgendamentoActivity.this).execute(localArquivoFoto, cpfPaciente);
                break;
            }
            case "2": {
                Toast.makeText(this, "Pedido médico enviado com sucesso.", Toast.LENGTH_LONG).show();
                onBackPressed();
                break;
            }
            case "9": {
                Toast.makeText(this, "Erro de conexão.", Toast.LENGTH_LONG).show();
                break;
            }
        }

    }

    public boolean validaCamposTela() {
        if (agendamento.getData_exame() == "" || respostaSecao == "" || respostaTurno == "") {
            Toast.makeText(this, "Preecha os campos do agendamento.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332: {
                onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}


