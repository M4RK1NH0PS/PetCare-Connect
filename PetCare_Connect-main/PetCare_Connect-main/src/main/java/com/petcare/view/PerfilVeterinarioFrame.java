package com.petcare.view;

import com.petcare.data.Database;
import com.petcare.model.*;
import javax.swing.*;
import java.awt.*;

public class PerfilVeterinarioFrame extends JFrame {
    private Database db;
    private Usuario veterinario;
    private JTextField nomeField;
    private JTextField emailField;
    private JTextField crmvField;
    private JTextField especialidadeField;
    private JPasswordField senhaField;

    public PerfilVeterinarioFrame(Usuario veterinario) {
        this.veterinario = veterinario;
        this.db = Database.getInstance();
        configurarTela();
    }

    private void configurarTela() {
        setTitle("Meu Perfil");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Nome
        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(new JLabel("Nome:"), gbc);
        nomeField = new JTextField(20);
        nomeField.setText(veterinario.getNome());
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painel.add(nomeField, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painel.add(new JLabel("Email:"), gbc);
        emailField = new JTextField(20);
        emailField.setText(veterinario.getEmail());
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painel.add(emailField, gbc);

        // CRMV
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painel.add(new JLabel("CRMV:"), gbc);
        crmvField = new JTextField(20);
        if (veterinario instanceof Veterinario) {
            crmvField.setText(((Veterinario) veterinario).getCrmv());
        }
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painel.add(crmvField, gbc);

        // Especialidade
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painel.add(new JLabel("Especialidade:"), gbc);
        especialidadeField = new JTextField(20);
        if (veterinario instanceof Veterinario) {
            especialidadeField.setText(((Veterinario) veterinario).getEspecialidade());
        }
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painel.add(especialidadeField, gbc);

        // Senha
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painel.add(new JLabel("Nova Senha:"), gbc);
        senhaField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painel.add(senhaField, gbc);

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
        veterinario.setNome(nomeField.getText());
        veterinario.setEmail(emailField.getText());
        String novaSenha = new String(senhaField.getPassword());
        if (!novaSenha.isEmpty()) {
            veterinario.setSenha(novaSenha);
        }

        if (veterinario instanceof Veterinario) {
            ((Veterinario) veterinario).setCrmv(crmvField.getText());
            ((Veterinario) veterinario).setEspecialidade(especialidadeField.getText());
        }

        JOptionPane.showMessageDialog(this, "Perfil atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}

