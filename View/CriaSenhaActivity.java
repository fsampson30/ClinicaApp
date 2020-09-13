package br.com.sonus.sonuscliente.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import br.com.sonus.sonuscliente.Controller.TarefaCriaSenhaPaciente;
import br.com.sonus.sonuscliente.Model.Paciente;
import br.com.sonus.sonuscliente.R;

public class CriaSenhaActivity extends AppCompatActivity {

    private Paciente paciente = new Paciente();
    private String cpfPaciente="";
    private String senha1="";
    public ProgressBar barraCircular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cria_senha);

        cpfPaciente = getIntent().getExtras().getString("CPF");
        senha1 = getIntent().getExtras().getString("senha1");

        barraCircular = (ProgressBar) findViewById(R.id.circularBarraCriaSenha);
        barraCircular.setVisibility(View.GONE);

        final TextView senha2 = (TextView) findViewById(R.id.txtConfirmaSenha);
        Button btnLogar = (Button) findViewById(R.id.btnCriaSenha);

        senha2.setText("");

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (senha1.length() == 0 || senha2.length() == 0) {
                    Toast.makeText(CriaSenhaActivity.this, "Preencha os campos corretamente.", Toast.LENGTH_LONG).show();
                } else if (!senha1.equals(senha2.getText().toString())) {
                    Toast.makeText(CriaSenhaActivity.this, "Senhas não conferem.", Toast.LENGTH_LONG).show();
                } else {
                    paciente.setSenha(senha2.getText().toString());
                    paciente.setCpf(cpfPaciente);
                    Log.d("teste", paciente.getCpf());
                    new TarefaCriaSenhaPaciente(CriaSenhaActivity.this).execute(paciente);
                }
            }
        });
    }

    public void retornoTarefaExterna(String retorno) {
        switch (retorno) {
            case "0": {
                Toast.makeText(this, "Senha Cadastrada com Sucesso.", Toast.LENGTH_LONG).show();
                Intent principal = new Intent(CriaSenhaActivity.this, PrincipalActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("CPF",paciente.getCpf());
                principal.putExtras(bundle);
                CriaSenhaActivity.this.startActivity(principal);
                finish();
                break;
            }
            case "1": {
                Toast.makeText(this, "Erro ao cadastrar a senha.", Toast.LENGTH_LONG).show();
                break;
            }
        }
    }
}
