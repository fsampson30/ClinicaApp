package br.com.sonus.sonuscliente.Controller;

public class MontaData {

    public String retornaDataMontada(int dia, int mes, int ano) {

        String mesTexto;
        String diaTexto;
        String anoTexto;

        if (String.valueOf(mes).length() == 1) {
            mesTexto = "0" + String.valueOf(mes);
        } else {
            mesTexto = String.valueOf(mes);
        }

        if (String.valueOf(dia).length() == 1) {
            diaTexto = "0" + String.valueOf(dia);
        } else {
            diaTexto = String.valueOf(dia);
        }

        return diaTexto + "/" + mesTexto + "/" + String.valueOf(ano);
    }
}

