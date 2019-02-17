package omar.lo;

import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.*;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ProjetSentimentAnalysisApplication {

   
    private String myTexte = "I hate staying alone at home.";


    public static void main(String[] args) {
        SpringApplication.run(ProjetSentimentAnalysisApplication.class, args);
    }


    @Bean
    CommandLineRunner start(){
        return args -> {

            /*
            *  API Natural language Understanding
            * */

            final String API_KEY = "lYSzIQMGHeuV9kirwfzi4gCiYjCaAwVFxJg2Xa4t4vc3";
            final String URL = "https://gateway-lon.watsonplatform.net/natural-language-understanding/api";
            // Instanciation du service NLU
            IamOptions options = new IamOptions.Builder()
                    .apiKey(API_KEY)
                    .build();
            NaturalLanguageUnderstanding service = new NaturalLanguageUnderstanding(
                    "2018-11-16", options);
            service.setEndPoint(URL);

            // Traitement et appel du service NLU
            SentimentOptions sentiment = new SentimentOptions.Builder()
                    //.targets(targets)
                    .build();

            EmotionOptions emotionOptions = new EmotionOptions.Builder().build();

            Features features = new Features.Builder()
                    .sentiment(sentiment)
                    .emotion(emotionOptions)
                    .build();

            AnalyzeOptions parameters = new AnalyzeOptions.Builder()
                    .text(myTexte)
                    .features(features)
                    .build();

            AnalysisResults response = service
                    .analyze(parameters)
                    .execute();

            System.out.println(response.getSentiment()); // Result for get sentiment
            System.out.println(response.getEmotion());// Result for get emotion




            /*
             *  API Tone Analyzer
             * */

            String apiKey = "6J916C2b0_Iqn85RxcDNqdPn0T1_bDrGTpA5g4elbv_a";
            String url = "https://gateway-lon.watsonplatform.net/tone-analyzer/api";

            IamOptions optionsTone = new IamOptions.Builder()
                    .apiKey(apiKey)
                    .build();

            ToneAnalyzer toneAnalyzer = new ToneAnalyzer("2017-09-21", optionsTone);
            toneAnalyzer.setEndPoint(url);

            ToneOptions toneOptions = new ToneOptions.Builder()
                    .text(myTexte)
                    .build();

            ToneAnalysis toneAnalysis = toneAnalyzer.tone(toneOptions).execute();
            System.out.println(toneAnalysis.getDocumentTone());


            System.out.println("************** Tone Analyzer ************");


            List<Utterance> utterances = new ArrayList<>();
            utterances.add(new Utterance.Builder()
                    .text(myTexte)
                    .user("customer")
                    .build());

            ToneChatOptions toneChatOptions = new ToneChatOptions.Builder()
                    .utterances(utterances)
                    .build();

            UtteranceAnalyses utteranceAnalyses =
                    toneAnalyzer.toneChat(toneChatOptions).execute();
            System.out.println(utteranceAnalyses.getUtterancesTone());


        };
    }
}

