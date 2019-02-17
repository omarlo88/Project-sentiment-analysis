package omar.lo;

import com.microsoft.azure.cognitiveservices.textanalytics.*;
import com.microsoft.azure.cognitiveservices.textanalytics.implementation.*;
import omar.lo.analysisText.AnalysisTextServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TestApiMicrosoftAnalysisTextApplication {

    // https://github.com/Azure-Samples/cognitive-services-java-sdk-samples/blob/master/TextAnalytics/src/main/java/com/microsoft/azure/textanalytics/samples/Samples.java

    @Autowired
    private AnalysisTextServices analysisTextServices;

    public static void main(String[] args) {
        SpringApplication.run(TestApiMicrosoftAnalysisTextApplication.class, args);
    }

    @Bean
    CommandLineRunner start() {
        return args -> {

            // Create a client.
            TextAnalyticsAPIImpl client = analysisTextServices.getClient();
            client.withAzureRegion(AzureRegions.WESTEUROPE);

            // Extracting language
            System.out.println("===== LANGUAGE EXTRACTION ======");
            BatchInputInner batchInput = new BatchInputInner();
            List<Input> documents = new ArrayList<>();
            documents.add(makeInput("1", "This is a document written in English."));
            documents.add(makeInput("2", "Este es un document escrito en Español."));
            documents.add(makeInput("3", "这是一个用中文写的文件"));
            documents.add(makeInput("4", "Je m'appelle Lo Omar"));
            batchInput.withDocuments(documents);

            LanguageBatchResultInner result = client.detectLanguage(batchInput);
            // Printing language results.
            for (LanguageBatchResultItem document : result.documents()) {
                System.out.println(
                        String.format("Document ID: %s , Language: %s", document.id(),
                                document.detectedLanguages().get(0).name()));
            }


            // Getting key-phrases
            System.out.println("\n\n===== KEY-PHRASE EXTRACTION ======");
            List<MultiLanguageInput> keyPhraseInput = new ArrayList<>();
            keyPhraseInput.add(makeMultiLanguageInput("ja", "1", "猫は幸せ"));
            keyPhraseInput.add(makeMultiLanguageInput("de", "2", "Fahrt nach Stuttgart und dann zum Hotel zu Fu."));
            keyPhraseInput.add(makeMultiLanguageInput("en", "3", "My cat is stiff as a rock."));
            keyPhraseInput.add(makeMultiLanguageInput("es", "4", "A mi me encanta el fútbol!"));
            keyPhraseInput.add(makeMultiLanguageInput("fr", "5", "Je m'appelle Lo Omar en compagnie de Philippe Dugerdil, habitant à Genève"));
            MultiLanguageBatchInputInner keyPhraseInputs = new MultiLanguageBatchInputInner();
            keyPhraseInputs.withDocuments(keyPhraseInput);
            KeyPhraseBatchResultInner result2 = client.keyPhrases(keyPhraseInputs);

            // Printing keyphrases
            for (KeyPhraseBatchResultItem document : result2.documents()) {
                System.out.println(
                        String.format("Document ID: %s ", document.id()));

                System.out.println("\t Key phrases:");

                for (String keyphrase : document.keyPhrases()) {
                    System.out.println("\t\t" + keyphrase);
                }

            }

            // Extracting sentiment
            System.out.println("\n\n===== SENTIMENT ANALYSIS ======");
            List<MultiLanguageInput> sentimentInput = new ArrayList<>();
            sentimentInput.add(makeMultiLanguageInput("en", "0", "I had the best day of my life."));
            sentimentInput.add(makeMultiLanguageInput("en", "1", "This was a waste of my time. The speaker put me to sleep."));
            sentimentInput.add(makeMultiLanguageInput("es", "2", "No tengo dinero ni nada que dar..."));
            sentimentInput.add(makeMultiLanguageInput("it", "3", "L'hotel veneziano era meraviglioso. È un bellissimo pezzo di architettura."));
            sentimentInput.add(makeMultiLanguageInput("it", "4", "Je m'appelle Lo Omar, je suis content!!"));
            MultiLanguageBatchInputInner sentimentInputs = new MultiLanguageBatchInputInner();
            sentimentInputs.withDocuments(sentimentInput);
            SentimentBatchResultInner result3 = client.sentiment(sentimentInputs);


            // Printing sentiment results
            for (SentimentBatchResultItem document1 : result3.documents()) {
                System.out.println(
                        String.format("Document ID: %s , Sentiment Score: %,.2f", document1.id(), document1.score()));
            }

            // Extracting entities pas encore fait
            System.out.println("\n\n===== Entities ======");
            List<MultiLanguageInput> entitiesInput = new ArrayList<>();
            entitiesInput.add(makeMultiLanguageInput("en", "0", "I had the best day of my life."));
            entitiesInput.add(makeMultiLanguageInput("en", "1", "This was a waste of my time. The speaker put me to sleep."));
            entitiesInput.add(makeMultiLanguageInput("es", "2", "No tengo dinero ni nada que dar..."));
            entitiesInput.add(makeMultiLanguageInput("it", "3", "L'hotel veneziano era meraviglioso. È un bellissimo pezzo di architettura."));
            entitiesInput.add(makeMultiLanguageInput("it", "4", "Je m'appelle Lo Omar, je suis content!!"));
            MultiLanguageBatchInputInner entitiesInputs = new MultiLanguageBatchInputInner();
            entitiesInputs.withDocuments(entitiesInput);


            entitiesInputs.withDocuments(entitiesInput).documents().forEach(e ->{
                System.out.println(e.text());
            });


        };
    }

    private Input makeInput(String id, String text) {
        Input input = new Input();
        input.withId(id);
        input.withText(text);
        return input;
    }

    private MultiLanguageInput makeMultiLanguageInput(String language, String id, String text) {
        MultiLanguageInput input = new MultiLanguageInput();
        input.withLanguage(language);
        input.withId(id);
        input.withText(text);
        return input;
    }
}

