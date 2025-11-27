package com.petcare.view;

import com.petcare.data.Database;
import com.petcare.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PetsClientesFrame extends JFrame {
    private Database db;
    private JTable tabelaPets;
    private JTable tabelaClientes;

    public PetsClientesFrame(Usuario admin) {
        this.db = Database.getInstance();
        configurarTela();
    }

    private void configurarTela() {
        setTitle("Pets e Clientes");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Abas
        JTabbedPane abas = new JTabbedPane();

        // Aba Clientes
        String[] colunasClientes = {"ID", "Nome", "Email", "Telefone", "Endereço"};
        DefaultTableModel modelClientes = new DefaultTableModel(colunasClientes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaClientes = new JTable(modelClientes);
        carregarClientes(modelClientes);
        abas.addTab("Clientes", new JScrollPane(tabelaClientes));

        // Aba Pets
        String[] colunasPets = {"ID", "Nome", "Espécie", "Raça", "Idade", "Tutor"};
        DefaultTableModel modelPets = new DefaultTableModel(colunasPets, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaPets = new JTable(modelPets);
        carregarPets(modelPets);
        abas.addTab("Pets", new JScrollPane(tabelaPets));

        add(abas, BorderLayout.CENTER);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnFechar);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void carregarClientes(DefaultTableModel model) {
        model.setRowCount(0);
        for (Usuario u : db.getUsuarios()) {
            if (u instanceof Cliente) {
                Cliente cliente = (Cliente) u;
                model.addRow(new Object[]{
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getEmail(),
                    cliente.getTelefone(),
                    cliente.getEndereco()
                });
            }
        }
    }

    private void carregarPets(DefaultTableModel model) {
        model.setRowCount(0);
        for (Pet pet : db.getPets()) {
            Usuario cliente = db.getUsuarios().stream()
                .filter(u -> u.getId() == pet.getClienteId())
                .findFirst().orElse(null);
            String tutorNome = cliente != null ? cliente.getNome() : "N/A";

            model.addRow(new Object[]{
                pet.getId(),
                pet.getNome(),
                pet.getEspecie(),
                pet.getRaca(),
                pet.getIdade(),
                tutorNome
            });
        }
    }
}

