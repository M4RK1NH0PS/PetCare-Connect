package com.petcare.model;

public class Veterinario extends Usuario {
    private String crmv;
    private String especialidade;

    public Veterinario(int id, String nome, String email, String senha, String crmv, String especialidade) {
        super(id, nome, email, senha, TipoUsuario.VETERINARIO);
        this.crmv = crmv;
        this.especialidade = especialidade;
    }

    public String getCrmv() { return crmv; }
    public void setCrmv(String crmv) { this.crmv = crmv; }
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
}

