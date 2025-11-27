package com.petcare.model;

import java.time.LocalDateTime;

public class Agendamento {
    private int id;
    private int petId;
    private int clienteId;
    private int servicoId;
    private int veterinarioId;
    private LocalDateTime dataHora;
    private String status; // agendado, em_atendimento, finalizado, cancelado
    private String observacoes;

    public Agendamento(int id, int petId, int clienteId, int servicoId, LocalDateTime dataHora) {
        this.id = id;
        this.petId = petId;
        this.clienteId = clienteId;
        this.servicoId = servicoId;
        this.dataHora = dataHora;
        this.status = "agendado";
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getPetId() { return petId; }
    public void setPetId(int petId) { this.petId = petId; }
    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }
    public int getServicoId() { return servicoId; }
    public void setServicoId(int servicoId) { this.servicoId = servicoId; }
    public int getVeterinarioId() { return veterinarioId; }
    public void setVeterinarioId(int veterinarioId) { this.veterinarioId = veterinarioId; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    @Override
    public String toString() {
        return "ID: " + id + " - " + dataHora.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}

