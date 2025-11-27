package com.petcare.view;

import com.petcare.data.Database;
import com.petcare.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class AtribuirAtendimentoFrame extends JFrame {
    private Database db;
    private Usuario veterinario;
    private JTable tabela;

    public AtribuirAtendimentoFrame(Usuario veterinario) {
        this.veterinario = veterinario;
        this.db = Database.getInstance();
        configurarTela();
    }

    private void configurarTela() {
        setTitle("Atribuir Atendimento");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Agendamentos Disponíveis");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTopo.add(titulo);

        String[] colunas = {"ID", "Pet", "Cliente", "Serviço", "Data/Hora", "Status"};
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
        JButton btnAtribuir = new JButton("Atribuir Selecionado");
        btnAtribuir.addActionListener(e -> atribuir());
        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        painelBotoes.add(btnAtribuir);
        painelBotoes.add(btnFechar);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void carregarAgendamentos(DefaultTableModel model) {
        model.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Agendamento ag : db.getAgendamentosDoDia()) {
            if (ag.getStatus().equals("agendado")) {
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

    private void atribuir() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um agendamento!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int agendamentoId = (Integer) tabela.getValueAt(linha, 0);
        Agendamento ag = db.getAgendamentos().stream()
            .filter(a -> a.getId() == agendamentoId)
            .findFirst().orElse(null);

        if (ag != null) {
            ag.setVeterinarioId(veterinario.getId());
            ag.setStatus("em_atendimento");
            JOptionPane.showMessageDialog(this, "Atendimento atribuído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarAgendamentos((DefaultTableModel) tabela.getModel());
        }
    }
}

