package org.rajeshkurup.common.http;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class HttpOperatorTest {

    private HttpOperations httpOperator;

    @AfterEach
    public void cleanup() {
        this.httpOperator = null;
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetPass() throws IOException, InterruptedException {
        this.httpOperator = HttpOperator.builder("http://www.google.com").insecure(true).build();
        try (MockedStatic<HttpClient> mockedHttpClient = mockStatic(HttpClient.class)) {
            HttpClient.Builder httpClientBuilderMock = mock(HttpClient.Builder.class);
            HttpClient httpClientMock = mock(HttpClient.class);
            HttpResponse<String> httpResponseMock = mock(HttpResponse.class);

            mockedHttpClient.when(HttpClient::newBuilder).thenReturn(httpClientBuilderMock);
            when(httpClientBuilderMock.sslContext(any(SSLContext.class))).thenReturn(httpClientBuilderMock);
            when(httpClientBuilderMock.build()).thenReturn(httpClientMock);
            when(httpClientMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponseMock);
            when(httpResponseMock.statusCode()).thenReturn(200);
            when(httpResponseMock.body()).thenReturn("Mocked response body");

            Assertions.assertEquals(200, httpOperator.get().get().getHttpStatus());

            verify(httpClientBuilderMock, times(1)).sslContext(any(SSLContext.class));
            verify(httpClientBuilderMock, times(1)).build();
            verify(httpClientMock, times(1)).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
            verify(httpResponseMock, times(1)).statusCode();
            verify(httpResponseMock, times(1)).body();
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetPassWithHeaderAndReadTimeout() throws IOException, InterruptedException {
        this.httpOperator = HttpOperator.builder("http://www.google.com").headers(Map.of("Accept", "application/json")).readTimeoutMs(1000).build();
        try (MockedStatic<HttpClient> mockedHttpClient = mockStatic(HttpClient.class)) {
            HttpClient.Builder httpClientBuilderMock = mock(HttpClient.Builder.class);
            HttpClient httpClientMock = mock(HttpClient.class);
            HttpResponse<String> httpResponseMock = mock(HttpResponse.class);

            mockedHttpClient.when(HttpClient::newBuilder).thenReturn(httpClientBuilderMock);
            when(httpClientBuilderMock.sslContext(any(SSLContext.class))).thenReturn(httpClientBuilderMock);
            when(httpClientBuilderMock.build()).thenReturn(httpClientMock);
            when(httpClientMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponseMock);
            when(httpResponseMock.statusCode()).thenReturn(200);
            when(httpResponseMock.body()).thenReturn("Mocked response body");

            Assertions.assertEquals(200, httpOperator.get().get().getHttpStatus());

            verify(httpClientBuilderMock, times(0)).sslContext(any(SSLContext.class));
            verify(httpClientBuilderMock, times(1)).build();
            verify(httpClientMock, times(1)).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
            verify(httpResponseMock, times(1)).statusCode();
            verify(httpResponseMock, times(1)).body();
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetFailException() throws IOException, InterruptedException {
        this.httpOperator = HttpOperator.builder("https://www.google.com").connectTimeoutMs(1000).build();
        try (MockedStatic<HttpClient> mockedHttpClient = mockStatic(HttpClient.class)) {
            HttpClient.Builder httpClientBuilderMock = mock(HttpClient.Builder.class);
            HttpClient httpClientMock = mock(HttpClient.class);
            HttpResponse<String> httpResponseMock = mock(HttpResponse.class);

            mockedHttpClient.when(HttpClient::newBuilder).thenReturn(httpClientBuilderMock);
            when(httpClientBuilderMock.sslContext(any(SSLContext.class))).thenReturn(httpClientBuilderMock);
            when(httpClientBuilderMock.build()).thenReturn(httpClientMock);
            when(httpClientMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenThrow(new IOException("Mocked exception"));
            when(httpResponseMock.statusCode()).thenReturn(200);
            when(httpResponseMock.body()).thenReturn("Mocked response body");

            HttpOpsResponse httpOpsResponse = httpOperator.get().get();
            Assertions.assertEquals(0, httpOpsResponse.getHttpStatus());
            Assertions.assertNotNull(httpOpsResponse.getError());
            Assertions.assertEquals(-1, httpOpsResponse.getError().getErrCode());

            verify(httpClientBuilderMock, times(0)).sslContext(any(SSLContext.class));
            verify(httpClientBuilderMock, times(1)).build();
            verify(httpClientMock, times(1)).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
            verify(httpResponseMock, times(0)).statusCode();
            verify(httpResponseMock, times(0)).body();
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDeletePass() throws IOException, InterruptedException {
        this.httpOperator = HttpOperator.builder("http://www.google.com").insecure(true).build();
        try (MockedStatic<HttpClient> mockedHttpClient = mockStatic(HttpClient.class)) {
            HttpClient.Builder httpClientBuilderMock = mock(HttpClient.Builder.class);
            HttpClient httpClientMock = mock(HttpClient.class);
            HttpResponse<String> httpResponseMock = mock(HttpResponse.class);

            mockedHttpClient.when(HttpClient::newBuilder).thenReturn(httpClientBuilderMock);
            when(httpClientBuilderMock.sslContext(any(SSLContext.class))).thenReturn(httpClientBuilderMock);
            when(httpClientBuilderMock.build()).thenReturn(httpClientMock);
            when(httpClientMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponseMock);
            when(httpResponseMock.statusCode()).thenReturn(200);
            when(httpResponseMock.body()).thenReturn("Mocked response body");

            Assertions.assertEquals(200, httpOperator.delete().get().getHttpStatus());

            verify(httpClientBuilderMock, times(1)).sslContext(any(SSLContext.class));
            verify(httpClientBuilderMock, times(1)).build();
            verify(httpClientMock, times(1)).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
            verify(httpResponseMock, times(1)).statusCode();
            verify(httpResponseMock, times(1)).body();
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testPostPass() throws IOException, InterruptedException {
        this.httpOperator = HttpOperator.builder("http://www.google.com").insecure(true).body("Hello World!").build();
        try (MockedStatic<HttpClient> mockedHttpClient = mockStatic(HttpClient.class)) {
            HttpClient.Builder httpClientBuilderMock = mock(HttpClient.Builder.class);
            HttpClient httpClientMock = mock(HttpClient.class);
            HttpResponse<String> httpResponseMock = mock(HttpResponse.class);

            mockedHttpClient.when(HttpClient::newBuilder).thenReturn(httpClientBuilderMock);
            when(httpClientBuilderMock.sslContext(any(SSLContext.class))).thenReturn(httpClientBuilderMock);
            when(httpClientBuilderMock.build()).thenReturn(httpClientMock);
            when(httpClientMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponseMock);
            when(httpResponseMock.statusCode()).thenReturn(200);
            when(httpResponseMock.body()).thenReturn("Mocked response body");

            Assertions.assertEquals(200, httpOperator.post().get().getHttpStatus());

            verify(httpClientBuilderMock, times(1)).sslContext(any(SSLContext.class));
            verify(httpClientBuilderMock, times(1)).build();
            verify(httpClientMock, times(1)).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
            verify(httpResponseMock, times(1)).statusCode();
            verify(httpResponseMock, times(1)).body();
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testPostPassWithNoBody() throws IOException, InterruptedException {
        this.httpOperator = HttpOperator.builder("http://www.google.com").insecure(true).build();
        try (MockedStatic<HttpClient> mockedHttpClient = mockStatic(HttpClient.class)) {
            HttpClient.Builder httpClientBuilderMock = mock(HttpClient.Builder.class);
            HttpClient httpClientMock = mock(HttpClient.class);
            HttpResponse<String> httpResponseMock = mock(HttpResponse.class);

            mockedHttpClient.when(HttpClient::newBuilder).thenReturn(httpClientBuilderMock);
            when(httpClientBuilderMock.sslContext(any(SSLContext.class))).thenReturn(httpClientBuilderMock);
            when(httpClientBuilderMock.build()).thenReturn(httpClientMock);
            when(httpClientMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponseMock);
            when(httpResponseMock.statusCode()).thenReturn(200);
            when(httpResponseMock.body()).thenReturn("Mocked response body");

            Assertions.assertEquals(200, httpOperator.post().get().getHttpStatus());

            verify(httpClientBuilderMock, times(1)).sslContext(any(SSLContext.class));
            verify(httpClientBuilderMock, times(1)).build();
            verify(httpClientMock, times(1)).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
            verify(httpResponseMock, times(1)).statusCode();
            verify(httpResponseMock, times(1)).body();
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testPutPass() throws IOException, InterruptedException {
        this.httpOperator = HttpOperator.builder("http://www.google.com").insecure(true).body("Hello World!").build();
        try (MockedStatic<HttpClient> mockedHttpClient = mockStatic(HttpClient.class)) {
            HttpClient.Builder httpClientBuilderMock = mock(HttpClient.Builder.class);
            HttpClient httpClientMock = mock(HttpClient.class);
            HttpResponse<String> httpResponseMock = mock(HttpResponse.class);

            mockedHttpClient.when(HttpClient::newBuilder).thenReturn(httpClientBuilderMock);
            when(httpClientBuilderMock.sslContext(any(SSLContext.class))).thenReturn(httpClientBuilderMock);
            when(httpClientBuilderMock.build()).thenReturn(httpClientMock);
            when(httpClientMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponseMock);
            when(httpResponseMock.statusCode()).thenReturn(200);
            when(httpResponseMock.body()).thenReturn("Mocked response body");

            Assertions.assertEquals(200, httpOperator.put().get().getHttpStatus());

            verify(httpClientBuilderMock, times(1)).sslContext(any(SSLContext.class));
            verify(httpClientBuilderMock, times(1)).build();
            verify(httpClientMock, times(1)).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
            verify(httpResponseMock, times(1)).statusCode();
            verify(httpResponseMock, times(1)).body();
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testPutPassWithNoBody() throws IOException, InterruptedException {
        this.httpOperator = HttpOperator.builder("http://www.google.com").insecure(true).build();
        try (MockedStatic<HttpClient> mockedHttpClient = mockStatic(HttpClient.class)) {
            HttpClient.Builder httpClientBuilderMock = mock(HttpClient.Builder.class);
            HttpClient httpClientMock = mock(HttpClient.class);
            HttpResponse<String> httpResponseMock = mock(HttpResponse.class);

            mockedHttpClient.when(HttpClient::newBuilder).thenReturn(httpClientBuilderMock);
            when(httpClientBuilderMock.sslContext(any(SSLContext.class))).thenReturn(httpClientBuilderMock);
            when(httpClientBuilderMock.build()).thenReturn(httpClientMock);
            when(httpClientMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponseMock);
            when(httpResponseMock.statusCode()).thenReturn(200);
            when(httpResponseMock.body()).thenReturn("Mocked response body");

            Assertions.assertEquals(200, httpOperator.put().get().getHttpStatus());

            verify(httpClientBuilderMock, times(1)).sslContext(any(SSLContext.class));
            verify(httpClientBuilderMock, times(1)).build();
            verify(httpClientMock, times(1)).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
            verify(httpResponseMock, times(1)).statusCode();
            verify(httpResponseMock, times(1)).body();
        }
    }

}
