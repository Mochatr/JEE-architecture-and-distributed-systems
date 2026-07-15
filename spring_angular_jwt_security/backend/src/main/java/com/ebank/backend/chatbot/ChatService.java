package com.ebank.backend.chatbot;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service
@AllArgsConstructor
public class ChatService {

    private static final Logger log = LoggerFactory.getLogger(ChatService.class);

    private final BankContextRetriever contextRetriever;
    private final OpenAiClient openAiClient;

    public String ask(String question) {
        if (!openAiClient.isConfigured()) {
            return "Le chatbot n'est pas configuré : la clé OpenAI est manquante côté serveur.";
        }

        String context = contextRetriever.retrieveContext(question);
        String systemPrompt = """
                Tu es l'assistant virtuel de la banque E-Bank. Réponds en français, de manière concise,
                en te basant uniquement sur les informations de comptes bancaires fournies ci-dessous.
                Si l'information demandée n'est pas dans le contexte, dis-le clairement au lieu d'inventer une réponse.

                Contexte (comptes bancaires) :
                %s
                """.formatted(context);

        try {
            return openAiClient.chatCompletion(systemPrompt, question);
        } catch (RestClientException e) {
            log.error("Échec de l'appel à l'API OpenAI", e);
            return "Le chatbot est momentanément indisponible (erreur de communication avec le fournisseur IA).";
        }
    }
}
