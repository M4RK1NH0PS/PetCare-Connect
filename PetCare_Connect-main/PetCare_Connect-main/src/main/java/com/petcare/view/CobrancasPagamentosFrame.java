package com.petcare.view;

import com.petcare.data.Database;
import com.petcare.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class CobrancasPagamentosFrame extends JFrame {
    private Database db;
    private Usuario cliente;
    private JTable tabela;

    public CobrancasPagamentosFrame(Usuario cliente) {
        this.cliente = cliente;
        this.db = Database.getInstance();
        configurarTela();
    }

    private void configurarTela() {
        setTitle("Cobranças e Pagamentos");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] colunas = {"ID", "Serviço", "Valor", "Data Agendamento", "Status Pagamento"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(model);
        carregarPagamentos(model);

        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnPagar = new JButton("Pagar Selecionado");
        btnPagar.addActionListener(e -> pagarSelecionado());
        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        painelBotoes.add(btnPagar);
        painelBotoes.add(btnFechar);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void carregarPagamentos(DefaultTableModel model) {
        model.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Pagamento pag : db.getPagamentos()) {
            Agendamento ag = db.getAgendamentos().stream()
                .filter(a -> a.getId() == pag.getAgendamentoId() && a.getClienteId() == cliente.getId())
                .findFirst().orElse(null);

            if (ag != null) {
                Servico servico = db.getServicos().stream()
                    .filter(s -> s.getId() == ag.getServicoId())
                    .findFirst().orElse(null);
                String servicoNome = servico != null ? servico.getNome() : "N/A";
                String dataHora = ag.getDataHora().format(formatter);

                model.addRow(new Object[]{
                    pag.getId(),
                    servicoNome,
                    String.format("R$ %.2f", pag.getValor()),
                    dataHora,
                    pag.getStatus()
                });
            }
        }
    }

    private void pagarSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um pagamento!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int pagamentoId = (Integer) tabela.getValueAt(linha, 0);
        Pagamento pag = db.getPagamentos().stream()
            .filter(p -> p.getId() == pagamentoId)
            .findFirst().orElse(null);

        if (pag != null && pag.getStatus().equals("pendente")) {
            pag.setStatus("pago");
            pag.setDataPagamento(java.time.LocalDateTime.now());
            JOptionPane.showMessageDialog(this, "Pagamento realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarPagamentos((DefaultTableModel) tabela.getModel());
        } else {
            JOptionPane.showMessageDialog(this, "Este pagamento já foi realizado!", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
}

