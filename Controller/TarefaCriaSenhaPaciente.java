package br.com.sonus.sonuscliente.Controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;

import br.com.sonus.sonuscliente.Model.Paciente;
import br.com.sonus.sonuscliente.View.CriaSenhaActivity;

public class TarefaCriaSenhaPaciente extends AsyncTask<Paciente, Void, String> {

    private CriaSenhaActivity context;
    private CaminhoServlets caminho = new CaminhoServlets();

    public TarefaCriaSenhaPaciente(CriaSenhaActivity context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        context.barraCircular.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(Paciente... strings) {
        try {
            URLConnection conexao = new URL(caminho.getCaminho()+"/ServletCriaSenhaPaciente").openConnection();
            conexao.setDoOutput(true);
            conexao.setConnectTimeout(10000);
            Paciente paciente = strings[0];
            String parametro = URLEncoder.encode(new Gson().toJson(paciente, Paciente.class), "UTF-8");
            OutputStreamWriter localOutputStreamWriter = new OutputStreamWriter(conexao.getOutputStream());
            localOutputStreamWriter.write(parametro.toString());
            localOutputStreamWriter.close();
            BufferedReader reader = null;
            reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String resposta = URLDecoder.decode(reader.readLine(), "UTF-8");
            Log.d("retorno", resposta);
            reader.close();
            return resposta;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e){
            e.printStackTrace();
            return "9";
        } catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String resposta) {
        context.barraCircular.setVisibility(View.GONE);
        this.context.retornoTarefaExterna(resposta);

    }
}
