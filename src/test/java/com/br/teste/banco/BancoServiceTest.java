package com.br.teste.banco;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BancoServiceTest {

    @Test
    void testTransferenciaConcorrente() throws InterruptedException {
        BancoService bancoService = new BancoService();
        ContaBancaria conta1 = new ContaBancaria(1, 1000.0);
        ContaBancaria conta2 = new ContaBancaria(2, 1000.0);

        Thread t1 = new Thread(() -> {
            try {
                for (int i = 0; i < 100; i++) {
                    bancoService.transferir(conta1, conta2, 10.0);
                }
            } catch (IllegalArgumentException e) {
                fail("Erro na transferência: " + e.getMessage());
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                for (int i = 0; i < 100; i++) {
                    bancoService.transferir(conta2, conta1, 5.0);
                }
            } catch (IllegalArgumentException e) {
                fail("Erro na transferência: " + e.getMessage());
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        assertEquals(500.0, conta1.getSaldo(), 0.01);
        assertEquals(1500.0, conta2.getSaldo(), 0.01);
    }
}