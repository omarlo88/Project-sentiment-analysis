package omar.lo;

import com.microsoft.azure.cognitiveservices.textanalytics.AzureRegions;
import com.microsoft.azure.cognitiveservices.textanalytics.MultiLanguageInput;
import com.microsoft.azure.cognitiveservices.textanalytics.SentimentBatchResultItem;
import com.microsoft.azure.cognitiveservices.textanalytics.implementation.MultiLanguageBatchInputInner;
import com.microsoft.azure.cognitiveservices.textanalytics.implementation.SentimentBatchResultInner;
import com.microsoft.azure.cognitiveservices.textanalytics.implementation.TextAnalyticsAPIImpl;
import omar.lo.texteAnalysis.TexteAnalysisMicrosoft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SentimentAnalysisProjectMicrosoftApplication {

    //@Autowired
    //private TexteAnalysisMicrosoft texteAnalysisMicrosoft;

    @Bean
    CommandLineRunner start(TexteAnalysisMicrosoft texteAnalysisMicrosoft){
        return args -> {

            // Create a client.
            TextAnalyticsAPIImpl client = texteAnalysisMicrosoft.getClient();
            client.withAzureRegion(AzureRegions.WESTEUROPE);

            // Extracting sentiment
            System.out.println("\n\n===== SENTIMENT ANALYSIS ======");
            List<MultiLanguageInput> sentimentInput = new ArrayList<>();
            sentimentInput.add(makeMultiLanguageInput("en", "0", "It's sunny, I'm going out to have a beer with my friends."));
            sentimentInput.add(makeMultiLanguageInput(
                    "en", "1", "This was a waste of my time. The speaker put me to sleep."));
            sentimentInput.add(makeMultiLanguageInput("es", "2", "No tengo dinero ni nada que dar..."));
            sentimentInput.add(makeMultiLanguageInput(
                    "it", "3", "L'hotel veneziano era meraviglioso. È un bellissimo pezzo di architettura."));
            sentimentInput.add(makeMultiLanguageInput("fr", "4", "Je suis vraiment content de votre service!!"));
            sentimentInput.add(makeMultiLanguageInput("fr", "5", "Je déteste rester seul à la maison."));
            MultiLanguageBatchInputInner sentimentInputs = new MultiLanguageBatchInputInner();
            sentimentInputs.withDocuments(sentimentInput);
            SentimentBatchResultInner result = client.sentiment(sentimentInputs);

            // Printing sentiment results
            for (SentimentBatchResultItem document : result.documents()) {
                System.out.println(
                        String.format("Document ID: %s , Sentiment Score: %,.2f", document.id(), document.score()));
            }

        };
    }

    private MultiLanguageInput makeMultiLanguageInput(String language, String id, String text) {
        MultiLanguageInput input = new MultiLanguageInput();
        input.withLanguage(language);
        input.withId(id);
        input.withText(text);
        return input;
    }

    public static void main(String[] args) {
        SpringApplication.run(SentimentAnalysisProjectMicrosoftApplication.class, args);
    }

}

