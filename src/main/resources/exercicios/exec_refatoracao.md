### 2 - Refatoração de código
Foi refatorado a classe Order e dividida em várias classes usando os pricipios de SOLID para melhorar a legibilidade e a manutenção do código. As classes `ProductItem`, `OrderItem` e `OrderService` foram criadas para encapsular a lógica relacionada aos itens do pedido e ao serviço de pedidos.

Order.java agora so armazena itens e o calculo e feito por OrderService.

É possivel criar novos tipos de OrderItem.

ProductItem substitui o antigo OrderItem sem quebrar o codigo.

Iterface simples de OrderItem foi criada.

Order depende da abstração de OrderItem, não da implementação.
