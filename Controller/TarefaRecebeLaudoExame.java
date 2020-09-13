package br.com.sonus.sonuscliente.Controller;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import br.com.sonus.sonuscliente.BuildConfig;
import br.com.sonus.sonuscliente.View.AgendamentoActivity;
import br.com.sonus.sonuscliente.View.LaudosActivity;

public class TarefaRecebeLaudoExame extends AsyncTask<String, Void, String> {

    private ProgressDialog pb;
    private LaudosActivity context;
    private RecyclerViewLaudosAdapter la;
    private CaminhoServlets caminho = new CaminhoServlets();

    public TarefaRecebeLaudoExame(LaudosActivity context, RecyclerViewLaudosAdapter la) {
        this.context = context;
        this.la = la;
    }

    @Override
    protected void onPreExecute() {
        context.barraCircular.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URLConnection conexao = new URL(caminho.getCaminho()+"/ServletRetornaLaudos").openConnection();
            conexao.setDoOutput(true);
            conexao.setConnectTimeout(10000);
            String caminhoLaudo = strings[0];
            String codigoExame = strings[1];
            Log.d("Caminho",caminhoLaudo);

            String parametro = URLEncoder.encode(caminhoLaudo, "UTF-8");

            OutputStreamWriter localOutputStreamWriter = new OutputStreamWriter(conexao.getOutputStream());
            localOutputStreamWriter.write(parametro);
            localOutputStreamWriter.close();

            InputStream in = conexao.getInputStream();

            byte [] laudo = new byte[4096];

            String caminhoFoto = context.getExternalFilesDir(null) +"/"+codigoExame+".pdf";
            File pdf = new File(caminhoFoto);

            long total = 0;
            int count;
            FileOutputStream fos = new FileOutputStream(caminhoFoto);

            while ((count = in.read(laudo)) != -1) {
                total += count;
                fos.write(laudo, 0, count);
                System.out.println(count);
            }
            fos.flush();
            fos.close();
            in.close();

            return caminhoFoto;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        context.barraCircular.setVisibility(View.GONE);
        la.retornaLaudo(s);

    }
}
