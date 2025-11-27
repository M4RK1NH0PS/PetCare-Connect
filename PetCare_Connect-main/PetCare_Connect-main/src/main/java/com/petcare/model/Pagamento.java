package com.petcare.model;

import java.time.LocalDateTime;

public class Pagamento {
    private int id;
    private int agendamentoId;
    private double valor;
    private LocalDateTime dataPagamento;
    private String status; // pendente, pago, cancelado

    public Pagamento(int id, int agendamentoId, double valor) {
        this.id = id;
        this.agendamentoId = agendamentoId;
        this.valor = valor;
        this.status = "pendente";
        this.dataPagamento = null;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getAgendamentoId() { return agendamentoId; }
    public void setAgendamentoId(int agendamentoId) { this.agendamentoId = agendamentoId; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
    public LocalDateTime getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDateTime dataPagamento) { this.dataPagamento = dataPagamento; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

