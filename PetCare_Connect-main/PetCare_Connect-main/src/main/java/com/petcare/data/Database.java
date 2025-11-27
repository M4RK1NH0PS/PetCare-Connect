package com.petcare.data;

import com.petcare.model.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static Database instance;
    private List<Usuario> usuarios;
    private List<Pet> pets;
    private List<Servico> servicos;
    private List<Agendamento> agendamentos;
    private List<Pagamento> pagamentos;
    private int nextUsuarioId = 1;
    private int nextPetId = 1;
    private int nextServicoId = 1;
    private int nextAgendamentoId = 1;
    private int nextPagamentoId = 1;

    private Database() {
        usuarios = new ArrayList<>();
        pets = new ArrayList<>();
        servicos = new ArrayList<>();
        agendamentos = new ArrayList<>();
        pagamentos = new ArrayList<>();
        
        // Dados iniciais para teste
        inicializarDados();
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private void inicializarDados() {
        // Criar admin
        usuarios.add(new Usuario(nextUsuarioId++, "Admin", "admin@petcare.com", "admin", Usuario.TipoUsuario.ADMIN));
        
        // Criar veterinário
        usuarios.add(new Veterinario(nextUsuarioId++, "Dr. João", "joao@petcare.com", "123", "CRMV-12345", "Clínica Geral"));
        
        // Criar cliente
        usuarios.add(new Cliente(nextUsuarioId++, "Maria Silva", "maria@email.com", "123", "(11) 99999-9999", "Rua A, 123"));
        
        // Criar pet
        pets.add(new Pet(nextPetId++, "Rex", "Cachorro", "Labrador", 3, 3));
        
        // Criar serviços
        servicos.add(new Servico(nextServicoId++, "Banho", "banho", 50.0, "Banho completo"));
        servicos.add(new Servico(nextServicoId++, "Tosa", "tosa", 80.0, "Tosa completa"));
        servicos.add(new Servico(nextServicoId++, "Vacina", "vacina", 100.0, "Vacinação"));
        servicos.add(new Servico(nextServicoId++, "Consulta", "consulta", 150.0, "Consulta veterinária"));
    }

    // Getters
    public List<Usuario> getUsuarios() { return usuarios; }
    public List<Pet> getPets() { return pets; }
    public List<Servico> getServicos() { return servicos; }
    public List<Agendamento> getAgendamentos() { return agendamentos; }
    public List<Pagamento> getPagamentos() { return pagamentos; }

    // Métodos auxiliares
    public Usuario login(String email, String senha) {
        return usuarios.stream()
            .filter(u -> u.getEmail().equals(email) && u.getSenha().equals(senha))
            .findFirst()
            .orElse(null);
    }

    public List<Pet> getPetsByCliente(int clienteId) {
        List<Pet> resultado = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet.getClienteId() == clienteId) {
                resultado.add(pet);
            }
        }
        return resultado;
    }

    public List<Agendamento> getAgendamentosByCliente(int clienteId) {
        List<Agendamento> resultado = new ArrayList<>();
        for (Agendamento ag : agendamentos) {
            if (ag.getClienteId() == clienteId) {
                resultado.add(ag);
            }
        }
        return resultado;
    }

    public List<Agendamento> getAgendamentosByVeterinario(int vetId) {
        List<Agendamento> resultado = new ArrayList<>();
        for (Agendamento ag : agendamentos) {
            if (ag.getVeterinarioId() == vetId) {
                resultado.add(ag);
            }
        }
        return resultado;
    }

    public List<Agendamento> getAgendamentosDoDia() {
        List<Agendamento> resultado = new ArrayList<>();
        LocalDateTime hoje = LocalDateTime.now();
        for (Agendamento ag : agendamentos) {
            if (ag.getDataHora().toLocalDate().equals(hoje.toLocalDate())) {
                resultado.add(ag);
            }
        }
        return resultado;
    }

    public int getNextPetId() { return nextPetId++; }
    public int getNextServicoId() { return nextServicoId++; }
    public int getNextAgendamentoId() { return nextAgendamentoId++; }
    public int getNextPagamentoId() { return nextPagamentoId++; }
}

