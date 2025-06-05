package com.br.teste.banco;

public class ContaBancaria {
    private final int id;
    private double saldo;

    public ContaBancaria(int id, double saldoInicial) {
        this.id = id;
        this.saldo = saldoInicial;
    }

    public synchronized void sacar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de saque deve ser positivo.");
        }
        if (valor > saldo) {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }
        saldo -= valor;
    }
    public synchronized void depositar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de depósito deve ser positivo.");
        }
        saldo += valor;
    }

    public synchronized double getSaldo() {
        return saldo;
    }

    public int getId() {
        return id;
    }
}
