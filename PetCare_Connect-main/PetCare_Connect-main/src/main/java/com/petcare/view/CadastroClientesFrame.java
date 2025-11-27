package com.petcare.view;

import com.petcare.data.Database;
import com.petcare.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CadastroClientesFrame extends JFrame {
    private Database db;
    private JTable tabela;
    private JTextField nomeField;
    private JTextField emailField;
    private JTextField senhaField;
    private JTextField telefoneField;
    private JTextField enderecoField;
    private JTextField nomeAnimalField;
    private JComboBox<String> tipoAnimalCombo;
    private JSpinner idadeAnimalSpinner;

    public CadastroClientesFrame(Usuario admin) {
        this.db = Database.getInstance();
        configurarTela();
    }

    private void configurarTela() {
        setTitle("Cadastro de Clientes");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Painel principal com dois painéis lado a lado
        JPanel painelPrincipalForm = new JPanel(new GridLayout(1, 2, 10, 10));
        
        // Painel de formulário do cliente
        JPanel painelFormCliente = new JPanel(new GridBagLayout());
        painelFormCliente.setBorder(BorderFactory.createTitledBorder("Novo Cliente"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        painelFormCliente.add(new JLabel("Nome:"), gbc);
        nomeField = new JTextField(15);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelFormCliente.add(nomeField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        painelFormCliente.add(new JLabel("Email:"), gbc);
        emailField = new JTextField(15);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelFormCliente.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        painelFormCliente.add(new JLabel("Senha:"), gbc);
        senhaField = new JTextField(15);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelFormCliente.add(senhaField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        painelFormCliente.add(new JLabel("Telefone:"), gbc);
        telefoneField = new JTextField(15);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelFormCliente.add(telefoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        painelFormCliente.add(new JLabel("Endereço:"), gbc);
        enderecoField = new JTextField(15);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelFormCliente.add(enderecoField, gbc);

        // Painel de formulário do animal
        JPanel painelFormAnimal = new JPanel(new GridBagLayout());
        painelFormAnimal.setBorder(BorderFactory.createTitledBorder("Animal do Cliente"));
        GridBagConstraints gbcAnimal = new GridBagConstraints();
        gbcAnimal.insets = new Insets(5, 5, 5, 5);
        gbcAnimal.anchor = GridBagConstraints.WEST;

        gbcAnimal.gridx = 0; gbcAnimal.gridy = 0;
        painelFormAnimal.add(new JLabel("Nome do Animal:"), gbcAnimal);
        nomeAnimalField = new JTextField(15);
        gbcAnimal.gridx = 1;
        gbcAnimal.fill = GridBagConstraints.HORIZONTAL;
        painelFormAnimal.add(nomeAnimalField, gbcAnimal);

        gbcAnimal.gridx = 0; gbcAnimal.gridy = 1;
        gbcAnimal.fill = GridBagConstraints.NONE;
        painelFormAnimal.add(new JLabel("Tipo:"), gbcAnimal);
        tipoAnimalCombo = new JComboBox<>(new String[]{"Cachorro", "Gato"});
        gbcAnimal.gridx = 1;
        gbcAnimal.fill = GridBagConstraints.HORIZONTAL;
        painelFormAnimal.add(tipoAnimalCombo, gbcAnimal);

        gbcAnimal.gridx = 0; gbcAnimal.gridy = 2;
        gbcAnimal.fill = GridBagConstraints.NONE;
        painelFormAnimal.add(new JLabel("Idade:"), gbcAnimal);
        SpinnerNumberModel idadeModel = new SpinnerNumberModel(0, 0, 30, 1);
        idadeAnimalSpinner = new JSpinner(idadeModel);
        gbcAnimal.gridx = 1;
        gbcAnimal.fill = GridBagConstraints.HORIZONTAL;
        painelFormAnimal.add(idadeAnimalSpinner, gbcAnimal);

        painelPrincipalForm.add(painelFormCliente);
        painelPrincipalForm.add(painelFormAnimal);

        JButton btnAdicionar = new JButton("Adicionar Cliente e Animal");
        btnAdicionar.addActionListener(e -> adicionar());
        JButton btnRemover = new JButton("Remover Selecionado");
        btnRemover.addActionListener(e -> remover());
        JPanel painelBotoesForm = new JPanel();
        painelBotoesForm.add(btnAdicionar);
        painelBotoesForm.add(btnRemover);

        // Tabela
        String[] colunas = {"ID", "Nome", "Email", "Telefone", "Endereço"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(model);
        carregarClientes(model);

        JScrollPane scrollPane = new JScrollPane(tabela);

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        JPanel painelFormCompleto = new JPanel(new BorderLayout());
        painelFormCompleto.add(painelPrincipalForm, BorderLayout.CENTER);
        painelFormCompleto.add(painelBotoesForm, BorderLayout.SOUTH);
        painelPrincipal.add(painelFormCompleto, BorderLayout.NORTH);
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnFechar);

        add(painelPrincipal, BorderLayout.CENTER);
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

    private void adicionar() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = senhaField.getText();
        String telefone = telefoneField.getText();
        String endereco = enderecoField.getText();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || telefone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios do cliente!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nomeAnimal = nomeAnimalField.getText();
        String tipoAnimal = (String) tipoAnimalCombo.getSelectedItem();
        int idadeAnimal = (Integer) idadeAnimalSpinner.getValue();

        if (nomeAnimal.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o nome do animal!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int nextId = db.getUsuarios().stream().mapToInt(Usuario::getId).max().orElse(0) + 1;
        Cliente novoCliente = new Cliente(nextId, nome, email, senha, telefone, endereco);
        db.getUsuarios().add(novoCliente);

        // Cadastrar o animal
        int nextPetId = db.getPets().stream().mapToInt(Pet::getId).max().orElse(0) + 1;
        Pet novoPet = new Pet(nextPetId, nomeAnimal, tipoAnimal, "", idadeAnimal, nextId);
        db.getPets().add(novoPet);

        limparCampos();
        carregarClientes((DefaultTableModel) tabela.getModel());
        JOptionPane.showMessageDialog(this, "Cliente e animal cadastrados com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void remover() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = (Integer) tabela.getValueAt(linha, 0);
        db.getUsuarios().removeIf(u -> u.getId() == id);
        carregarClientes((DefaultTableModel) tabela.getModel());
        JOptionPane.showMessageDialog(this, "Cliente removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void limparCampos() {
        nomeField.setText("");
        emailField.setText("");
        senhaField.setText("");
        telefoneField.setText("");
        enderecoField.setText("");
        nomeAnimalField.setText("");
        tipoAnimalCombo.setSelectedIndex(0);
        idadeAnimalSpinner.setValue(0);
    }
}

