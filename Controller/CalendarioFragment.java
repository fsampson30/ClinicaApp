package br.com.sonus.sonuscliente.Controller;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import java.util.Calendar;
import br.com.sonus.sonuscliente.R;


public class CalendarioFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private int ano;
    private int mes;
    private int dia;
    private String dataSelecionada="";
    private Context context;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        ano = c.get(Calendar.YEAR);
        mes = c.get(Calendar.MONTH);
        dia = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this,ano,mes,dia);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
        mes +=1;
        MontaData mt = new MontaData();
        String dataSelecionada = mt.retornaDataMontada(dia,mes,ano);
        ((TextView) getActivity().findViewById(R.id.txtDataEscolhida)).setText(dataSelecionada);
    }

    @Override
    public void onResume() {
        super.onResume();
        dataSelecionada();
    }

    public String dataSelecionada(){
        return dataSelecionada;
    }
}
