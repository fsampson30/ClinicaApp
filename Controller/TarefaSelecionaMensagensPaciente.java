package br.com.sonus.sonuscliente.Controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import br.com.sonus.sonuscliente.R;
import br.com.sonus.sonuscliente.View.MensagemActivity;

public class TarefaSelecionaMensagensPaciente extends AsyncTask<String, Void, HashMap<String, ArrayList<String>>> {

    private MensagemActivity context;
    private CaminhoServlets caminho = new CaminhoServlets();

    public TarefaSelecionaMensagensPaciente(MensagemActivity context) {
        this.context = (MensagemActivity) context;
    }

    @Override
    protected void onPreExecute() {
        context.barraCircular.setVisibility(View.VISIBLE);
        context.barraCircular.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    @Override
    protected HashMap<String, ArrayList<String>> doInBackground(String... strings) {
        try {
            URLConnection conexao = new URL(caminho.getCaminho() + "/ServletConsultaMensagensPaciente").openConnection();
            conexao.setDoOutput(true);
            conexao.setConnectTimeout(10000);
            String cpfPaciente = strings[0];
            cpfPaciente = URLEncoder.encode(cpfPaciente, "UTF-8");

            OutputStreamWriter localOutputStreamWriter = new OutputStreamWriter(conexao.getOutputStream());
            localOutputStreamWriter.write(cpfPaciente);
            localOutputStreamWriter.close();

            BufferedReader reader = null;
            reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String resposta = URLDecoder.decode(reader.readLine(), "UTF-8");

            Log.d("TESTE", resposta.toString());

            HashMap<String, ArrayList<String>> msg = new HashMap<String, ArrayList<String>>();
            Type listType = new TypeToken<HashMap<String, ArrayList<String>>>() {
            }.getType();
            msg = new Gson().fromJson(resposta, listType);
            Log.d("MONTANDO RESPOSTA:", msg.toString());

            reader.close();
            return msg;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(HashMap<String, ArrayList<String>> list) {
        context.barraCircular.setVisibility(View.GONE);
        if (list == null) {
            Toast.makeText(context, "Erro de conexão com o servidor. Tente novamente.", Toast.LENGTH_SHORT).show();
        } else if (list.get("data").size() == 0) {
            Toast.makeText(context, "Sem mensagens para exibir.", Toast.LENGTH_SHORT).show();
        } else {
            context.retornoTarefaExterna(list);
        }
    }
}
