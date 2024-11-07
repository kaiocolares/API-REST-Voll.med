# API-REST Voll.med

Uma API RESTful para o gerenciamento de cadastros de médicos e pacientes de uma clínica, com autenticação de usuários para acesso. Este projeto foi desenvolvido como um aplicativo de aprendizagem, com foco em práticas e tecnologias modernas de desenvolvimento de APIs.

## Índice

- [Descrição](#descrição)
- [Tecnologias](#tecnologias)
- [Funcionalidades](#funcionalidades)
- [Requisitos](#requisitos)
- [Instalação](#instalação)
- [Uso](#uso)
- [Autenticação](#autenticação)
- [Contribuição](#contribuição)
- [Licença](#licença)

---

## Descrição

A API Voll.med permite o cadastro, listagem, atualização e exclusão de dados de médicos e pacientes. A aplicação é voltada para aprendizado em desenvolvimento de APIs seguras e escaláveis, com uma camada de autenticação para restringir o acesso.

## Tecnologias

- **Java** — linguagem de programação principal
- **Spring Boot** — framework para simplificar o desenvolvimento de aplicações Java
- **Spring Security** — autenticação e autorização
- **JPA (Java Persistence API)** — persistência de dados
- **H2 Database** — banco de dados em memória para desenvolvimento e testes
- **Maven** — gerenciador de dependências
- **Swagger** — documentação interativa da API

## Funcionalidades

- Cadastro e listagem de médicos
- Cadastro e listagem de pacientes
- Atualização e exclusão de registros
- Autenticação de usuário para controle de acesso

## Requisitos

- **Java 11** ou superior
- **Maven** instalado

## Instalação

1. Clone o repositório:
    ```bash
    git clone https://github.com/kaiocolares/API-REST-Voll.med.git
    ```

2. Acesse o diretório do projeto:
    ```bash
    cd API-REST-Voll.med
    ```

3. Instale as dependências:
    ```bash
    mvn install
    ```

4. Inicie a aplicação:
    ```bash
    mvn spring-boot:run
    ```

## Uso

Uma vez que a aplicação esteja em execução, você pode acessar a documentação interativa da API com Swagger, geralmente acessível em `http://localhost:8080/swagger-ui.html`.

## Autenticação

Para acessar as funcionalidades de cadastro e listagem, é necessário estar autenticado. Registre-se e faça login na API para obter um token de autenticação.

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e enviar pull requests.

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).
