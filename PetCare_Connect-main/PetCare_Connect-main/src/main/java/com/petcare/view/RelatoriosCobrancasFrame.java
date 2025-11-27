package com.petcare.view;

import com.petcare.data.Database;
import com.petcare.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class RelatoriosCobrancasFrame extends JFrame {
    private Database db;
    private JTable tabela;
    private JComboBox<String> filtroStatus;

    public RelatoriosCobrancasFrame(Usuario admin) {
        this.db = Database.getInstance();
        configurarTela();
    }

    private void configurarTela() {
        setTitle("Relatórios e Cobranças Gerais");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelFiltros.add(new JLabel("Filtrar por Status:"));
        filtroStatus = new JComboBox<>(new String[]{"Todos", "pago", "pendente", "cancelado"});
        filtroStatus.addActionListener(e -> carregarPagamentos());
        painelFiltros.add(filtroStatus);

        String[] colunas = {"ID", "Cliente", "Pet", "Serviço", "Valor", "Data Agendamento", "Status Pagamento", "Data Pagamento"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(model);
        carregarPagamentos();

        JScrollPane scrollPane = new JScrollPane(tabela);

        // Resumo
        JPanel painelResumo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        double totalPago = db.getPagamentos().stream()
            .filter(p -> p.getStatus().equals("pago"))
            .mapToDouble(Pagamento::getValor)
            .sum();
        double totalPendente = db.getPagamentos().stream()
            .filter(p -> p.getStatus().equals("pendente"))
            .mapToDouble(Pagamento::getValor)
            .sum();
        painelResumo.add(new JLabel("Total Pago: R$ " + String.format("%.2f", totalPago)));
        painelResumo.add(new JLabel("Total Pendente: R$ " + String.format("%.2f", totalPendente)));

        add(painelFiltros, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(painelResumo, BorderLayout.SOUTH);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        painelResumo.add(btnFechar);
    }

    private void carregarPagamentos() {
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        model.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String filtro = (String) filtroStatus.getSelectedItem();

        for (Pagamento pag : db.getPagamentos()) {
            if (!filtro.equals("Todos") && !pag.getStatus().equals(filtro)) {
                continue;
            }

            Agendamento ag = db.getAgendamentos().stream()
                .filter(a -> a.getId() == pag.getAgendamentoId())
                .findFirst().orElse(null);

            if (ag != null) {
                Usuario cliente = db.getUsuarios().stream()
                    .filter(u -> u.getId() == ag.getClienteId())
                    .findFirst().orElse(null);
                Pet pet = db.getPets().stream()
                    .filter(p -> p.getId() == ag.getPetId())
                    .findFirst().orElse(null);
                Servico servico = db.getServicos().stream()
                    .filter(s -> s.getId() == ag.getServicoId())
                    .findFirst().orElse(null);

                String clienteNome = cliente != null ? cliente.getNome() : "N/A";
                String petNome = pet != null ? pet.getNome() : "N/A";
                String servicoNome = servico != null ? servico.getNome() : "N/A";
                String dataHora = ag.getDataHora().format(formatter);
                String dataPagamento = pag.getDataPagamento() != null ? 
                    pag.getDataPagamento().format(formatter) : "-";

                model.addRow(new Object[]{
                    pag.getId(),
                    clienteNome,
                    petNome,
                    servicoNome,
                    String.format("R$ %.2f", pag.getValor()),
                    dataHora,
                    pag.getStatus(),
                    dataPagamento
                });
            }
        }
    }
}

