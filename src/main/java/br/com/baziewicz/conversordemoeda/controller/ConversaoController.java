package br.com.baziewicz.conversordemoeda.controller;

import br.com.baziewicz.conversordemoeda.servico.ExchangeRateService;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class ConversaoController {
    private final ExchangeRateService service;
    private final Scanner scanner;

    public ConversaoController() {
        this.service = new ExchangeRateService();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() throws IOException, InterruptedException {
        int opcaoPrincipal = 0;

        while (opcaoPrincipal != 3) {
            exibirMenuPrincipal();
            try {
                if (scanner.hasNextInt()) {
                    opcaoPrincipal = scanner.nextInt();
                    scanner.nextLine();

                    switch (opcaoPrincipal) {
                        case 1 -> menuConversao();
                        case 2 -> mostrarPanorama();
                        case 3 -> System.out.println("Encerrando o programa. Obrigado por usar o conversor!");
                        default -> System.out.println("Opção inválida. Tente novamente.");
                    }
                } else {
                    System.out.println("Entrada inválida. Por favor, digite um número.");
                    scanner.next();
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro de entrada. Por favor, digite um número inteiro.");
                scanner.next();
            }
            System.out.println();
        }
        scanner.close();
    }

    private void exibirMenuPrincipal() {
        System.out.println("**************************************");
        System.out.println("Seja bem-vindo/a ao Conversor de Moeda =]");
        System.out.println("1) Converter entre duas moedas");
        System.out.println("2) Ver panorama da moeda base");
        System.out.println("3) Sair");
        System.out.println("Escolha uma opção válida: ");
        System.out.println("**************************************");
    }

    private void menuConversao() throws IOException, InterruptedException {
        int opcao = 0;

        while (opcao != 7) {
            exibirMenuConversao();
            try {
                if (scanner.hasNextInt()) {
                    opcao = scanner.nextInt();

                    if (opcao >= 1 && opcao <= 6) {
                        realizarConversao(opcao);
                    } else if (opcao == 7) {
                        System.out.println("Retornando ao menu principal.");
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
    }

    private void exibirMenuConversao() {
        System.out.println("**************************************");
        System.out.println("Conversão entre moedas:");
        System.out.println("1) Dólar =>> Real brasileiro");
        System.out.println("2) Real brasileiro =>> Dólar");
        System.out.println("3) Dólar =>> Peso argentino");
        System.out.println("4) Peso argentino =>> Dólar");
        System.out.println("5) Dólar =>> Peso colombiano");
        System.out.println("6) Peso colombiano =>> Dólar");
        System.out.println("7) Voltar");
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
                System.out.printf("Valor convertido: %.5f %s%n", convertido, moedaDestino);
            } else {
                System.out.println("Não foi possível obter a taxa de câmbio.");
            }

        } catch (InputMismatchException e) {
            System.out.println("Valor inválido. Por favor, digite um número.");
            scanner.next();
        }
    }

    private void mostrarPanorama() throws IOException, InterruptedException {
        String[] moedas = {"BRL", "USD", "EUR", "ARS", "COP"};

        System.out.println("Escolha a moeda base:");
        for (int i = 0; i < moedas.length; i++) {
            System.out.printf("%d) %s%n", i + 1, moedas[i]);
        }
        System.out.print("Digite o número correspondente à moeda base: ");

        int escolha = -1;
        try {
            escolha = scanner.nextInt();
            scanner.nextLine();
            if (escolha < 1 || escolha > moedas.length) {
                System.out.println("Opção inválida. Retornando ao menu principal.");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Retornando ao menu principal.");
            scanner.next();
            return;
        }

        String moedaBase = moedas[escolha - 1];
        Map<String, Double> taxas = service.buscarTaxasDeCambio(moedaBase);

        System.out.printf("\nPanorama para 1 %s:%n", moedaBase);
        for (String destino : moedas) {
            if (!destino.equalsIgnoreCase(moedaBase)) {
                Double taxa = taxas.get(destino);
                if (taxa != null) {
                    System.out.printf("%s: %.5f%n", destino, taxa);
                } else {
                    System.out.printf("%s: Taxa indisponível%n", destino);
                }
            }
        }
    }
}
