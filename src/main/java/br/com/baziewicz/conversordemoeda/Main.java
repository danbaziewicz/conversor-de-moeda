package br.com.baziewicz.conversordemoeda;

import br.com.baziewicz.conversordemoeda.servico.ExchangeRateService;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        ExchangeRateService service = new ExchangeRateService();

        int opcao = 0;

        while (opcao != 7) {
            System.out.println("**************************************");
            System.out.println("$$$ Seja bem-vindo/a ao Conversor de Moeda $$$");
            System.out.println();
            System.out.println("1) Dólar =>> Real brasileiro");
            System.out.println("2) Real brasileiro =>> Dólar");
            System.out.println("3) Dólar =>> Peso argentino");
            System.out.println("4) Peso argentino =>> Dólar");
            System.out.println("5) Dólar =>> Peso colombiano");
            System.out.println("6) Peso colombiano =>> Dólar");
            System.out.println("7) Sair");
            System.out.print("Escolha uma opção válida: \n");
            System.out.println("**************************************");

            opcao = scanner.nextInt();

            if (opcao >= 1 && opcao <= 6) {
                System.out.print("Digite o valor que deseja converter: ");
                double valor = scanner.nextDouble();

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
                double convertido = valor * taxa;

                System.out.printf("Valor convertido: %.2f %s%n", convertido, moedaDestino);
            } else if (opcao == 7) {
                System.out.println("Encerrando o programa. Obrigado por usar o conversor!");
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }

            System.out.println();
        }
    }
}