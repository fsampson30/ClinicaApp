package br.com.sonus.sonuscliente.Controller;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import br.com.sonus.sonuscliente.Model.Foto;
import br.com.sonus.sonuscliente.View.AgendamentoActivity;
import br.com.sonus.sonuscliente.View.PrincipalActivity;

public class TarefaEnviaFotoExterno extends AsyncTask<String, Void, String> {

    private AgendamentoActivity context;
    private CaminhoServlets caminho = new CaminhoServlets();

    public TarefaEnviaFotoExterno(Context context) {
        this.context = ((AgendamentoActivity) context);
    }

    @Override
    protected void onPreExecute() {
        context.barraCircular.setVisibility(View.VISIBLE);
    }


    @Override
    protected String doInBackground(String... strings) {
        String caminhoFoto = strings[0];
        String cpfPaciente = strings[1];
        try {
            URL url = new URL(caminho.getCaminho() + "/ServletArmazenaFoto");
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setDoInput(true);
            conexao.setRequestMethod("POST");
            FileInputStream arquivo = new FileInputStream(caminhoFoto);
            byte[] foto = new byte[arquivo.available()];
            arquivo.read(foto);
            arquivo.close();

            DataOutputStream dos = new DataOutputStream(conexao.getOutputStream());
            dos.write(foto);
            dos.close();

            BufferedReader reader = null;
            reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String resposta = URLDecoder.decode(reader.readLine(), "UTF-8");

            Log.d("retorno", resposta);
            reader.close();
            return resposta;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String retorno) {
        context.barraCircular.setVisibility(View.GONE);
        this.context.retornoTarefaExterna(retorno);
    }

}
