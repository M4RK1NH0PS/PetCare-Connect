package com.petcare.view;

import com.petcare.data.Database;
import com.petcare.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class FichaPetFrame extends JFrame {
    private Database db;
    private JComboBox<Pet> petCombo;
    private JTextArea infoArea;
    private JTable tabelaHistorico;

    public FichaPetFrame(Usuario veterinario) {
        this.db = Database.getInstance();
        configurarTela();
    }

    private void configurarTela() {
        setTitle("Ficha do Pet");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTopo.add(new JLabel("Selecione o Pet:"));
        petCombo = new JComboBox<>();
        for (Pet pet : db.getPets()) {
            petCombo.addItem(pet);
        }
        petCombo.addActionListener(e -> carregarFicha());
        painelTopo.add(petCombo);

        infoArea = new JTextArea(5, 30);
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        String[] colunas = {"Data/Hora", "Serviço", "Observações"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaHistorico = new JTable(model);

        JPanel painelInfo = new JPanel(new BorderLayout());
        painelInfo.setBorder(BorderFactory.createTitledBorder("Informações do Pet e Tutor"));
        painelInfo.add(new JScrollPane(infoArea), BorderLayout.CENTER);

        JPanel painelHistorico = new JPanel(new BorderLayout());
        painelHistorico.setBorder(BorderFactory.createTitledBorder("Histórico de Atendimentos"));
        painelHistorico.add(new JScrollPane(tabelaHistorico), BorderLayout.CENTER);

        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.add(painelInfo, BorderLayout.NORTH);
        painelCentral.add(painelHistorico, BorderLayout.CENTER);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnFechar);

        add(painelTopo, BorderLayout.NORTH);
        add(painelCentral, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        if (petCombo.getItemCount() > 0) {
            carregarFicha();
        }
    }

    private void carregarFicha() {
        Pet pet = (Pet) petCombo.getSelectedItem();
        if (pet == null) return;

        Usuario cliente = db.getUsuarios().stream()
            .filter(u -> u.getId() == pet.getClienteId())
            .findFirst().orElse(null);

        StringBuilder info = new StringBuilder();
        info.append("PET:\n");
        info.append("Nome: ").append(pet.getNome()).append("\n");
        info.append("Espécie: ").append(pet.getEspecie()).append("\n");
        info.append("Raça: ").append(pet.getRaca()).append("\n");
        info.append("Idade: ").append(pet.getIdade()).append(" anos\n");
        info.append("\nTUTOR:\n");
        if (cliente != null) {
            info.append("Nome: ").append(cliente.getNome()).append("\n");
            info.append("Email: ").append(cliente.getEmail()).append("\n");
            if (cliente instanceof Cliente) {
                info.append("Telefone: ").append(((Cliente) cliente).getTelefone()).append("\n");
            }
        }
        infoArea.setText(info.toString());

        DefaultTableModel model = (DefaultTableModel) tabelaHistorico.getModel();
        model.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Agendamento ag : db.getAgendamentos()) {
            if (ag.getPetId() == pet.getId() && ag.getStatus().equals("finalizado")) {
                Servico servico = db.getServicos().stream()
                    .filter(s -> s.getId() == ag.getServicoId())
                    .findFirst().orElse(null);
                String servicoNome = servico != null ? servico.getNome() : "N/A";
                String dataHora = ag.getDataHora().format(formatter);
                String obs = ag.getObservacoes() != null ? ag.getObservacoes() : "-";

                model.addRow(new Object[]{dataHora, servicoNome, obs});
            }
        }
    }
}

