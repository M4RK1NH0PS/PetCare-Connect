package com.petcare.view;

import com.petcare.data.Database;
import com.petcare.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CadastroServicosFrame extends JFrame {
    private Database db;
    private JTable tabela;
    private JTextField nomeField;
    private JComboBox<String> tipoCombo;
    private JTextField precoField;
    private JTextArea descricaoArea;

    public CadastroServicosFrame(Usuario admin) {
        this.db = Database.getInstance();
        configurarTela();
    }

    private void configurarTela() {
        setTitle("Cadastro de Serviços");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Painel de formulário
        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBorder(BorderFactory.createTitledBorder("Novo Serviço"));
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
        painelForm.add(new JLabel("Tipo:"), gbc);
        tipoCombo = new JComboBox<>(new String[]{"banho", "tosa", "vacina", "consulta"});
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelForm.add(tipoCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        painelForm.add(new JLabel("Preço:"), gbc);
        precoField = new JTextField(15);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelForm.add(precoField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        painelForm.add(new JLabel("Descrição:"), gbc);
        descricaoArea = new JTextArea(3, 20);
        descricaoArea.setLineWrap(true);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        painelForm.add(new JScrollPane(descricaoArea), gbc);

        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.addActionListener(e -> adicionar());
        JButton btnRemover = new JButton("Remover Selecionado");
        btnRemover.addActionListener(e -> remover());
        JPanel painelBotoesForm = new JPanel();
        painelBotoesForm.add(btnAdicionar);
        painelBotoesForm.add(btnRemover);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        painelForm.add(painelBotoesForm, gbc);

        // Tabela
        String[] colunas = {"ID", "Nome", "Tipo", "Preço", "Descrição"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(model);
        carregarServicos(model);

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

    private void carregarServicos(DefaultTableModel model) {
        model.setRowCount(0);
        for (Servico s : db.getServicos()) {
            model.addRow(new Object[]{
                s.getId(),
                s.getNome(),
                s.getTipo(),
                String.format("R$ %.2f", s.getPreco()),
                s.getDescricao()
            });
        }
    }

    private void adicionar() {
        String nome = nomeField.getText();
        String tipo = (String) tipoCombo.getSelectedItem();
        String precoStr = precoField.getText();
        String descricao = descricaoArea.getText();

        if (nome.isEmpty() || precoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha nome e preço!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double preco = Double.parseDouble(precoStr);
            Servico novoServico = new Servico(db.getNextServicoId(), nome, tipo, preco, descricao);
            db.getServicos().add(novoServico);

            limparCampos();
            carregarServicos((DefaultTableModel) tabela.getModel());
            JOptionPane.showMessageDialog(this, "Serviço cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void remover() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um serviço!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = (Integer) tabela.getValueAt(linha, 0);
        db.getServicos().removeIf(s -> s.getId() == id);
        carregarServicos((DefaultTableModel) tabela.getModel());
        JOptionPane.showMessageDialog(this, "Serviço removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void limparCampos() {
        nomeField.setText("");
        precoField.setText("");
        descricaoArea.setText("");
    }
}

