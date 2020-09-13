package br.com.sonus.sonuscliente.Controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

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
import br.com.sonus.sonuscliente.View.LaudosActivity;

public class TarefaRecebeListaLaudos extends AsyncTask<String, Void, HashMap<String, ArrayList<String>>> {

    private LaudosActivity context;
    private CaminhoServlets caminho = new CaminhoServlets();

    public TarefaRecebeListaLaudos(LaudosActivity context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        context.barraCircular.setVisibility(View.VISIBLE);
    }

    @Override
    protected HashMap<String, ArrayList<String>> doInBackground(String... strings) {

        try {
            URLConnection conexao = new URL(caminho.getCaminho() + "/ServletRetornaListaLaudos").openConnection();
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

            HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
            Type listType = new TypeToken<HashMap<String, ArrayList<String>>>() {
            }.getType();
            map = new Gson().fromJson(resposta, listType);

            reader.close();
            return map;

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
    protected void onPostExecute(HashMap<String, ArrayList<String>> strings) {
        context.barraCircular.setVisibility(View.GONE);
        if (strings == null) {
            Toast.makeText(context, "Erro de conexão com o servidor. Tente novamente.", Toast.LENGTH_SHORT).show();
        } else if (strings.get("matricula").size() == 0) {
            Toast.makeText(context, "Sem laudos para exibir.", Toast.LENGTH_SHORT).show();
        } else {
            context.retornoTarefaExterna(strings);
        }
    }
}
