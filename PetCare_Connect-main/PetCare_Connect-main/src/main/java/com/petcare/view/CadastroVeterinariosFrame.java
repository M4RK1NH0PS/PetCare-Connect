package com.petcare.view;

import com.petcare.data.Database;
import com.petcare.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CadastroVeterinariosFrame extends JFrame {
    private Database db;
    private JTable tabela;
    private JTextField nomeField;
    private JTextField emailField;
    private JTextField senhaField;
    private JTextField crmvField;
    private JTextField especialidadeField;

    public CadastroVeterinariosFrame(Usuario admin) {
        this.db = Database.getInstance();
        configurarTela();
    }

    private void configurarTela() {
        setTitle("Cadastro de Veterinários");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Painel de formulário
        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBorder(BorderFactory.createTitledBorder("Novo Veterinário"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        painelForm.add(new JLabel("Nome:"), gbc);
        nomeField = new JTextField(15);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelForm.add(nomeField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        painelForm.add(new JLabel("Email:"), gbc);
        emailField = new JTextField(15);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelForm.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        painelForm.add(new JLabel("Senha:"), gbc);
        senhaField = new JTextField(15);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelForm.add(senhaField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        painelForm.add(new JLabel("CRMV:"), gbc);
        crmvField = new JTextField(15);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelForm.add(crmvField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        painelForm.add(new JLabel("Especialidade:"), gbc);
        especialidadeField = new JTextField(15);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelForm.add(especialidadeField, gbc);

        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.addActionListener(e -> adicionar());
        JButton btnRemover = new JButton("Remover Selecionado");
        btnRemover.addActionListener(e -> remover());
        JPanel painelBotoesForm = new JPanel();
        painelBotoesForm.add(btnAdicionar);
        painelBotoesForm.add(btnRemover);
        painelForm.add(painelBotoesForm, gbc);

        // Tabela
        String[] colunas = {"ID", "Nome", "Email", "CRMV", "Especialidade"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(model);
        carregarVeterinarios(model);

        JScrollPane scrollPane = new JScrollPane(tabela);

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.add(painelForm, BorderLayout.NORTH);
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnFechar);

        add(painelPrincipal, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void carregarVeterinarios(DefaultTableModel model) {
        model.setRowCount(0);
        for (Usuario u : db.getUsuarios()) {
            if (u instanceof Veterinario) {
                Veterinario vet = (Veterinario) u;
                model.addRow(new Object[]{
                    vet.getId(),
                    vet.getNome(),
                    vet.getEmail(),
                    vet.getCrmv(),
                    vet.getEspecialidade()
                });
            }
        }
    }

    private void adicionar() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = senhaField.getText();
        String crmv = crmvField.getText();
        String especialidade = especialidadeField.getText();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || crmv.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int nextId = db.getUsuarios().stream().mapToInt(Usuario::getId).max().orElse(0) + 1;
        Veterinario novoVet = new Veterinario(nextId, nome, email, senha, crmv, especialidade);
        db.getUsuarios().add(novoVet);

        limparCampos();
        carregarVeterinarios((DefaultTableModel) tabela.getModel());
        JOptionPane.showMessageDialog(this, "Veterinário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void remover() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veterinário!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = (Integer) tabela.getValueAt(linha, 0);
        db.getUsuarios().removeIf(u -> u.getId() == id);
        carregarVeterinarios((DefaultTableModel) tabela.getModel());
        JOptionPane.showMessageDialog(this, "Veterinário removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void limparCampos() {
        nomeField.setText("");
        emailField.setText("");
        senhaField.setText("");
        crmvField.setText("");
        especialidadeField.setText("");
    }
}

