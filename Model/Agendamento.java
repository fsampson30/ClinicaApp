package br.com.sonus.sonuscliente.Model;

public class Agendamento {

    private String data_exame;
    private String turno;
    private String codigo_secao;
    private String cpf_paciente;
    private String data_solicitacao;

    public String getData_exame() {
        return data_exame;
    }

    public void setData_exame(String data_exame) {
        this.data_exame = data_exame;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getCodigo_secao() {
        return codigo_secao;
    }

    public void setCodigo_secao(String codigo_secao) {
        this.codigo_secao = codigo_secao;
    }

    public String getCpf_paciente() {
        return cpf_paciente;
    }

    public void setCpf_paciente(String cpf_paciente) {
        this.cpf_paciente = cpf_paciente;
    }

    public String getData_solicitacao() {
        return data_solicitacao;
    }

    public void setData_solicitacao(String data_solicitacao) {
        this.data_solicitacao = data_solicitacao;
    }
}
