package com.email.writer;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Service
public class emailservice {

    private final WebClient webclient;
    private final String apikey;


    private final String model ="gemini-3-flash-preview";

    public emailservice(WebClient.Builder webclientbuilder,
                        @Value("${gemini.api.baseurl}") String baseurl,
                        @Value("${gemini.api.key}") String apikey) {

        this.webclient = webclientbuilder.baseUrl(baseurl).build();
        this.apikey = apikey;
    }

    public Map<String, Object> generateEmail(emailrequest emailRequest) {
        String prompt = buildPrompt(emailRequest);

        String requestBody = String.format(
                "{ \"contents\": [ { \"parts\": [ { \"text\": \"%s\" } ] } ] }",
                prompt
        );

        String response = webclient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1beta/models/{model}:generateContent")
                        .build(model))
                .header("Content-Type", "application/json")
                .header("X-Goog-Api-Key", apikey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return extractresponse(response);
    }

    private Map<String, Object> extractresponse(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            String email = root
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

            // ✅ Clean formatting
            email = email.replace("\\n", "\n").trim();

            int promptTokens = root.path("usageMetadata").path("promptTokenCount").asInt();
            int responseTokens = root.path("usageMetadata").path("candidatesTokenCount").asInt();
            var totalTokens = root.path("usageMetadata").path("totalTokenCount").asInt();

            double cost = calculateCostINR(promptTokens, responseTokens);

            Map<String, Object> result = new HashMap<>();

            result.put("email", email);

            // ✅ Nested tokens (clean UI)
            Map<String, Integer> tokens = new HashMap<>();
            tokens.put("prompt", promptTokens);
            tokens.put("response", responseTokens);
            tokens.put("total", totalTokens);

            result.put("tokens", tokens);
            result.put("cost", "₹" + String.format("%.4f", cost));
            result.put("model", "gemini-3-flash-preview");

            return result;

        } catch (Exception e) {
            throw new RuntimeException("Error parsing Gemini response", e);
        }
    }

    // 💰 Cost estimation method (INR)
    private double calculateCostINR(int promptTokens, int responseTokens) {

        double inputCostPer1K = 0.029;   // ₹ per 1000 tokens
        double outputCostPer1K = 0.044;  // ₹ per 1000 tokens

        double inputCost = (promptTokens / 1000.0) * inputCostPer1K;
        double outputCost = (responseTokens / 1000.0) * outputCostPer1K;

        return inputCost + outputCost;
    }

    private String buildPrompt(@Nonnull emailrequest emailRequest) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("You are an email assistant.\n");
        prompt.append("Generate ONLY ONE professional email reply.\n");
        prompt.append("Do NOT provide multiple options.\n");
        prompt.append("Do NOT include placeholders like [Name], [Your Name].\n");
        prompt.append("If name is not given, use a generic greeting like 'Dear Sir/Madam'.\n");
        prompt.append("Always include a proper subject line.\n");
        prompt.append("Return only the final email.\n\n");

        if (emailRequest.getTone() != null && emailRequest.getTone().length > 0) {
            prompt.append("Use a ").append(emailRequest.getTone()).append(" tone.\n");
        }

        prompt.append("Original email:\n");
        prompt.append(emailRequest.getOriginalemail());

        return prompt.toString();
    }
}