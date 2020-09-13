package br.com.sonus.sonuscliente.Controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.sonus.sonuscliente.R;

public class ViewHolderLaudos extends RecyclerView.ViewHolder {

    public TextView txtDataLaudo;
    public TextView txtExameRealizado;
    public TextView txtMatricula;
    public Button btnLaudo;
    public Button btnFoto;

    public ViewHolderLaudos(View itemView) {
        super(itemView);
        this.txtDataLaudo = (TextView) itemView.findViewById(R.id.txtDataLaudo);
        this.txtExameRealizado = (TextView) itemView.findViewById(R.id.txtExameRealizado);
        this.txtMatricula = (TextView) itemView.findViewById(R.id.txtMatricula);
        this.btnLaudo = (Button) itemView.findViewById(R.id.btnLaudo);
        this.btnFoto = (Button) itemView.findViewById(R.id.btnFoto);
    }
}
