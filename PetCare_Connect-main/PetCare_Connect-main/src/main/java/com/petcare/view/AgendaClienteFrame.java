package com.petcare.view;

import com.petcare.data.Database;
import com.petcare.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class AgendaClienteFrame extends JFrame {
    private Database db;
    private Usuario cliente;
    private JTable tabela;

    public AgendaClienteFrame(Usuario cliente) {
        this.cliente = cliente;
        this.db = Database.getInstance();
        configurarTela();
    }

    private void configurarTela() {
        setTitle("Minha Agenda");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] colunas = {"ID", "Pet", "Serviço", "Data/Hora", "Status"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(model);
        carregarAgendamentos(model);

        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnFechar);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void carregarAgendamentos(DefaultTableModel model) {
        model.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Agendamento ag : db.getAgendamentosByCliente(cliente.getId())) {
            Pet pet = db.getPets().stream().filter(p -> p.getId() == ag.getPetId()).findFirst().orElse(null);
            Servico servico = db.getServicos().stream().filter(s -> s.getId() == ag.getServicoId()).findFirst().orElse(null);
            
            String petNome = pet != null ? pet.getNome() : "N/A";
            String servicoNome = servico != null ? servico.getNome() : "N/A";
            String dataHora = ag.getDataHora().format(formatter);

            model.addRow(new Object[]{ag.getId(), petNome, servicoNome, dataHora, ag.getStatus()});
        }
    }
}

