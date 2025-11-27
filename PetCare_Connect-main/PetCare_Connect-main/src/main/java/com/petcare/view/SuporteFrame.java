package com.petcare.view;

import javax.swing.*;
import java.awt.*;

public class SuporteFrame extends JFrame {
    public SuporteFrame() {
        configurarTela();
    }

    private void configurarTela() {
        setTitle("Suporte / Ajuda");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("PetCare Connect - Suporte");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        painel.add(titulo);
        painel.add(Box.createVerticalStrut(20));

        painel.add(new JLabel("Entre em contato conosco:"));
        painel.add(Box.createVerticalStrut(10));
        painel.add(new JLabel("Telefone: (11) 3333-4444"));
        painel.add(new JLabel("Email: suporte@petcare.com"));
        painel.add(Box.createVerticalStrut(20));

        painel.add(new JLabel("Horário de Atendimento:"));
        painel.add(new JLabel("Segunda a Sexta: 8h às 18h"));
        painel.add(new JLabel("Sábado: 8h às 13h"));
        painel.add(Box.createVerticalStrut(20));

        painel.add(new JLabel("Dúvidas Frequentes:"));
        painel.add(Box.createVerticalStrut(10));
        painel.add(new JLabel("• Como agendar um serviço?"));
        painel.add(new JLabel("  Acesse 'Agendar Serviço' no menu principal."));
        painel.add(Box.createVerticalStrut(5));
        painel.add(new JLabel("• Como cancelar um agendamento?"));
        painel.add(new JLabel("  Acesse 'Minha Agenda' e edite o agendamento."));
        painel.add(Box.createVerticalStrut(5));
        painel.add(new JLabel("• Como visualizar o histórico do meu pet?"));
        painel.add(new JLabel("  Acesse 'Histórico do Pet' no menu principal."));

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnFechar);

        add(painel, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }
}

