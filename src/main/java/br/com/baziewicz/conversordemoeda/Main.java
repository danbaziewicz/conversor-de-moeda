package br.com.baziewicz.conversordemoeda;

import br.com.baziewicz.conversordemoeda.controller.ConversaoController;

public class Main {
    public static void main(String[] args) {
        try {
            new ConversaoController().iniciar();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }
}