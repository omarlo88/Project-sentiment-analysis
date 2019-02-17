package omar.lo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClient;
import com.amazonaws.services.comprehend.model.DetectEntitiesRequest;
import com.amazonaws.services.comprehend.model.DetectEntitiesResult;
import com.amazonaws.services.comprehend.model.DetectSentimentRequest;
import com.amazonaws.services.comprehend.model.DetectSentimentResult;

@SpringBootApplication
public class SentimentAnalysisAmazonApplication {

	public static void main(String[] args) {
		SpringApplication.run(SentimentAnalysisAmazonApplication.class, args);
	}

	@Bean
	CommandLineRunner start() {
		return args -> {

			String text = "It is sunny, I'm going out to have a beer with my friends.";

			/*
			 * On peut soit setter les credentials avec le code Java, soit les setter avec
			 * AWS Toolkit sur Eclipse
			 */
			// String accessKey = "AKIAJV6765SEUDM6J7WA";
			// String secretKey = "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";
			
			String accessKey = "AKIAJU2WIVB3HSCDBYKA";
			String secretKey = "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";

			/*
			 * AWSCredentialsProvider awsCred = new AWSStaticCredentialsProvider( new
			 * BasicAWSCredentials(accessKey, secretKey));
			 * 
			 * AmazonComprehend amazonClient = AmazonComprehendClient.builder()
			 * .withCredentials(awsCred) .withRegion("us-west-2").build();
			 */

			/* Les credentials sont configurés avec AWS Toolkit sur Eclipse */
			AWSCredentialsProvider awsCred = DefaultAWSCredentialsProviderChain.getInstance();

			AmazonComprehend amazonClient = AmazonComprehendClient.builder()
					.withCredentials(awsCred)
					.withRegion("us-west-2").build();
			
			//Détection des sentiments
			DetectSentimentRequest detectSentimentRequest = new DetectSentimentRequest()
					.withText(text)
					.withLanguageCode("fr");
			DetectSentimentResult result = amazonClient.detectSentiment(detectSentimentRequest);
			System.out.println(result);

			// Détection des entités
			/*DetectEntitiesRequest detectEntitiesRequest = new DetectEntitiesRequest()
					.withText(text)
					.withLanguageCode("fr");
			DetectEntitiesResult result1 = amazonClient.detectEntities(detectEntitiesRequest);
			System.out.println(result1);*/
		};
	}
}
