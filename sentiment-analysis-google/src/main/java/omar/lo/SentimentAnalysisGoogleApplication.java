package omar.lo;

import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SentimentAnalysisGoogleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentimentAnalysisGoogleApplication.class, args);
    }

    @Bean
    CommandLineRunner start(){
        return args -> {

            // Instantiates a client
            LanguageServiceClient language = LanguageServiceClient.create();

            // The text to analyze
            String[] texts = {"I love this!", "I hate this!"};
            for (String text : texts) {
                Document doc = Document.newBuilder().setContent(text).setType(Document.Type.PLAIN_TEXT).build();
                // Detects the sentiment of the text
                Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();

                System.out.printf("Text: \"%s\"%n", text);
                System.out.printf(
                        "Sentiment: score = %s, magnitude = %s%n",
                        sentiment.getScore(), sentiment.getMagnitude());

            }

        };
    }
}

