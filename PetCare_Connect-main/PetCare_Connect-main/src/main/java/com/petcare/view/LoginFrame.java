package com.petcare.view;

import com.petcare.data.Database;
import com.petcare.model.Usuario;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField senhaField;
    private Database db;

    public LoginFrame() {
        db = Database.getInstance();
        configurarTela();
    }

    private void configurarTela() {
        setTitle("PetCare Connect - Login");
        setSize(400, 450); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Painel central
        JPanel painelCentral = new JPanel(new GridBagLayout());
        painelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Configuração padrão de espaçamento
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fazer componentes esticarem
        gbc.weightx = 1.0; // Permitir que estiquem

        // --- Título (Logo) ---
        try {
            ImageIcon logoIcon = new ImageIcon("resources/logo.png");
            // Mantemos o tamanho de 150x150, que é bom
            Image image = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(image));
            
            gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
            gbc.insets = new Insets(5, 5, 20, 5); 
            painelCentral.add(logoLabel, gbc);

        } catch (Exception e) {
            // ... (código alternativo caso a imagem falhe) ...
            JLabel titulo = new JLabel("PetCare Connect");
            titulo.setFont(new Font("Arial", Font.BOLD, 24));
            gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
            painelCentral.add(titulo, gbc);
            System.err.println("Erro ao carregar a logo: " + e.getMessage());
        }

        // Resetar insets para o padrão
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // --- Email ---
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST; 
        gbc.fill = GridBagConstraints.NONE; 
        gbc.weightx = 0; 
        painelCentral.add(new JLabel("Email:"), gbc);
        
        emailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 1.0;
        painelCentral.add(emailField, gbc);

        // --- Senha ---
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST; 
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelCentral.add(new JLabel("Senha:"), gbc);
        
        senhaField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painelCentral.add(senhaField, gbc);

        // --- Botão Login ---
        JButton btnLogin = new JButton("Entrar");
        btnLogin.addActionListener(e -> fazerLogin());
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 5, 5, 5); 
        painelCentral.add(btnLogin, gbc);
        add(painelCentral, BorderLayout.CENTER);
        JLabel dica = new JLabel("<html><center>Dica: " + 
                                "admin@petcare.com / admin (ADM)<br>" +
                                "maria@email.com / 123 (Cliente)<br>" +
                                "joao@petcare.com / 123 (Veterinário)</center></html>");
        dica.setFont(new Font("Arial", Font.PLAIN, 10));
        dica.setHorizontalAlignment(SwingConstants.CENTER); 
        dica.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); 
        
        add(dica, BorderLayout.SOUTH);
    }

    private void fazerLogin() {
        String email = emailField.getText();
        String senha = new String(senhaField.getPassword());

        Usuario usuario = db.login(email, senha);
        if (usuario != null) {
            dispose();
            abrirTelaPorTipo(usuario);
        } else {
            JOptionPane.showMessageDialog(this, "Email ou senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirTelaPorTipo(Usuario usuario) {
        switch (usuario.getTipo()) {
            case CLIENTE:
                new ClienteMenuFrame(usuario).setVisible(true);
                break;
            case VETERINARIO:
                new VeterinarioMenuFrame(usuario).setVisible(true);
                break;
            case ADMIN:
                new AdminMenuFrame(usuario).setVisible(true);
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}

