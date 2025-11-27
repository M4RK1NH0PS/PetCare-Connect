package com.petcare.model;

public class Servico {
    private int id;
    private String nome;
    private String tipo; // banho, tosa, vacina, consulta
    private double preco;
    private String descricao;

    public Servico(int id, String nome, String tipo, double preco, String descricao) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.preco = preco;
        this.descricao = descricao;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    @Override
    public String toString() {
        return nome + " - R$ " + String.format("%.2f", preco);
    }
}

