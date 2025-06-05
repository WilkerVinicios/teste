package com.br.teste.banco;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class TransferenciaRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        BancoService bancoService = new BancoService();

        ContaBancaria conta1 = new ContaBancaria(1, 1000.0);
        ContaBancaria conta2 = new ContaBancaria(2, 1000.0);
        System.out.println("Saldo inicial da conta 1: " + conta1.getSaldo());
        System.out.println("Saldo inicial da conta 2: " + conta2.getSaldo());

        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executor.submit(() -> {
                try {
                    bancoService.transferir(conta1, conta2, 10.0);
                } catch (IllegalArgumentException e) {
                    System.err.println("Erro na transferência: " + e.getMessage());
                }
            });
            executor.submit(() -> {
                try {
                    bancoService.transferir(conta2, conta1, 5.0);
                } catch (IllegalArgumentException e) {
                    System.err.println("Erro na transferência: " + e.getMessage());
                }
            });
            executor.shutdown();
            while (!executor.isTerminated()) {
                Thread.sleep(100);
            }
            System.out.println("Saldo final da conta 1: " + conta1.getSaldo());
            System.out.println("Saldo final da conta 2: " + conta2.getSaldo());
        }
    }
}
