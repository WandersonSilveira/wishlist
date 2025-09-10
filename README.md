# Wishlist API

## Descrição
API RESTful desenvolvida em Java com Spring Boot para gerenciamento de wishlists de clientes, utilizando MongoDB como banco de dados. Permite adicionar, remover, consultar e verificar produtos na wishlist de cada cliente.

## Tecnologias Utilizadas
- Java 21
- Spring Boot
- Spring Data MongoDB
- Maven
- MongoDB
- JUnit 5 / Mockito (testes)

## Como executar o projeto
1. **Pré-requisitos:**
   - Java 21 instalado
   - MongoDB em execução (local ou remoto)
   - Maven instalado (ou use o Maven Wrapper incluso)

2. **Configuração do banco de dados:**
   - Edite o arquivo `src/main/resources/application.properties` e configure a URI do MongoDB:
     ```
     spring.data.mongodb.uri=mongodb://localhost:27017/wishlist
     ```

3. **Executando a aplicação:**
   - No terminal, execute:
     ```
     ./mvnw spring-boot:run
     ```
   - Ou, se estiver no Windows:
     ```
     mvnw.cmd spring-boot:run
     ```

## Endpoints principais

- **Adicionar produto à wishlist:**
  - `POST /wishlist/{clienteId}/produtos/{produtoId}`
- **Remover produto da wishlist:**
  - `DELETE /wishlist/{clienteId}/produtos/{produtoId}`
- **Consultar produtos da wishlist:**
  - `GET /wishlist/{clienteId}/produtos`
- **Verificar se produto está na wishlist:**
  - `GET /wishlist/{clienteId}/produtos/{produtoId}/existe`

## Testes
- Para rodar os testes automatizados:
  ```
  ./mvnw test
  ```

## Autor
- Desenvolvido por Wanderson da Silveira.

