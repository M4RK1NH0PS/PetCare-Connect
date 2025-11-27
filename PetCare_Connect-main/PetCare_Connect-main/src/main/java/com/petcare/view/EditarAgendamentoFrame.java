package com.petcare.view;

import com.petcare.data.Database;
import com.petcare.model.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EditarAgendamentoFrame extends JFrame {
    private Database db;
    private Usuario cliente;
    private JComboBox<Agendamento> agendamentoCombo;
    private JTextField dataField;
    private JTextField horaField;
    private JComboBox<Servico> servicoCombo;

    public EditarAgendamentoFrame(Usuario cliente) {
        this.cliente = cliente;
        this.db = Database.getInstance();
        configurarTela();
    }

    private void configurarTela() {
        setTitle("Editar Agendamento");
        setSize(500, 400);
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
        agendamentoCombo.addActionListener(e -> preencherCampos());
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painel.add(agendamentoCombo, gbc);

        // Serviço
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painel.add(new JLabel("Tipo de Serviço:"), gbc);
        servicoCombo = new JComboBox<>();
        for (Servico s : db.getServicos()) {
            servicoCombo.addItem(s);
        }
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painel.add(servicoCombo, gbc);

        // Data
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painel.add(new JLabel("Data (dd/MM/yyyy):"), gbc);
        dataField = new JTextField(15);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painel.add(dataField, gbc);

        // Hora
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painel.add(new JLabel("Hora (HH:mm):"), gbc);
        horaField = new JTextField(15);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painel.add(horaField, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvar());
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);

        add(painel, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void carregarAgendamentos() {
        agendamentoCombo.removeAllItems();
        for (Agendamento ag : db.getAgendamentosByCliente(cliente.getId())) {
            if (!ag.getStatus().equals("finalizado") && !ag.getStatus().equals("cancelado")) {
                agendamentoCombo.addItem(ag);
            }
        }
    }

    private void preencherCampos() {
        Agendamento ag = (Agendamento) agendamentoCombo.getSelectedItem();
        if (ag != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            String dataHora = ag.getDataHora().format(formatter);
            String[] partes = dataHora.split(" ");
            dataField.setText(partes[0]);
            horaField.setText(partes[1]);

            Servico servico = db.getServicos().stream()
                .filter(s -> s.getId() == ag.getServicoId())
                .findFirst().orElse(null);
            if (servico != null) {
                servicoCombo.setSelectedItem(servico);
            }
        }
    }

    private void salvar() {
        Agendamento ag = (Agendamento) agendamentoCombo.getSelectedItem();
        if (ag == null) {
            JOptionPane.showMessageDialog(this, "Selecione um agendamento!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String dataStr = dataField.getText();
            String horaStr = horaField.getText();
            LocalDateTime dataHora = LocalDateTime.parse(dataStr + " " + horaStr, 
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

            Servico servico = (Servico) servicoCombo.getSelectedItem();
            ag.setDataHora(dataHora);
            ag.setServicoId(servico.getId());

            JOptionPane.showMessageDialog(this, "Agendamento atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Data ou hora inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

