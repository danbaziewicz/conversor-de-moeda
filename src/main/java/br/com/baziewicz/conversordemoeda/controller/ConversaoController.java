package br.com.baziewicz.conversordemoeda.controller;

import br.com.baziewicz.conversordemoeda.servico.ExchangeRateService;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConversaoController {
    private final ExchangeRateService service;
    private final Scanner scanner;

    public ConversaoController() {
        this.service = new ExchangeRateService();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() throws IOException, InterruptedException {
        int opcao = 0;

        while (opcao != 7) {
            exibirMenu();
            try {
                if (scanner.hasNextInt()) {
                    opcao = scanner.nextInt();

                    if (opcao >= 1 && opcao <= 6) {
                        realizarConversao(opcao);
                    } else if (opcao == 7) {
                        System.out.println("Encerrando o programa.\n Obrigado por usar o conversor!");
                    } else {
                        System.out.println("Opção inválida. Tente novamente.");
                    }
                } else {
                    System.out.println("Entrada inválida. Por favor, digite um número.");
                    scanner.next();
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro de entrada. Por favor, digite um número inteiro.");
                scanner.next();
                opcao = -1;
            }
            System.out.println();
        }
        scanner.close();
    }

    private void exibirMenu() {
        System.out.println("**************************************");
        System.out.println("Seja bem-vindo/a ao Conversor de Moeda =]");
        System.out.println("1) Dólar =>> Real brasileiro");
        System.out.println("2) Real brasileiro =>> Dólar");
        System.out.println("3) Dólar =>> Peso argentino");
        System.out.println("4) Peso argentino =>> Dólar");
        System.out.println("5) Dólar =>> Peso colombiano");
        System.out.println("6) Peso colombiano =>> Dólar");
        System.out.println("7) Sair");
        System.out.println("Escolha uma opção válida: ");
        System.out.println("**************************************");
    }

    private void realizarConversao(int opcao) throws IOException, InterruptedException {
        System.out.print("Digite o valor que deseja converter: ");
        try {
            double valor = scanner.nextDouble();
            scanner.nextLine();

            String moedaBase = "";
            String moedaDestino = "";

            switch (opcao) {
                case 1 -> { moedaBase = "USD"; moedaDestino = "BRL"; }
                case 2 -> { moedaBase = "BRL"; moedaDestino = "USD"; }
                case 3 -> { moedaBase = "USD"; moedaDestino = "ARS"; }
                case 4 -> { moedaBase = "ARS"; moedaDestino = "USD"; }
                case 5 -> { moedaBase = "USD"; moedaDestino = "COP"; }
                case 6 -> { moedaBase = "COP"; moedaDestino = "USD"; }
            }

            Double taxa = service.obterTaxa(moedaBase, moedaDestino);
            if (taxa != null) {
                double convertido = valor * taxa;
                System.out.printf("Valor convertido: %.2f %s%n", convertido, moedaDestino);
            } else {
                System.out.println("Não foi possível obter a taxa de câmbio.");
            }

        } catch (InputMismatchException e) {
            System.out.println("Valor inválido. Por favor, digite um número.");
            scanner.next();
        }
    }
}