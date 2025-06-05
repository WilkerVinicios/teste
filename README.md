## Exercícios de Java
### CacheLRU
Este repositório contém um exercício de Java que implementa um cache LRU (Least Recently Used) utilizando uma estrutura de dados baseada em `LinkedHashMap`.

Classe - [`CacheLRU.java`](src/main/java/com/br/teste/cache/CacheLRU.java).

Teste - [`CacheLRUTest.java`](src/test/java/com/br/teste/cache/CacheLRUTest.java).

### Manipulação de Arquivos
Este exercício demonstra como manipular arquivos em Java, incluindo leitura, remoção de linhas em branco e escrita de arquivos de texto.

Classe Service -[`FileProcessor.java`](src/main/java/com/br/teste/file/FileProcessor.java).
Classe Execução -[`FileRunner.java`](src/main/java/com/br/teste/file/FileRunner.java).
Teste - [`FileProcessorTest.java`](src/test/java/com/br/teste/file/FileProcessorTest.java).

### Multithreading
Este exercício demonstra o uso de multithreading em Java, incluindo a criação de threads, sincronização e manipulação de concorrência.

Classe - [`ContaBancaria.java`](src/main/java/com/br/teste/banco/ContaBancaria.java).

Classe Service - [`BancoService.java`](src/main/java/com/br/teste/banco/BancoService.java).

Classe Execução - [`TransferenciaRunner.java`](src/main/java/com/br/teste/banco/TransferenciaRunner.java).

Teste - [`BancoServiceTest.java`](src/test/java/com/br/teste/banco/BancoServiceTest.java).

### Spring Framework
Projeto Spring Simples que exponha uma API RESTfull com as seguintes funcionalidades:
```
Post: Criação de um usuário.
Get: Listagem de todos os usuários.
Get por ID: Listagem de um usuário específico.
Put: Atualização de um usuário.
Delete: Exclusão de um usuário.
```

Controller - [`UserController.java`](src/main/java/com/br/teste/spring/controller/UserController.java).

Entity - [`User.java`](src/main/java/com/br/teste/spring/entity/User.java).

DTO - [`UserDTO.java`](src/main/java/com/br/teste/spring/dto/UserDTO.java).

Service - [`UserService.java`](src/main/java/com/br/teste/spring/service/UserService.java).

Repository - [`UserRepository.java`](src/main/java/com/br/teste/spring/repository/UserRepository.java).

Teste - [`UserServiceTest.java`](src/test/java/com/br/teste/spring/service/UserServiceTest.java).

Exception - [`UserNotFoundException.java`](src/main/java/com/br/teste/spring/exception/UserNotFoundException.java).

## Analise e Design
### Projeto de Sistema
1 - Consulte o arquivo [`biblioteca.png`](src/main/resources/diagrama/biblioteca.png) ou [`biblioteca.jpg`](src/main/resources/diagrama/biblioteca.jpg)  para ver o diagrama de classes do sistema de biblioteca.
    Consulte o arquivo [`biblioteca.md`](src/main/resources/diagrama/biblioteca.md) para detalhes sobre o diagrama de classes.

### Refatoração de Código

[`Order.java`](src/main/java/com/br/teste/order/Order.java)
[`ProductItem.java`](src/main/java/com/br/teste/order/ProductItem.java)
[`OrderItem.java`](src/main/java/com/br/teste/order/OrderItem.java)
[`OrderService.java`](src/main/java/com/br/teste/order/OrderService.java)

Texto de referencia a refatoração de código da classe Order.java usando os principios SOLID - [`exec_refatoracao.md`](src/main/resources/exercicios/exec_refatoracao.md)

## Respostas Conhecimento de Frameworks e Ferramentas
Consulte o arquivo [`src/main/resources/exercicios/exec_java.md`](src/main/resources/exercicios/exec_java.md) para detalhes e exemplos completos.

## Respostas Resolução de Problemas
Consulte o arquivo [`src/main/resources/exercicios/exec_java.md`](src/main/resources/exercicios/exec_java.md) para detalhes.

## Respostas SQL
Consulte o arquivo [`src/main/resources/sql/respostas-sql.md`](src/main/resources/sql/respostas-sql.md) para detalhes e exemplos completos.