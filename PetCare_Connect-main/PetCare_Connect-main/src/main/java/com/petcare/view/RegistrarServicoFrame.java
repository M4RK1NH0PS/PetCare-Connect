package com.petcare.view;

import com.petcare.data.Database;
import com.petcare.model.*;
import javax.swing.*;
import java.awt.*;

public class RegistrarServicoFrame extends JFrame {
    private Database db;
    private Usuario veterinario;
    private JComboBox<Agendamento> agendamentoCombo;
    private JTextArea observacoesArea;

    public RegistrarServicoFrame(Usuario veterinario) {
        this.veterinario = veterinario;
        this.db = Database.getInstance();
        configurarTela();
    }

    private void configurarTela() {
        setTitle("Registrar Serviço Realizado");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Agendamento
        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(new JLabel("Agendamento:"), gbc);
        agendamentoCombo = new JComboBox<>();
        carregarAgendamentos();
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painel.add(agendamentoCombo, gbc);

        // Observações
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painel.add(new JLabel("Observações:"), gbc);
        observacoesArea = new JTextArea(10, 30);
        observacoesArea.setLineWrap(true);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        painel.add(new JScrollPane(observacoesArea), gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> registrar());
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        painelBotoes.add(btnRegistrar);
        painelBotoes.add(btnCancelar);

        add(painel, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void carregarAgendamentos() {
        agendamentoCombo.removeAllItems();
        for (Agendamento ag : db.getAgendamentos()) {
            if (ag.getVeterinarioId() == veterinario.getId() && 
                (ag.getStatus().equals("agendado") || ag.getStatus().equals("em_atendimento"))) {
                agendamentoCombo.addItem(ag);
            }
        }
    }

    private void registrar() {
        Agendamento ag = (Agendamento) agendamentoCombo.getSelectedItem();
        if (ag == null) {
            JOptionPane.showMessageDialog(this, "Selecione um agendamento!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ag.setStatus("finalizado");
        ag.setObservacoes(observacoesArea.getText());
        ag.setVeterinarioId(veterinario.getId());

        JOptionPane.showMessageDialog(this, "Serviço registrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}

