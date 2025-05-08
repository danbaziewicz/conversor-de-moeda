package br.com.baziewicz.conversordemoeda.servico;

import br.com.baziewicz.conversordemoeda.util.ApiKeyLoader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class ExchangeRateService {

    private static final String API_KEY = ApiKeyLoader.loadApiKey();
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    private final HttpClient client;
    private final Gson gson;

    public ExchangeRateService() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public Map<String, Double> buscarTaxasDeCambio(String moedaBase) throws IOException, InterruptedException {
        String url = BASE_URL + API_KEY + "/latest/" + moedaBase.toUpperCase();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);

        if (!"success".equalsIgnoreCase(jsonObject.get("result").getAsString())) {
            throw new IOException("Erro ao buscar taxas: " + jsonObject);
        }

        JsonObject conversionRates = jsonObject.getAsJsonObject("conversion_rates");

        return gson.fromJson(conversionRates, Map.class);
    }

    public Double obterTaxa(String moedaBase, String moedaDestino) throws IOException, InterruptedException {
        Map<String, Double> taxas = buscarTaxasDeCambio(moedaBase);
        return taxas.get(moedaDestino.toUpperCase());
    }
}
