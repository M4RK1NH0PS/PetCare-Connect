package com.petcare.view;

import com.petcare.data.Database;
import com.petcare.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class DashboardVeterinarioFrame extends JFrame {
    private Database db;
    private Usuario veterinario;
    private JTable tabela;

    public DashboardVeterinarioFrame(Usuario veterinario) {
        this.veterinario = veterinario;
        this.db = Database.getInstance();
        configurarTela();
    }

    private void configurarTela() {
        setTitle("Dashboard - Fila de Atendimentos");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Pets Agendados para Hoje");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTopo.add(titulo);

        String[] colunas = {"ID", "Pet", "Tutor", "Serviço", "Data/Hora", "Status"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(model);
        carregarAgendamentos(model);

        JScrollPane scrollPane = new JScrollPane(tabela);
        add(painelTopo, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(e -> carregarAgendamentos(model));
        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnFechar);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void carregarAgendamentos(DefaultTableModel model) {
        model.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Agendamento ag : db.getAgendamentosDoDia()) {
            Pet pet = db.getPets().stream().filter(p -> p.getId() == ag.getPetId()).findFirst().orElse(null);
            Usuario cliente = db.getUsuarios().stream().filter(u -> u.getId() == ag.getClienteId()).findFirst().orElse(null);
            Servico servico = db.getServicos().stream().filter(s -> s.getId() == ag.getServicoId()).findFirst().orElse(null);

            String petNome = pet != null ? pet.getNome() : "N/A";
            String clienteNome = cliente != null ? cliente.getNome() : "N/A";
            String servicoNome = servico != null ? servico.getNome() : "N/A";
            String dataHora = ag.getDataHora().format(formatter);

            model.addRow(new Object[]{ag.getId(), petNome, clienteNome, servicoNome, dataHora, ag.getStatus()});
        }
    }
}

