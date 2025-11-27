package com.petcare.view;

import com.petcare.data.Database;
import com.petcare.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class AgendaGlobalFrame extends JFrame {
    private Database db;
    private JTable tabela;

    public AgendaGlobalFrame(Usuario admin) {
        this.db = Database.getInstance();
        configurarTela();
    }

    private void configurarTela() {
        setTitle("Agenda Global");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] colunas = {"ID", "Pet", "Cliente", "Veterinário", "Serviço", "Data/Hora", "Status"};
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

        for (Agendamento ag : db.getAgendamentos()) {
            Pet pet = db.getPets().stream().filter(p -> p.getId() == ag.getPetId()).findFirst().orElse(null);
            Usuario cliente = db.getUsuarios().stream().filter(u -> u.getId() == ag.getClienteId()).findFirst().orElse(null);
            Usuario vet = ag.getVeterinarioId() > 0 ? 
                db.getUsuarios().stream().filter(u -> u.getId() == ag.getVeterinarioId()).findFirst().orElse(null) : null;
            Servico servico = db.getServicos().stream().filter(s -> s.getId() == ag.getServicoId()).findFirst().orElse(null);

            String petNome = pet != null ? pet.getNome() : "N/A";
            String clienteNome = cliente != null ? cliente.getNome() : "N/A";
            String vetNome = vet != null ? vet.getNome() : "Não atribuído";
            String servicoNome = servico != null ? servico.getNome() : "N/A";
            String dataHora = ag.getDataHora().format(formatter);

            model.addRow(new Object[]{
                ag.getId(),
                petNome,
                clienteNome,
                vetNome,
                servicoNome,
                dataHora,
                ag.getStatus()
            });
        }
    }
}

