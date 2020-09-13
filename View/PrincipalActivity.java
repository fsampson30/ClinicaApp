package br.com.sonus.sonuscliente.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import br.com.sonus.sonuscliente.R;

public class PrincipalActivity extends AppCompatActivity {

    private boolean ok;
    private int quantidade = 0;
    private String cpfPaciente = "";
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        cpfPaciente = getIntent().getExtras().getString("CPF");
        Log.d("Cpf", cpfPaciente);

        toolbar = (Toolbar) findViewById(R.id.toolBarPrincipalActivity);
        setSupportActionBar(toolbar);

        ImageView imgBtnAgenda = (ImageView) findViewById(R.id.imgButtonAgenda);
        ImageView imgBtnLaudo = (ImageView) findViewById(R.id.imgButtonLaudo);
        ImageView imgBtnMensagens = (ImageView) findViewById(R.id.imgButtonMensagens);

        imgBtnAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent calendario = new Intent(PrincipalActivity.this, AgendamentoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("CPF", cpfPaciente);
                calendario.putExtras(bundle);
                PrincipalActivity.this.startActivity(calendario);
            }
        });

        imgBtnLaudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent laudos = new Intent(PrincipalActivity.this, LaudosActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("CPF", cpfPaciente);
                laudos.putExtras(bundle);
                PrincipalActivity.this.startActivity(laudos);
            }
        });

        imgBtnMensagens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mensagens = new Intent(PrincipalActivity.this, MensagemActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("CPF", cpfPaciente);
                Log.d("CPF Mensagem", cpfPaciente);
                mensagens.putExtras(bundle);
                PrincipalActivity.this.startActivity(mensagens);
            }
        });
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuPrincipalSair: {
                System.exit(0);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

