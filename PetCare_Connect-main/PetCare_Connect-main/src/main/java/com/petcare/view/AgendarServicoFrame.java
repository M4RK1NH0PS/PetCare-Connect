package com.petcare.view;

import com.petcare.data.Database;
import com.petcare.model.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AgendarServicoFrame extends JFrame {
    private Database db;
    private Usuario cliente;
    private JComboBox<Pet> petCombo;
    private JComboBox<Servico> servicoCombo;
    private JTextField dataField;
    private JTextField horaField;

    public AgendarServicoFrame(Usuario cliente) {
        this.cliente = cliente;
        this.db = Database.getInstance();
        configurarTela();
    }

    private void configurarTela() {
        setTitle("Agendar Serviço");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Pet
        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(new JLabel("Pet:"), gbc);
        petCombo = new JComboBox<>();
        carregarPets();
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painel.add(petCombo, gbc);

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
        JButton btnAgendar = new JButton("Agendar");
        btnAgendar.addActionListener(e -> agendar());
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        painelBotoes.add(btnAgendar);
        painelBotoes.add(btnCancelar);

        add(painel, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void carregarPets() {
        petCombo.removeAllItems();
        for (Pet pet : db.getPetsByCliente(cliente.getId())) {
            petCombo.addItem(pet);
        }
    }

    private void agendar() {
        Pet pet = (Pet) petCombo.getSelectedItem();
        Servico servico = (Servico) servicoCombo.getSelectedItem();
        String dataStr = dataField.getText();
        String horaStr = horaField.getText();

        if (pet == null || servico == null || dataStr.isEmpty() || horaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalDateTime dataHora = LocalDateTime.parse(dataStr + " " + horaStr, 
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

            Agendamento agendamento = new Agendamento(
                db.getNextAgendamentoId(),
                pet.getId(),
                cliente.getId(),
                servico.getId(),
                dataHora
            );
            db.getAgendamentos().add(agendamento);

            // Criar pagamento pendente
            Pagamento pagamento = new Pagamento(db.getNextPagamentoId(), agendamento.getId(), servico.getPreco());
            db.getPagamentos().add(pagamento);

            JOptionPane.showMessageDialog(this, "Agendamento realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Data ou hora inválida! Use o formato: dd/MM/yyyy e HH:mm", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

