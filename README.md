# 💱 Conversor de Moeda

Bem-vindo ao projeto **Conversor de Moeda**, um aplicativo de linha de comando em Java que permite converter valores entre diferentes moedas e visualizar um panorama completo de taxas de câmbio com base em uma moeda escolhida.

---

## 📑 Sumário

- [📌 Sobre o Projeto](#-sobre-o-projeto)
- [🚀 Funcionalidades](#-funcionalidades)
- [🛠️ Pré-requisitos](#️-pré-requisitos)
- [⚙️ Como Executar](#️-como-executar)
- [🧪 Exemplos de Uso](#-exemplos-de-uso)
- [📦 Organização do Código](#-organização-do-código)
- [🔐 Chave da API](#-chave-da-api)
- [📃 Licença](#-licença)

---

## 📌 Sobre o Projeto

Este projeto foi desenvolvido em Java puro, com foco no uso de APIs REST para buscar dados em tempo real de conversão de moedas através da [ExchangeRate API](https://www.exchangerate-api.com/). 
Ele roda em terminal e fornece duas funcionalidades principais:

1. Converter entre pares de moedas pré-definidas.
2. Exibir um panorama das taxas de câmbio de uma moeda base para outras moedas populares.

---

## 🚀 Funcionalidades

- 🌍 Conversão de moedas: USD, BRL, EUR, ARS, COP, GBP, entre outras.
- 📊 Panorama de câmbio: veja como uma moeda base se comporta frente a várias outras.
- 🔒 Validação de entradas do usuário.
- 💬 Mensagens informativas e retorno claro dos resultados.

---

## 🛠️ Pré-requisitos

- Java **versão 17** ou superior.
- [ExchangeRate API Key](https://www.exchangerate-api.com/).
- Terminal / CMD ou IDE (como IntelliJ, Eclipse, etc).

---

## ⚙️ Como Executar

1. **Clone o repositório:**

```bash
git clone https://github.com/seu-usuario/conversor-de-moeda.git
```

2. **Compile os arquivos Java:**

```bash
cd conversor-de-moeda/src
javac br/com/baziewicz/conversordemoeda/**/*.java
```

3. **Execute o programa:**

```bash
java br.com.baziewicz.conversordemoeda.controller.ConversaoController
```

---

## 🧪 Exemplos de Uso

### 🎯 Menu Principal:

```
1) Converter entre duas moedas
2) Ver panorama da moeda base
3) Sair
```

### 💱 Exemplo de Conversão:

```
USD =>> BRL
Digite o valor que deseja converter: 100
Valor convertido: 519.47300 BRL
```

### 🌐 Exemplo de Panorama:

```
Escolha a moeda base:
1) BRL
2) USD
...
Panorama para 1 USD:
BRL: 5.19473
EUR: 0.91547
...
```

---

## 📦 Organização do Código

```
src/
conversor-de-moeda/
├── src/
│   └── main/
│       └── java/
│           └── br/
│               └── com/
│                   └── baziewicz/
│                       └── conversordemoeda/
│                           ├── controller/
│                           │   └── ConversaoController.java
│                           ├── servico/
│                           │   └── ExchangeRateService.java
│                           ├── util/
│                           │   └── ApiKeyLoader.java
│                           │
│                           └── Main.java
├── .gitignore
└── README.md
```

---

## 🔐 Chave da API

A chave da API é carregada através da classe `ApiKeyLoader`, que lê um arquivo `.env` ou `.properties`. Certifique-se de incluir sua chave da ExchangeRate API.
Você pode fazer a requisição da chave através do link abaixo:
```bash
https://www.exchangerate-api.com/
```
---

## 📃 Licença

Este projeto está licenciado sob a **MIT License**. Sinta-se à vontade para usar e modificar com os devidos créditos.

---
