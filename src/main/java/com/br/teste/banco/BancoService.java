package com.br.teste.banco;

public class BancoService {

    public void transferir(ContaBancaria contaOrigem, ContaBancaria contaDestino, double valor) {
        if (contaOrigem == null || contaDestino == null) {
            throw new IllegalArgumentException("Contas não podem ser nulas.");
        }
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de transferência deve ser positivo.");
        }
        ContaBancaria primeira = contaOrigem.getId() < contaDestino.getId() ? contaOrigem : contaDestino;
        ContaBancaria segunda = contaOrigem.getId() < contaDestino.getId() ? contaDestino : contaOrigem;

        // Evita deadlock ao garantir que sempre bloqueamos as contas na mesma ordem
        synchronized (primeira) {
            synchronized (segunda) {
                if (contaOrigem.getSaldo() >= valor) {
                    contaOrigem.sacar(valor);
                    contaDestino.depositar(valor);
                } else {
                    throw new IllegalArgumentException("Saldo insuficiente na conta de origem.");
                }
            }
        }
    }
}
