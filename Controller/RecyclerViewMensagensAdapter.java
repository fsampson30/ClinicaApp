package br.com.sonus.sonuscliente.Controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.sonus.sonuscliente.R;

public class RecyclerViewMensagensAdapter extends RecyclerView.Adapter<ViewHolderMensagensEnviadas> {

    private HashMap<String, ArrayList<String>> msg;

    public RecyclerViewMensagensAdapter(HashMap<String, ArrayList<String>> msg) {
        this.msg = msg;
    }

    @Override
    public ViewHolderMensagensEnviadas onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_mensagem,parent,false);
        return new ViewHolderMensagensEnviadas(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderMensagensEnviadas holder, int position) {
        holder.txtDataEnvio.setText(msg.get("data").get(position));
        holder.txtTituloMensagem.setText(msg.get("titulo").get(position));
        holder.txtItemMensagem.setText(msg.get("texto").get(position));
    }

    @Override
    public int getItemCount() {
        return msg.get("data").size();
    }
}

