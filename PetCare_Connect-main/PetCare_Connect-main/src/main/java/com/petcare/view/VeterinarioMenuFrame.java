package com.petcare.view;

import com.petcare.model.Usuario;
import javax.swing.*;
import java.awt.*;

public class VeterinarioMenuFrame extends JFrame {
    private Usuario usuario;

    public VeterinarioMenuFrame(Usuario usuario) {
        this.usuario = usuario;
        configurarTela();
    }

    private void configurarTela() {
        setTitle("PetCare Connect - Veterinário");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(4, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnDashboard = new JButton("Dashboard / Fila de Atendimentos");
        btnDashboard.addActionListener(e -> new DashboardVeterinarioFrame(usuario).setVisible(true));

        JButton btnFichaPet = new JButton("Ficha do Pet");
        btnFichaPet.addActionListener(e -> new FichaPetFrame(usuario).setVisible(true));

        JButton btnRegistrarServico = new JButton("Registrar Serviço Realizado");
        btnRegistrarServico.addActionListener(e -> new RegistrarServicoFrame(usuario).setVisible(true));

        JButton btnAgenda = new JButton("Minha Agenda");
        btnAgenda.addActionListener(e -> new AgendaVeterinarioFrame(usuario).setVisible(true));

        JButton btnAtribuirAtendimento = new JButton("Atribuir Atendimento");
        btnAtribuirAtendimento.addActionListener(e -> new AtribuirAtendimentoFrame(usuario).setVisible(true));

        JButton btnHistorico = new JButton("Meu Histórico de Atendimentos");
        btnHistorico.addActionListener(e -> new HistoricoVeterinarioFrame(usuario).setVisible(true));

        JButton btnCobrancas = new JButton("Cobranças e Serviços");
        btnCobrancas.addActionListener(e -> new CobrancasVeterinarioFrame(usuario).setVisible(true));

        JButton btnPerfil = new JButton("Meu Perfil");
        btnPerfil.addActionListener(e -> new PerfilVeterinarioFrame(usuario).setVisible(true));

        JButton btnSair = new JButton("Sair");
        btnSair.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        painel.add(btnDashboard);
        painel.add(btnFichaPet);
        painel.add(btnRegistrarServico);
        painel.add(btnAgenda);
        painel.add(btnAtribuirAtendimento);
        painel.add(btnHistorico);
        painel.add(btnCobrancas);
        painel.add(btnPerfil);
        painel.add(btnSair);

        add(painel);
    }
}

