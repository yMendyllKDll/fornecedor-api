Sistema de Gestão de Empresas e Fornecedores

Sistema full-stack para gerenciamento de empresas e seus fornecedores, desenvolvido como parte de um desafio técnico.

## Tecnologias Utilizadas

### Backend
- Java 11
- Spring Boot 2.7.18
- Spring Data JPA
- Postgresql
- Maven
- Lombok

## Funcionalidades

### Empresas
- CRUD completo de empresas
- Validação de CNPJ único
- Validação de CEP via API externa

### Fornecedores
- CRUD completo de fornecedores
- Validação de CPF/CNPJ único
- Validação de CEP via API externa
- Suporte a pessoa física e jurídica
- Validações específicas para fornecedores pessoa física:
  - RG obrigatório
  - Data de nascimento obrigatória
  - Restrição de idade para empresas do Paraná

### Relacionamentos
- Empresas podem ter múltiplos fornecedores
- Fornecedores podem trabalhar para múltiplas empresas

## Requisitos do Sistema

- Java 11
- Postgresql
- Maven

## Configuração do Ambiente

1. Clone o repositório
```bash
git clone https://github.com/seu-usuario/intelitransfer.git
```

2. Configure o banco de dados Postgresql
```sql
CREATE DATABASE fornecedor_api;
CREATE USER admin WITH PASSWORD 'admin';
GRANT ALL PRIVILEGES ON DATABASE fornecedor_api TO admin;
```

3. Configure as credenciais do banco no arquivo `application.properties`

4. Execute o projeto
```bash
mvn spring-boot:run
```

## Próximos Passos

1. Adição de mais testes unitários
2. Implementação de Docker
3. Melhorias de segurança

