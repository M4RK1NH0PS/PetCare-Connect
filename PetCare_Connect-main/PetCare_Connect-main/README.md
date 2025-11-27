# PetCare Connect

Sistema de gestão para clínica veterinária/petshop desenvolvido em Java com Swing.

## Funcionalidades

### Cliente
- Agendar Serviço
- Minha Agenda
- Editar Agendamento
- Histórico do Pet
- Cobranças e Pagamentos
- Meu Perfil
- Suporte / Ajuda

### Veterinário
- Dashboard / Fila de Atendimentos
- Ficha do Pet
- Registrar Serviço Realizado
- Minha Agenda
- Atribuir Atendimento
- Meu Histórico de Atendimentos
- Cobranças e Serviços
- Meu Perfil

### Administrador
- Dashboard ADM
- Cadastro de Veterinários / Funcionários
- Cadastro de Serviços
- Agenda Global
- Pets e Clientes
- Atividades dos Veterinários
- Relatórios e Cobranças Gerais
- Configurações Gerais

## Como Executar

### Windows

1. Faça o download do JDK
```bash 
https://download.oracle.com/java/25/latest/jdk-25_windows-x64_bin.exe
```

2. Compile o projeto:
```bash
Compilador.bat
```

3. Execute a aplicação:
```bash
Executavel_App.bat
```

## Credenciais de Acesso

### Administrador
- Email: `admin@petcare.com`
- Senha: `admin`

### Cliente
- Email: `maria@email.com`
- Senha: `123`

### Veterinário
- Email: `joao@petcare.com`
- Senha: `123`

## Estrutura do Projeto

```
src/main/java/com/petcare/
├── model/          # Classes de modelo (Usuario, Pet, Servico, etc.)
├── data/           # Database (simulação de banco de dados)
└── view/           # Telas Swing
```

## Tecnologias

- Java (Swing para interface gráfica)
- Java 8+ (usa LocalDateTime)

## Observações

- O sistema usa uma simulação de banco de dados em memória (classe Database)
- Os dados são perdidos ao fechar a aplicação
- Para produção, seria necessário integrar com um banco de dados real
