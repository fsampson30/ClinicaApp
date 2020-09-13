package br.com.sonus.sonuscliente.Model;

public class Mensagem {

    private String data_envio;
    private String titulo_texto;
    private String cpf;
    private String texto;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getData_envio() {

        return data_envio;
    }

    public void setData_envio(String data_envio) {

        this.data_envio = data_envio;
    }

    public String getTitulo_texto() {

        return titulo_texto;
    }

    public void setTitulo_texto(String titulo_texto) {

        this.titulo_texto = titulo_texto;
    }
}


