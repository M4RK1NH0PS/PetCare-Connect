package com.petcare.view;

import com.petcare.data.Database;
import com.petcare.model.*;
import javax.swing.*;
import java.awt.*;

public class DashboardAdminFrame extends JFrame {
    private Database db;

    public DashboardAdminFrame(Usuario admin) {
        this.db = Database.getInstance();
        configurarTela();
    }

    private void configurarTela() {
        setTitle("Dashboard Administrativo");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Calcular estatísticas
        long numClientes = db.getUsuarios().stream().filter(u -> u.getTipo() == Usuario.TipoUsuario.CLIENTE).count();
        long numVets = db.getUsuarios().stream().filter(u -> u.getTipo() == Usuario.TipoUsuario.VETERINARIO).count();
        int numPets = db.getPets().size();
        int numAtendimentos = db.getAgendamentos().size();
        double totalArrecadado = db.getPagamentos().stream()
            .filter(p -> p.getStatus().equals("pago"))
            .mapToDouble(Pagamento::getValor)
            .sum();

        JLabel titulo = new JLabel("Painel de Controle - PetCare Connect");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        painel.add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(new JLabel("Número de Clientes:"), gbc);
        gbc.gridx = 1;
        painel.add(new JLabel(String.valueOf(numClientes)), gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        painel.add(new JLabel("Número de Veterinários:"), gbc);
        gbc.gridx = 1;
        painel.add(new JLabel(String.valueOf(numVets)), gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        painel.add(new JLabel("Número de Pets:"), gbc);
        gbc.gridx = 1;
        painel.add(new JLabel(String.valueOf(numPets)), gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        painel.add(new JLabel("Total de Atendimentos:"), gbc);
        gbc.gridx = 1;
        painel.add(new JLabel(String.valueOf(numAtendimentos)), gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        painel.add(new JLabel("Total Arrecadado:"), gbc);
        gbc.gridx = 1;
        painel.add(new JLabel(String.format("R$ %.2f", totalArrecadado)), gbc);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnFechar);

        add(painel, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }
}

