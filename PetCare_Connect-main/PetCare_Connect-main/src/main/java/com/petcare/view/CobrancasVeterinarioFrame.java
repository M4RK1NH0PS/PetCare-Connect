package com.petcare.view;

import com.petcare.data.Database;
import com.petcare.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class CobrancasVeterinarioFrame extends JFrame {
    private Database db;
    private Usuario veterinario;
    private JTable tabela;

    public CobrancasVeterinarioFrame(Usuario veterinario) {
        this.veterinario = veterinario;
        this.db = Database.getInstance();
        configurarTela();
    }

    private void configurarTela() {
        setTitle("Cobranças e Serviços");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] colunas = {"ID", "Pet", "Cliente", "Serviço", "Valor", "Data", "Status Pagamento"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(model);
        carregarServicos(model);

        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnFechar);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void carregarServicos(DefaultTableModel model) {
        model.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Agendamento ag : db.getAgendamentosByVeterinario(veterinario.getId())) {
            if (ag.getStatus().equals("finalizado")) {
                Pet pet = db.getPets().stream().filter(p -> p.getId() == ag.getPetId()).findFirst().orElse(null);
                Usuario cliente = db.getUsuarios().stream().filter(u -> u.getId() == ag.getClienteId()).findFirst().orElse(null);
                Servico servico = db.getServicos().stream().filter(s -> s.getId() == ag.getServicoId()).findFirst().orElse(null);

                Pagamento pag = db.getPagamentos().stream()
                    .filter(p -> p.getAgendamentoId() == ag.getId())
                    .findFirst().orElse(null);

                String petNome = pet != null ? pet.getNome() : "N/A";
                String clienteNome = cliente != null ? cliente.getNome() : "N/A";
                String servicoNome = servico != null ? servico.getNome() : "N/A";
                String dataHora = ag.getDataHora().format(formatter);
                double valor = servico != null ? servico.getPreco() : 0.0;
                String statusPag = pag != null ? pag.getStatus() : "N/A";

                model.addRow(new Object[]{
                    ag.getId(),
                    petNome,
                    clienteNome,
                    servicoNome,
                    String.format("R$ %.2f", valor),
                    dataHora,
                    statusPag
                });
            }
        }
    }
}

