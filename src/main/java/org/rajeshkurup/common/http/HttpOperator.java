package org.rajeshkurup.common.http;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.NonNull;

public class HttpOperator implements HttpOperations {

    private String url;
    private Map<String, String> headers;
    private String body;
    private boolean insecure;
    private int connectTimeoutMs;
    private int readTimeoutMs;

    private HttpOperator(@NonNull final String url, final Map<String, String> headers, final String body, final boolean insecure, final int connectTimeoutMs, final int readTimeoutMs) {
        this.url = url;
        this.headers = headers;
        this.body = body;
        this.insecure = insecure;
        this.connectTimeoutMs = connectTimeoutMs;
        this.readTimeoutMs = readTimeoutMs;
    }

    @Override
    public Optional<HttpOpsResponse> get() {
        return execute(HttpMethod.GET);
    }

    @Override
    public Optional<HttpOpsResponse> post() {
        return execute(HttpMethod.POST);
    }

    @Override
    public Optional<HttpOpsResponse> put() {
        return execute(HttpMethod.PUT);
    }

    @Override
    public Optional<HttpOpsResponse> delete() {
        return execute(HttpMethod.DELETE);
    }

    private Optional<HttpOpsResponse> execute(HttpMethod method) {
        try(HttpClient client = createInsecureHttpClient()) {
            HttpResponse<String> response = client.send(getHttpRequest(method), HttpResponse.BodyHandlers.ofString());
            return Optional.of(new HttpOpsResponse(response.statusCode(), response.body(), null));
        } catch (Exception e) {
            return Optional.of(new HttpOpsResponse(0, null, new HttpOpsError(-1, "HttpOperations::execute Failed - " + e.toString() + System.lineSeparator() + getStackTrace(e))));
        }
    }

    private HttpRequest getHttpRequest(HttpMethod method) {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(URI.create(this.url));
        if(MapUtils.isNotEmpty(this.headers)) {
            this.headers.forEach(requestBuilder::header);
        }
        if(this.readTimeoutMs > 0) {
            requestBuilder.timeout(Duration.ofMillis(this.readTimeoutMs));
        }
        switch(method) {
            case GET: return requestBuilder.GET().build();
            case POST: return requestBuilder.POST(StringUtils.isNotBlank(this.body) ? HttpRequest.BodyPublishers.ofString(this.body) : HttpRequest.BodyPublishers.noBody()).build();
            case PUT: return requestBuilder.PUT(StringUtils.isNotBlank(this.body) ? HttpRequest.BodyPublishers.ofString(this.body) : HttpRequest.BodyPublishers.noBody()).build();
            case DELETE: return requestBuilder.DELETE().build();
            default: return null;
        }
    }

    private HttpClient createInsecureHttpClient() throws NoSuchAlgorithmException, KeyManagementException {
        HttpClient.Builder clientBuilder = HttpClient.newBuilder();
        if(this.insecure) {
            TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        // Empty implementation
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        // Empty implementation
                    }
                }
            };

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            clientBuilder.sslContext(sslContext);
        }

        if(this.connectTimeoutMs > 0) {
            clientBuilder.connectTimeout(Duration.ofMillis(connectTimeoutMs));
        }
        
        return clientBuilder.build();
    }

    private String getStackTrace(Throwable throwable) {
        return StringUtils.join(Arrays.asList(throwable.getStackTrace()), System.lineSeparator());
    }

    public static Builder builder(@NonNull final String url) {
        return new Builder(url);
    }

    public static class Builder {

        private String url;
        private Map<String, String> headers;
        private String body;
        private boolean insecure;
        private int connectTimeoutMs;
        private int readTimeoutMs;

        private Builder(@NonNull final String url) {
            this.url = url;
        }

        public Builder headers(@NonNull final Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder body(@NonNull final String body) {
            this.body = body;
            return this;
        }

        public Builder url(@NonNull final String url) {
            this.url = url;
            return this;
        }

        public Builder insecure(final boolean insecure) {
            this.insecure = insecure;
            return this;
        }

        public Builder connectTimeoutMs(final int connectTimeoutMs) {
            this.connectTimeoutMs = connectTimeoutMs;
            return this;
        }

        public Builder readTimeoutMs(final int readTimeoutMs) {
            this.readTimeoutMs = readTimeoutMs;
            return this;
        }

        public HttpOperations build() {
            return new HttpOperator(this.url, this.headers, this.body, this.insecure, this.connectTimeoutMs, this.readTimeoutMs);
        }

    }

    private static enum HttpMethod {
        GET, POST, PUT, DELETE;
    }

}
