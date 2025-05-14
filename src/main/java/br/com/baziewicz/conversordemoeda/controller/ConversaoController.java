package br.com.baziewicz.conversordemoeda.controller;

import br.com.baziewicz.conversordemoeda.servico.ExchangeRateService;

import java.io.IOException;
import java.util.*;

public class ConversaoController {
    private final ExchangeRateService service;
    private final Scanner scanner;
    private final Map<Integer, String[]> opcoesConversao;

    public ConversaoController() {
        this.service = new ExchangeRateService();
        this.scanner = new Scanner(System.in);
        this.opcoesConversao = new LinkedHashMap<>() {{
            put(1, new String[]{"USD", "BRL"});
            put(2, new String[]{"BRL", "USD"});
            put(3, new String[]{"USD", "ARS"});
            put(4, new String[]{"ARS", "USD"});
            put(5, new String[]{"USD", "COP"});
            put(6, new String[]{"COP", "USD"});
            put(7, new String[]{"USD", "EUR"});
            put(8, new String[]{"EUR", "USD"});
            put(9, new String[]{"USD", "GBP"});
            put(10, new String[]{"GBP", "USD"});
        }};
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

        while (opcao != opcoesConversao.size() + 1) {
            exibirMenuConversao();
            try {
                if (scanner.hasNextInt()) {
                    opcao = scanner.nextInt();
                    scanner.nextLine();

                    if (opcoesConversao.containsKey(opcao)) {
                        realizarConversao(opcao);
                    } else if (opcao == opcoesConversao.size() + 1) {
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
        for (Map.Entry<Integer, String[]> entry : opcoesConversao.entrySet()) {
            int numero = entry.getKey();
            String[] moedas = entry.getValue();
            System.out.printf("%d) %s =>> %s%n", numero, moedas[0], moedas[1]);
        }
        System.out.printf("%d) Voltar%n", opcoesConversao.size() + 1);
        System.out.println("Escolha uma opção válida: ");
        System.out.println("**************************************");
    }

    private void realizarConversao(int opcao) throws IOException, InterruptedException {
        System.out.print("Digite o valor que deseja converter: ");
        try {
            double valor = scanner.nextDouble();
            scanner.nextLine();

            String[] moedas = opcoesConversao.get(opcao);
            String moedaBase = moedas[0];
            String moedaDestino = moedas[1];

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
        String[] moedas = {"BRL", "USD", "EUR", "ARS", "COP", "GBP", "JPY", "CHF", "AUD", "CAD"};

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
