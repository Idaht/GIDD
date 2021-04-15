package idatt2106.group3.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import javax.net.ssl.SSLContext;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
      HttpComponentsClientHttpRequestFactory factory = 
        new HttpComponentsClientHttpRequestFactory(httpClient);
      return new RestTemplate(factory);        
        
        ResponseEntity<String> response = restTemplate().getForEntity(WELCOME_URL, String.class, Collections.emptyMap());
    
        assertEquals("<h1>Welcome to Secured Site</h1>", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    */

}