package com.petcare.view;

import com.petcare.model.Usuario;
import javax.swing.*;
import java.awt.*;

public class ClienteMenuFrame extends JFrame {
    private Usuario usuario;

    public ClienteMenuFrame(Usuario usuario) {
        this.usuario = usuario;
        configurarTela();
    }

    private void configurarTela() {
        setTitle("PetCare Connect - Cliente");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(4, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnAgendar = new JButton("Agendar Serviço");
        btnAgendar.addActionListener(e -> new AgendarServicoFrame(usuario).setVisible(true));

        JButton btnAgenda = new JButton("Minha Agenda");
        btnAgenda.addActionListener(e -> new AgendaClienteFrame(usuario).setVisible(true));

        JButton btnEditarAgendamento = new JButton("Editar Agendamento");
        btnEditarAgendamento.addActionListener(e -> new EditarAgendamentoFrame(usuario).setVisible(true));

        JButton btnHistoricoPet = new JButton("Histórico do Pet");
        btnHistoricoPet.addActionListener(e -> new HistoricoPetFrame(usuario).setVisible(true));

        JButton btnCobrancas = new JButton("Cobranças e Pagamentos");
        btnCobrancas.addActionListener(e -> new CobrancasPagamentosFrame(usuario).setVisible(true));

        JButton btnPerfil = new JButton("Meu Perfil");
        btnPerfil.addActionListener(e -> new PerfilClienteFrame(usuario).setVisible(true));

        JButton btnSuporte = new JButton("Suporte / Ajuda");
        btnSuporte.addActionListener(e -> new SuporteFrame().setVisible(true));

        JButton btnSair = new JButton("Sair");
        btnSair.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        painel.add(btnAgendar);
        painel.add(btnAgenda);
        painel.add(btnEditarAgendamento);
        painel.add(btnHistoricoPet);
        painel.add(btnCobrancas);
        painel.add(btnPerfil);
        painel.add(btnSuporte);
        painel.add(btnSair);

        add(painel);
    }
}

