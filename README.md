# CPF Control Application

Este projeto é uma aplicação backend desenvolvida com **Spring Boot** para gerenciar e validar CPFs. Ele permite adicionar, consultar e excluir CPFs de um banco de dados, com validações customizadas de CPF e respostas estruturadas em formato JSON.

## Tecnologias Utilizadas

- **Spring Boot**: Framework para desenvolvimento de aplicações Java.
- **Java 11+**: Linguagem de programação usada no desenvolvimento da aplicação.
- **MySQL 8.0.33**: Sistema de gerenciamento de banco de dados relacional, utilizado para armazenar as informações dos usuários e CPFs.
- **JPA (Java Persistence API)**: Para interação com o banco de dados de forma simples e eficaz. Utilizado através da interface `Repositorio`.
- **Lombok**: Biblioteca que simplifica a criação de métodos comuns como getters, setters e construtores, tornando o código mais limpo e reduzindo o boilerplate.

## Arquitetura da Aplicação

A aplicação segue uma arquitetura baseada em **MVC (Model-View-Controller)** adaptada para um padrão **RESTful**. As requisições HTTP são tratadas pelos controladores, que interagem com a camada de serviço e persistência.

### Estilo Arquitetural

- **Restful API**: A aplicação é construída com base em uma API RESTful. As operações de manipulação de dados (criação, leitura, atualização, exclusão) são mapeadas para os métodos HTTP padrão:
  - `GET` para leitura.
  - `POST` para criação.
  - `DELETE` para exclusão.

## Endpoints

- **POST /cpf**: Adiciona um novo CPF.
- **GET /cpf/{cpf}**: Recupera informações de um CPF específico.
- **DELETE /cpf/{cpf}**: Exclui um CPF específico.
- **GET /cpf**: Recupera todos os CPFs cadastrados.

