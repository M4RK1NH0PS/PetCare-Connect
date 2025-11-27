package com.petcare.view;

import com.petcare.data.Database;
import com.petcare.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class AtividadesVeterinariosFrame extends JFrame {
    private Database db;
    private JTable tabela;

    public AtividadesVeterinariosFrame(Usuario admin) {
        this.db = Database.getInstance();
        configurarTela();
    }

    private void configurarTela() {
        setTitle("Atividades dos Veterinários");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Status dos Veterinários - Tempo Real");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTopo.add(titulo);

        String[] colunas = {"Veterinário", "Status Atual", "Pet em Atendimento", "Serviço", "Data/Hora"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(model);
        carregarAtividades(model);

        JScrollPane scrollPane = new JScrollPane(tabela);
        add(painelTopo, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(e -> carregarAtividades(model));
        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnFechar);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void carregarAtividades(DefaultTableModel model) {
        model.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Usuario u : db.getUsuarios()) {
            if (u instanceof Veterinario) {
                Veterinario vet = (Veterinario) u;
                Agendamento atendimentoAtual = db.getAgendamentos().stream()
                    .filter(a -> a.getVeterinarioId() == vet.getId() && 
                                (a.getStatus().equals("em_atendimento") || a.getStatus().equals("agendado")))
                    .findFirst().orElse(null);

                String status = atendimentoAtual != null ? 
                    (atendimentoAtual.getStatus().equals("em_atendimento") ? "Em Atendimento" : "Aguardando") : 
                    "Livre";
                String petNome = "N/A";
                String servicoNome = "N/A";
                String dataHora = "-";

                if (atendimentoAtual != null) {
                    Pet pet = db.getPets().stream()
                        .filter(p -> p.getId() == atendimentoAtual.getPetId())
                        .findFirst().orElse(null);
                    Servico servico = db.getServicos().stream()
                        .filter(s -> s.getId() == atendimentoAtual.getServicoId())
                        .findFirst().orElse(null);
                    petNome = pet != null ? pet.getNome() : "N/A";
                    servicoNome = servico != null ? servico.getNome() : "N/A";
                    dataHora = atendimentoAtual.getDataHora().format(formatter);
                }

                model.addRow(new Object[]{
                    vet.getNome(),
                    status,
                    petNome,
                    servicoNome,
                    dataHora
                });
            }
        }
    }
}

