package com.petcare.view;

import com.petcare.model.Usuario;
import javax.swing.*;
import java.awt.*;

public class ConfiguracoesGeraisFrame extends JFrame {
    private JTextField horarioAberturaField;
    private JTextField horarioFechamentoField;
    private JTextArea politicasArea;
    private JTextField nomeClinicaField;
    private JTextField telefoneClinicaField;
    private JTextField emailClinicaField;

    public ConfiguracoesGeraisFrame(Usuario admin) {
        configurarTela();
    }

    private void configurarTela() {
        setTitle("Configurações Gerais");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Nome da Clínica
        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(new JLabel("Nome da Clínica:"), gbc);
        nomeClinicaField = new JTextField(20);
        nomeClinicaField.setText("PetCare Connect");
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painel.add(nomeClinicaField, gbc);

        // Telefone
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painel.add(new JLabel("Telefone:"), gbc);
        telefoneClinicaField = new JTextField(20);
        telefoneClinicaField.setText("(11) 3333-4444");
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painel.add(telefoneClinicaField, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painel.add(new JLabel("Email:"), gbc);
        emailClinicaField = new JTextField(20);
        emailClinicaField.setText("contato@petcare.com");
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painel.add(emailClinicaField, gbc);

        // Horário de Abertura
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painel.add(new JLabel("Horário de Abertura:"), gbc);
        horarioAberturaField = new JTextField(20);
        horarioAberturaField.setText("08:00");
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painel.add(horarioAberturaField, gbc);

        // Horário de Fechamento
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painel.add(new JLabel("Horário de Fechamento:"), gbc);
        horarioFechamentoField = new JTextField(20);
        horarioFechamentoField.setText("18:00");
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painel.add(horarioFechamentoField, gbc);

        // Políticas
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painel.add(new JLabel("Políticas:"), gbc);
        politicasArea = new JTextArea(5, 20);
        politicasArea.setLineWrap(true);
        politicasArea.setText("• Cancelamentos devem ser feitos com 24h de antecedência\n" +
                              "• Atendimentos atrasados podem ser remarcados\n" +
                              "• Pagamento pode ser realizado no local ou online");
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        painel.add(new JScrollPane(politicasArea), gbc);

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

    private void salvar() {
        JOptionPane.showMessageDialog(this, "Configurações salvas com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}

