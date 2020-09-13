package br.com.sonus.sonuscliente.Controller;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import br.com.sonus.sonuscliente.R;
import br.com.sonus.sonuscliente.View.LaudosActivity;

public class RecyclerViewLaudosAdapter extends RecyclerView.Adapter<ViewHolderLaudos> {

    private HashMap<String, ArrayList<String>> msg;
    private String caminhoLaudo;
    private String codigoExame;
    private String caminhoFotos;
    private LaudosActivity context;

    public RecyclerViewLaudosAdapter(HashMap<String, ArrayList<String>> msg, LaudosActivity context) {
        this.msg = msg;
        this.context = context;
    }

    @Override
    public ViewHolderLaudos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_laudo, parent, false);
        return new ViewHolderLaudos(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderLaudos holder, final int position) {
        holder.txtDataLaudo.setText(msg.get("data").get(position));
        holder.txtExameRealizado.setText(msg.get("nome_exame").get(position));
        holder.txtMatricula.setText(msg.get("matricula").get(position));

        holder.btnLaudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                caminhoLaudo = msg.get("caminhoLaudo").get(position) + "/" + msg.get("exame").get(position) + ".pdf";
                codigoExame = msg.get("exame").get(position);
                new TarefaRecebeLaudoExame(context, RecyclerViewLaudosAdapter.this).execute(caminhoLaudo, codigoExame);
            }
        });

        holder.btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                caminhoFotos = msg.get("caminhoLaudo").get(position) +"/"+ "fotos.pdf";
                new TarefaRecebeFotosExame(context, RecyclerViewLaudosAdapter.this).execute(caminhoFotos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return msg.get("data").size();
    }


    public void retornaLaudo(String caminhoLaudo) {
        File file = new File(caminhoLaudo);
        Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);
    }

    public void retornaFoto(String caminhoFoto) {
        File file = new File(caminhoFoto);
        Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);
    }
}

