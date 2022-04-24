## Tools API :hammer::wrench:

## Descrição do Projeto
API para gerenciar ferramentas.

## Instalação
Após baixar e abrir o projeto na sua IDE de preferencia é necessário  inicialmente baixar as dependências do maven:
```bash
mvnw clean install
```
Para iniciar a API temos duas opção via Terminal e via Docker, basta somente configurar as variáveis de ambiente para cada ambiente que desejar iniciar.

Terminal:
```bash
.\rundev.ps1
```
Docker:
```bash
.\rundocker.ps1
```
## Funcionalidades

### Tag
- `Cadastrar` :  Cadastra uma nova Tag
- `Listar`: Lista todas as tags
- `Remover`:  Remove uma tag pelo nome

### Tools
- `Cadastrar` :  Cadastra uma nova Tools
- `Listar`: Lista todas as tools
- `Remover`:  Remove uma tools pelo id

## Tecnologias utilizadas

- ``Java 11``
- ``Spring Boot``
- ``Spring Data Jpa``
- ``Lombok``
- ``Junit5 + Mockito``
- ``OpenAPI + Swagger``
- ``Oracle Autonomous Database``

