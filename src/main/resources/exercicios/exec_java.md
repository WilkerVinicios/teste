### 4 - Framworks e Ferramentas
1 - Hibernate e um framework de ORM que facilita na abstração de codigo SQL, otimiza a performance evitando queries repetidas, tem relacionamentos prontos usando @OneToMany, @ManyToOne. E ainda carrega objetos sob demanda.

Exemplo [`User.java`](src/main/java/com/br/teste/spring/entity/User.java)

2 - Testes unitarios trazem a garantia de detectar erros na logica de negocio e que o codigo vai funcionar conforme o esperado, confirma quer as mudanças feitas funcionam e explica por si só como o codigo deve se comportar.

Exemplo [`CacheLRUTest.java`](src/test/java/com/br/teste/spring/service/CacheLRUTest.java)

3 - O maven e mais verboso que o gradle, mas tem uma curva de aprendizado mais baixa, o que facilita a adoção por novos desenvolvedores. O gradle e mais flexivel e poderoso, mas pode ser mais complexo para iniciantes. O maven hoje e utilizado em projetos mais antigos, enquanto o gradle e mais comum em projetos novos.
Usar o maven ou gradle depende do contexto do projeto, principalmente buscando mais padronização ou projetos com muita rotatividade de desenvolvedores usaria o Maven, mas caso seja um projeto com muitos modulos e precisar de build rapido usaria o Gradle.

### 5 - Resolução de Problemas
1 - Debugar o codigo e um processo de investigar, identificar para corrigir um problema.
Tentataria reproduzir o erro em especifico, seguindo o fluxo passo a passo com breakpoints na aplicação pela IDE, se esse passo não e possivel colocaria logs com o log4j na aplicação para verificar onde exatamente esta estourando o erro.
Se e um problema na api usaria postman para fazer as requisições durante a investigação,

2 - Primeiro verificaria onde esta ocorrendo a lentidão, verificaria as telas e os endpoints relacionados, se esta ocorrendo com intermitencia, assim começaria a entender de onde vem.
Acompanharia as respostas do banco em queries, fazendo as chamadas pelo postman.
Seguiria todo o fluxo da requisição ou requisições, verificando se sao queries lentas, threads travadas ou alguma loop ou recursividade infinita.

Mudaria o uso de SELECT * para SELECT colunas especificas dependendo da querie.

Diminuiria o uso de Streams com filter() pra diminuir o uso de memoria.

Verificaria o uso de cache, se esta sendo usado corretamente, se esta sendo atualizado corretamente.

Verificaria campos não utilizados de Dtos e usaria JsonIgnore para nao serializar campos desnecessarios.
