package br.com.sonus.sonuscliente.Controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.sonus.sonuscliente.R;

public class ViewHolderMensagensEnviadas extends RecyclerView.ViewHolder {

    public TextView txtDataEnvio;
    public TextView txtTituloMensagem;
    public TextView txtItemMensagem;
    public LinearLayout layoutHeader;


    public ViewHolderMensagensEnviadas(View itemView) {
        super(itemView);
        this.txtDataEnvio = (TextView) itemView.findViewById(R.id.txtDataEnvio);
        this.txtTituloMensagem = (TextView) itemView.findViewById(R.id.txtTituloMensagem);
        this.txtItemMensagem = (TextView) itemView.findViewById(R.id.txtItemMensagemEnviada);
        this.layoutHeader = (LinearLayout) itemView.findViewById(R.id.layoutHeaderListaMensagens);
    }
}
