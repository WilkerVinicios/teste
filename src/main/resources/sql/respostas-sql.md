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

2.1 - 
```sql
CREATE OR REPLACE PROCEDURE realizar_transacao(
    p_account_id INT,
    p_amount DECIMAL(15,2)
    )
LANGUAGE plpgsql AS $$
DECLARE
    v_old_balance DECIMAL(15,2);
    v_new_balance DECIMAL(15,2);
BEGIN
     BEGIN
        SELECT balance INTO v_old_balance FROM accounts WHERE id = p_account_id FOR UPDATE;
        v_new_balance := v_old_balance + p_amount;
        UPDATE accounts SET balance = v_new_balance WHERE id = p_account_id;
        
        INSERT INTO transactions (account_id, amount, transaction_date) VALUES (p_account_id, p_amount, CURRENT_TIMESTAMP);
        INSERT INTO audit_log (account_id, old_balance, new_balance, change_date) VALUES (p_account_id, v_old_balance, v_new_balance, CURRENT_TIMESTAMP);
        COMMIT;
    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            RAISE NOTICE 'Erro ao realizar transação: %', SQLERRM;
    END;
END;
$$;
```
2.2 - 
```sql
CREATE OR REPLACE FUNCTION auditar_transacao() 
RETURNS TRIGGER AS $$
BEGIN 
    INSERT INTO audit_log (account_id, old_balance, new_balance, change_date) VALUES (NEW.account_id, OLD.balance, NEW.balance, CURRENT_TIMESTAMP);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_adit_transaction 
AFTER UPDATE ON accounts 
FOR EACH ROW 
WHEN (OLD.balance IS DISTINCT FROM NEW.balance) 
EXECUTE FUNCTION auditar_transacao();
```

2.3 - O uso de transactions como COMMIT e ROLLBACK garante a consistencia de dados no banco pois se alguma parte da transação falhar ou ou ocorrer um erro, todas as altertações feitas na transação são revertidas.

2.4 - Com triggers e possivel automatizar registros de auditoria e validações, reduzir codigo das aplicações pois as regras de negocio ficam no banco, e garantir integridade de dados sem depender da aplicação.

2.5 - PROCEDURE - Realiza todas as operações de uma vez em uma transação, consulta o saldo antigo, atualiza o saldo, insere a transação e registra no log de auditoria. Se algo falhar, reverte tudo com ROLLBACK. 

TRIGGER - Dispara sempre que houver uma atualização na tabela de contas sempre que o old.balance for diferente do new.balance, registrando automaticamente no log de auditoria sem precisar chamar manualmente.

2.6 - Uma das formas de evitar vulnerabilidades seria aplicar roles e permissões adequadas a cada função, evitar EXECUTE dinamicos, manter o uso de procedures parametrizadas para evitar SQL Injection.

2.7 - Criei uma procedure que recebia o valor de um agendamento de horario de ausencia temporaria de um funcionario, atualizava o status de saida para AUSENTE, e verificava se o horario atual estava dentro de um periodo aceitavel comparado ao agendado, quando o funcionario retornava, atualizava o status do agendamento, o usuario tinha acesso a marcar o horario de retorno.
Quando era atualizado o status do agendamento para RETORNOU pelo usuario, era disparado um trigger que registrava o CURRENT_TIMESTAMP na tabela de auditoria, e garantia que ele so conseguia mudar o status de AUSENTE para RETORNOU.

3.1 - 1FN - Não existe coluna multivalorada, cada coluna tem um unico valor.

2FN - Todas as colunas não chave dependem totalmente da chave primária, ou seja, não existem dependências parciais.

3FN - Nenhuma coluna depende de outra coluna que não seja chave primária, ou seja, não existem dependências transitivas.

3.2 - Uma das situações que usaria desnormalização  para melhorar a performance seria em algum dashboard onde é necessário realizar consultas complexas e frequentes, tambem para redução de joins em queries críticas.

3.3 - 
```sql 
SELECT 
    or.id AS order_id,
    ct.name AS customer_name,
    ct.email AS customer_email,
    pr.name AS product_name,
    oi.quantity,
    oi.price, 
    or.order_date
FROM orders or
JOIN customers ct ON or.customer_id = ct.id 
JOIN order_items oi ON or.id = oi.order_id 
JOIN products pr ON oi.product_id = pr.id;
```

3.4 - Analisaria a consulta com o explain, evitaria usar SELECT *, verificaria o uso de indices nas FKs, e se necessario criaria views para simplificar consultas complexas.

3.5 - Backups diarios completos e incrementais de hora em hora, testaria a integridade dos backups periodicamente, e manteria logs de transações para recuperação em caso de falhas.

3.6 - No caso do postgresql, usaria o pg_dump para backups completos e pg_basebackup para backups incrementais, tambem usaria o WAL (Write Ahead Log) para garantir a integridade dos dados e permitir a recuperação em caso de falhas.

3.7 - Não fiz uma mudança assim em um banco de e-commerce porem durante a implementação de um sistema de gerenciamento de banco de dados interno, fiz uma mudança em um ponto critico onde tinhamos a seguinte estrutura ( sgbd, user, solicitacao, sistema ) e alterei para ( user, solicitacao ) pois a tabela sgbd e a tabela sistema possuiam valores fixos e não necessitavam de uma tabela separada, o que simplificou a estrutura do banco e melhorou a performance das consultas.
Foi criado dois campos adicionais na tabela de solicitacao para armazenar os dados do sgbd e do sistema, eliminando a necessidade de joins desnecessários.
A mudança eliminou erros que ocorriam devido a nomes duplicados e por os valores serem tratados no JPA e como constantes diminuimos o risco de SQL Injection.

PROCEDURE
```sql
CREATE OR REPLACE PROCEDURE resgistrar_ausencia(p_ausencia_id INT)
LANGUAGE plpgsql AS $$
DECLARE
    v_horario_agendado TIMESTAMP;
    v_status_atual VARCHAR(20);
BEGIN
    SELECT horario_saida, status INTO v_horario_agendado, v_status_atual 
    FROM ausencias 
    WHERE id = p_ausencia_id FOR UPDATE;
    
    IF v_status_atual != 'AGENDADO' THEN
        RAISE EXCEPTION 'Ausência não agendada ou já iniciada.';
    END IF;
    IF NOW() < v_horario_agendado - INTERVAL '10 minutes' OR 
       NOW() > v_horario_agendado + INTERVAL '10 minutes' THEN 
        RAISE EXCEPTION 'Horário de ausência fora do intervalo permitido.';
    END IF;
    UPDATE ausencias
    SET status = 'AUSENTE' 
    WHERE id = p_ausencia_id;
    
    COMMIT;
END;
$$;
```

TRIGGER
```sql
CREATE OR REPLACE FUNCTION registrar_retorno() 
RETURNS TRIGGER AS $$
BEGIN
    IF OLD.status = 'AUSENTE' AND NEW.status = 'RETORNOU' THEN 
        NEW.horario_retorno := CURRENT_TIMESTAMP;
    ELSIF OLD.status = 'RETORNOU' AND NEW.status = 'RETORNOU' THEN 
        RAISE EXCEPTION 'Retorno já registrado.';
    ELSIF OLD.status != 'AUSENTE' AND NEW.status = 'RETORNOU' THEN
        RAISE EXCEPTION 'Não e possivel retornar sem ter ficado ausente.';
    END IF;
    
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_validar_retorno 
BEFORE UPDATE ON ausencias 
FOR EACH ROW 
WHEN (OLD.status IS DISTINCT FROM NEW.status AND NEW.status = 'RETORNOU')
EXECUTE FUNCTION registrar_retorno();
```

A solução com procedure e trigger trabalhando em conjunto garantiu um processo mais seguro mesmo com varios acessos simultaneos, tambem atuando como uma segurança adicional complementando a aplicação.