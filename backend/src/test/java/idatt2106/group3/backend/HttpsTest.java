package idatt2106.group3.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.http.HttpClient;

import javax.net.ssl.SSLContext;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class HttpsTest {
    
    /*
    @Test
    public void whenGETanHTTPSResource_thenCorrectResponse() throws Exception {
        SSLContext sslContext = new SSLContextBuilder()
            .loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray())
            .build();
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
        HttpClient httpClient = HttpClients.custom()
            .setSSLSocketFactory(socketFactory)
            .build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate =  new RestTemplate(factory);        
        
        ResponseEntity<String> response = restTemplate.getForEntity(WELCOME_URL, String.class, Collections.emptyMap());
    
        assertEquals("<h1>Welcome to Secured Site</h1>", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    */
    

}