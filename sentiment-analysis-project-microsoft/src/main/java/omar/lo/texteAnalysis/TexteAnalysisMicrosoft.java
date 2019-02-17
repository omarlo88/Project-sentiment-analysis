package omar.lo.texteAnalysis;

import com.microsoft.azure.cognitiveservices.textanalytics.implementation.TextAnalyticsAPIImpl;
import com.microsoft.rest.credentials.ServiceClientCredentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TexteAnalysisMicrosoft {

    private static final String URL = "https://westcentralus.api.cognitive.microsoft.com/text/analytics";
    private static final String API_KEY = "d2b2d393186c4e77a79512daea47c93a";

     /*
    *   https://westcentralus.api.cognitive.microsoft.com/text/analytics

        https://westcentralus.api.cognitive.microsoft.com/text/analytics

        Key 1: 6cfb3362b38c4bbf9735d5cf836c6915

        Key 2: a88c11fb4cc34121b0ded9fb90c744b8
    * */

    public TextAnalyticsAPIImpl getClient() {
        return new TextAnalyticsAPIImpl(
                URL,
                builder -> builder.addNetworkInterceptor(
                        chain -> {
                            Request request = null;
                            Request original = chain.request();
                            // Request customization: add request headers
                            Request.Builder requestBuilder = original.newBuilder()
                                    .addHeader("Ocp-Apim-Subscription-Key", API_KEY);
                            request = requestBuilder.build();
                            return chain.proceed(request);
                        }
                )
        );
    }

}// TexteAnalysisMicrosoft
