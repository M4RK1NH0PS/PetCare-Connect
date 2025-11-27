package com.petcare.view;

import com.petcare.data.Database;
import com.petcare.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class HistoricoPetFrame extends JFrame {
    private Database db;
    private Usuario cliente;
    private JComboBox<Pet> petCombo;
    private JTable tabela;

    public HistoricoPetFrame(Usuario cliente) {
        this.cliente = cliente;
        this.db = Database.getInstance();
        configurarTela();
    }

    private void configurarTela() {
        setTitle("Histórico do Pet");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTopo.add(new JLabel("Selecione o Pet:"));
        petCombo = new JComboBox<>();
        carregarPets();
        petCombo.addActionListener(e -> carregarHistorico());
        painelTopo.add(petCombo);

        String[] colunas = {"Data/Hora", "Serviço", "Observações", "Status"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(tabela);
        add(painelTopo, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnFechar);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void carregarPets() {
        petCombo.removeAllItems();
        for (Pet pet : db.getPetsByCliente(cliente.getId())) {
            petCombo.addItem(pet);
        }
    }

    private void carregarHistorico() {
        Pet pet = (Pet) petCombo.getSelectedItem();
        if (pet == null) return;

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        model.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Agendamento ag : db.getAgendamentosByCliente(cliente.getId())) {
            if (ag.getPetId() == pet.getId()) {
                Servico servico = db.getServicos().stream()
                    .filter(s -> s.getId() == ag.getServicoId())
                    .findFirst().orElse(null);
                String servicoNome = servico != null ? servico.getNome() : "N/A";
                String dataHora = ag.getDataHora().format(formatter);
                String obs = ag.getObservacoes() != null ? ag.getObservacoes() : "-";

                model.addRow(new Object[]{dataHora, servicoNome, obs, ag.getStatus()});
            }
        }
    }
}

