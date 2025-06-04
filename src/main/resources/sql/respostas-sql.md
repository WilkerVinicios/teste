1.1 - Usaria por exemplo no postgresql o EXPLAIN ANALYSE para entender o plano de execução das queries, identificar gargalos de performance e tambem habilitar logs das queries mais lentas.
Criaria um indice de posts(user_id), um indice likes(post_id) , um indice composto de (user_id, created_at) em posts para as postagem recentes, um created_at(posts e likes) para filtros por data.

1.2 - Foi necessario criar uma tabela likes para armazenar as curtidas dos posts, onde cada like é associado a um post e a um usuário. A tabela de posts tem uma chave estrangeira para o usuário que criou o post.

No exemplo abaixo, a tabela de likes armazena o id do post curtido e o id do usuário que curtiu, além de um timestamp para quando a curtida foi feita.
```sql
CREATE TABLE likes (
    id SERIAL PRIMARY KEY,
    post_id INT NOT NULL,
    user_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

Total de curtidas por usuario e nome do usuario:
```sql
SELECT us.username, COUNT(lk.id) AS total_likes 
FROM users us 
JOIN posts pt ON us.id = pt.user_id 
LEFT JOIN likes lk ON pt.id = lk.post_id 
GROUP BY us.username
ORDER BY total_likes DESC;
```
5 usuarios com mais postagens nos ultimos 30 dias:
```sql
SELECT us.username, COUNT(pt.id) AS total_posts 
FROM users us 
JOIN posts pt ON us.id = pt.user_id 
WHERE pt.created_at >= NOW() - INTERVAL '30 days' 
GROUP BY us.username 
ORDER BY total_posts DESC 
LIMIT 5;
```
1.3 - Manteria chaves estrangeiras bem definidas, contants e triggers para manter a integridade referencial e consistencia;
Dividir em particionamento por datas para melhor performance e arquivar dados antigos;
No particionamento iria dividir o volume por um periodo de tempo, por exemplo, 6 meses, e manteria os dados mais recentes em uma tabela principal e os dados antigos em tabelas particionadas.

1.4 - Durante o trabalho peguei uma consulta de viabilidade de nomes empresariais e otimizei a consulta usando o EXISTS para verificar a existencia de nomes em vez de manter a consulta total onde retornava a listagem de nomes e verificava na aplicação nome a nome dessa listagem, o que melhorou significativamente a performance da consulta.
exemplo simples aplicado ao posts:
```sql
SELECT pt.* 
FROM posts pt 
WHERE EXISTS (
    SELECT 1 
    FROM likes lk 
    WHERE lk.post_id = pt.id AND lk.user_id = :user_id
);
```
Essa consulta verifica se existem likes para o post do usuário sem precisar retornar todos os dados de likes, melhorando a performance.

