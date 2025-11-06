package com.petcare.view;

import com.petcare.model.Usuario;
import javax.swing.*;
import java.awt.*;

public class AdminMenuFrame extends JFrame {
    private Usuario usuario;

    public AdminMenuFrame(Usuario usuario) {
        this.usuario = usuario;
        configurarTela();
    }

    private void configurarTela() {
        setTitle("PetCare Connect - Administrador");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(4, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnDashboard = new JButton("Dashboard ADM");
        btnDashboard.addActionListener(e -> new DashboardAdminFrame(usuario).setVisible(true));

        JButton btnCadastroVet = new JButton("Cadastro de Veterinários");
        btnCadastroVet.addActionListener(e -> new CadastroVeterinariosFrame(usuario).setVisible(true));

        JButton btnCadastroServicos = new JButton("Cadastro de Serviços");
        btnCadastroServicos.addActionListener(e -> new CadastroServicosFrame(usuario).setVisible(true));

        JButton btnAgendaGlobal = new JButton("Agenda Global");
        btnAgendaGlobal.addActionListener(e -> new AgendaGlobalFrame(usuario).setVisible(true));

        JButton btnPetsClientes = new JButton("Pets e Clientes");
        btnPetsClientes.addActionListener(e -> new PetsClientesFrame(usuario).setVisible(true));

        JButton btnAtividadesVets = new JButton("Atividades dos Veterinários");
        btnAtividadesVets.addActionListener(e -> new AtividadesVeterinariosFrame(usuario).setVisible(true));

        JButton btnRelatorios = new JButton("Relatórios e Cobranças");
        btnRelatorios.addActionListener(e -> new RelatoriosCobrancasFrame(usuario).setVisible(true));

        JButton btnConfiguracoes = new JButton("Configurações Gerais");
        btnConfiguracoes.addActionListener(e -> new ConfiguracoesGeraisFrame(usuario).setVisible(true));

        JButton btnSair = new JButton("Sair");
        btnSair.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        painel.add(btnDashboard);
        painel.add(btnCadastroVet);
        painel.add(btnCadastroServicos);
        painel.add(btnAgendaGlobal);
        painel.add(btnPetsClientes);
        painel.add(btnAtividadesVets);
        painel.add(btnRelatorios);
        painel.add(btnConfiguracoes);
        painel.add(btnSair);

        add(painel);
    }
}

