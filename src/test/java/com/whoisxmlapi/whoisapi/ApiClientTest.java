package com.whoisxmlapi.whoisapi;

import com.whoisxmlapi.whoisapi.exception.*;
import com.whoisxmlapi.whoisapi.json.WhoisRecordParser;
import com.whoisxmlapi.whoisapi.model.BaseRecord;
import com.whoisxmlapi.whoisapi.model.RequestParameters;
import com.whoisxmlapi.whoisapi.model.WhoisRecord;
import com.whoisxmlapi.whoisapi.net.HttpClient;
import com.whoisxmlapi.whoisapi.ApiClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiClientTest {

    @Test
    void getWhois() throws EmptyApiKeyException, NetworkException, ApiEndpointException, ApiErrorMessageException, ApiAuthorizationException {
        ApiClient client = new ApiClient(System.getenv("API_KEY"));
        WhoisRecord whois = client.getWhois("whoisxmlapi.com");
        assertTrue(whois.getDomainName().isPresent());
        assertEquals("whoisxmlapi.com", whois.getDomainName().get());

        assertThrows(ApiErrorMessageException.class, () -> {client.getWhois("whoisxmlapi");});
    }

    @Test
    void testGetWhois() throws EmptyApiKeyException, NetworkException, ApiEndpointException, ApiErrorMessageException, ApiAuthorizationException {
        ApiClient client = new ApiClient(System.getenv("API_KEY"));
        RequestParameters rp = new RequestParameters(System.getenv("API_KEY"));
        rp.setDa("2");
        WhoisRecord whois = client.getWhois("whoisxmlapi.com", rp);
        assertTrue(whois.getDomainAvailability().isPresent());
        assertEquals("UNAVAILABLE", whois.getDomainAvailability().get());

        rp.setDa("0");
        WhoisRecord whois2 = client.getWhois("whoisxmlapi.com", rp);
        assertFalse(whois2.getDomainAvailability().isPresent());
    }

    @Test
    void getRawResponse() throws BaseException {
        ApiClient client = new ApiClient(System.getenv("API_KEY"));
        String response = client.getRawResponse("whoisxmlapi.com");
        assertEquals("whoisxmlapi.com", WhoisRecordParser.parse(response).getDomainName().get());
    }

    @Test
    void testGetRawResponse() throws BaseException {
        ApiClient client = new ApiClient(System.getenv("API_KEY"));
        RequestParameters rp = new RequestParameters(System.getenv("API_KEY"));
        rp.setDa("2");
        String response = client.getRawResponse("whoisxmlapi.com", rp);
        assertEquals("UNAVAILABLE", WhoisRecordParser.parse(response).getDomainAvailability().get());
    }

    @Test
    void testGetRawResponse1() throws BaseException {
        ApiClient client = new ApiClient(System.getenv("API_KEY"));
        String response = client.getRawResponse("whoisxmlapi.com", HttpClient.ResponseFormat.XML);
        assertTrue(response.contains("<?xml "));
    }

    @Test
    void testGetWhois1() throws Exception {
        RequestParameters rp = new RequestParameters(System.getenv("API_KEY"));
        rp.setDa("2");
        ApiClient client = new ApiClient(rp);
        String[] domains = (new String[]{"whoisxmlapi.com", "google.com"});
        BaseRecord[] records = client.getWhois(domains);
        for (BaseRecord record: records) {
            assertEquals(WhoisRecord.class, record.getClass());
            assertEquals("UNAVAILABLE", ((WhoisRecord) record).getDomainAvailability().get());
        }
    }

    @Test
    void testGetWhois2() throws Exception {
        ApiClient client = new ApiClient(System.getenv("API_KEY"));
        String[] domains = (new String[]{"whoisxmlapi.com", "google.com"});
        RequestParameters rp = new RequestParameters(System.getenv("API_KEY"));
        rp.setDa("0");
        BaseRecord[] records = client.getWhois(domains, rp);
        for (BaseRecord record: records) {
            assertEquals(WhoisRecord.class, record.getClass());
            assertFalse(((WhoisRecord) record).getDomainAvailability().isPresent());
        }
    }

    @Test
    void testGetRawResponse2() throws Exception {
        ApiClient client = new ApiClient(System.getenv("API_KEY"));
        String[] records = client.getRawResponse(new String[]{"whoisxmlapi.com", "google"});
        for (String record: records) {
            if (record.contains("whoisxmlapi.com")) {
                assertTrue(record.contains("WhoisRecord"));
            } else {
                assertTrue(record.contains("ErrorMessage"));
            }
        }
    }

    @Test
    void testGetRawResponse3() throws Exception {
        ApiClient client = new ApiClient(System.getenv("API_KEY"));
        String[] records = client.getRawResponse(new String[]{"whoisxmlapi.com", "google.com"},
                new RequestParameters(System.getenv("API_KEY").replace("0", "1")));
        assertTrue(records[0].contains("ErrorMessage"));
    }

    @Test
    void testGetRawResponse4() throws Exception {
        ApiClient client = new ApiClient(System.getenv("API_KEY"));
        String[] records = client.getRawResponse(new String[]{"whoisxmlapi.com", "google.com"}, HttpClient.ResponseFormat.XML);
        assertTrue(records[0].contains("<?xml "));
    }

    @Test
    void setRetries() {
        ApiClient client = new ApiClient(System.getenv("API_KEY"));
        assertDoesNotThrow(() -> {client.setRetries(2);});
        assertThrows(IllegalArgumentException.class, () -> {client.setRetries(-1);});
    }

    @Test
    void getRetries() {
        ApiClient client = new ApiClient(System.getenv("API_KEY"));
        client.setRetries(2);
        assertEquals(2, client.getRetries());
    }

    @Test
    void setPoolSize() {
        ApiClient client = new ApiClient(System.getenv("API_KEY"));
        assertDoesNotThrow( () -> {client.setPoolSize(5);});
        assertThrows(IllegalArgumentException.class, () -> { client.setPoolSize(222); });
    }

    @Test
    void getPoolSize() {
        ApiClient client = new ApiClient(System.getenv("API_KEY"));
        client.setPoolSize(2);
        assertEquals(2, client.getPoolSize());
    }
}