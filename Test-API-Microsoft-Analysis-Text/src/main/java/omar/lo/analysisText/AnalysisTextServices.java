package omar.lo.analysisText;

import com.microsoft.azure.cognitiveservices.textanalytics.implementation.TextAnalyticsAPIImpl;
import com.microsoft.rest.credentials.ServiceClientCredentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AnalysisTextServices {

    private static final String URL = "https://westcentralus.api.cognitive.microsoft.com/text/analytics";
    private static final String API_KEY = "6cfb3362b38c4bbf9735d5cf836c6915";

    /*

        https://westcentralus.api.cognitive.microsoft.com/text/analytics
		https://westcentralus.api.cognitive.microsoft.com/text/analytics

		Clé 1: e929e6b0b6d74657a3c09607eb347d36

		Clé 2: 7fe1427faaf6439c9ab6405a725581da
	 */

    /*
    *   https://westcentralus.api.cognitive.microsoft.com/text/analytics

        https://westcentralus.api.cognitive.microsoft.com/text/analytics

        Key 1: 6cfb3362b38c4bbf9735d5cf836c6915

        Key 2: a88c11fb4cc34121b0ded9fb90c744b8
    * */


    public TextAnalyticsAPIImpl getClient() {
        return /*
                new TextAnalyticsAPIImpl(
                URL,
                new ServiceClientCredentials() {
                    @Override
                    public void applyCredentialsFilter(OkHttpClient.Builder builder) {
                        builder.addNetworkInterceptor(
                                new Interceptor() {
                                    @Override
                                    public Response intercept(Chain chain) throws IOException {
                                        Request request = null;
                                        Request original = chain.request();
                                        // Request customization: add request headers
                                        Request.Builder requestBuilder = original.newBuilder()
                                                .addHeader("Ocp-Apim-Subscription-Key", API_KEY);
                                        request = requestBuilder.build();
                                        return chain.proceed(request);
                                    }
                                }
                        );
                    }
                }
        );
*/
                new TextAnalyticsAPIImpl(
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


}// AnalysisTextServices
