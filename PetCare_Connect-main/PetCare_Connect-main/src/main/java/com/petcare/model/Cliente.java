package com.petcare.model;

public class Cliente extends Usuario {
    private String telefone;
    private String endereco;

    public Cliente(int id, String nome, String email, String senha, String telefone, String endereco) {
        super(id, nome, email, senha, TipoUsuario.CLIENTE);
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
}

