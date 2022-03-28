[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0) [![](https://jitpack.io/v/com.whoisxmlapi/whois-api-java.svg)](https://jitpack.io/#com.whoisxmlapi/whois-api-java) [![](https://github.com/whois-api-llc/whois-api-java/workflows/Tests/badge.svg)](https://github.com/whois-api-llc/whois-api-java/actions)

# Whois XML API client library
The java library for the [Whois XML API service](https://whois.whoisxmlapi.com/).

## Requirements
* JRE version 1.8 or newer.
* WhoisXMLAPI account and the API key. The API key could be obtained [here](https://user.whoisxmlapi.com/products).

## Installation

### Gradle
```groovy
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
```
```groovy
    dependencies {
        implementation 'com.whoisxmlapi:whois-api-java:v1.1.0'
    }
```

### Maven

```xml
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
```
```xml
	<dependency>
	    <groupId>com.whoisxmlapi</groupId>
	    <artifactId>whois-api-java</artifactId>
	    <version>v1.1.0</version>
	</dependency>
```
### Jar files
Jar files are available on the release page.


## Usage
API documentation available [here](https://whois.whoisxmlapi.com/documentation/making-requests).

The minimal usage sample:
```java
        //Creating a client object with an API key and default settings
        ApiClient client = new ApiClient(System.getenv("API_KEY"));

        try {
            WhoisRecord record = client.getWhois("whoisxmlapi.com");

            // Model's getters return an Option<T> (Option<String>)
            if (record.getDomainName().isPresent()) {
                System.out.println(record.getDomainName().get());
            }
            // Or
            record.getContactEmail().ifPresent((email) -> System.out.println(email));
        } catch (BaseException e) {
            System.err.println(e.getMessage());
        }
```

Passing an invalid domain name
```java
        try {
            // Sometimes, when your request is invalid you will receive an error response
            WhoisRecord record2 = client.getWhois("incorrectdomain");
        } catch (ApiErrorException e) {
            // In that case the ApiErrorException will be thrown
            // Use e.getErrorMessageEntity() to get ErrorMessage object (Could be a null!)
            System.err.println(e.getApiErrorMessage());
        } catch (BaseException e) {
            System.err.println(e.getMessage());
        }
```

If the API key is missed the IllegalArgumentException will be thrown.
```java
        try {
            // The IllegalArgumentException will be thrown if the empty API key is specified
            // If the API key is null the NullPointerException will be thrown
            ApiClient client1 = new ApiClient("");
        } catch (IllegalArgumentException exception) {
            System.err.println(exception.getMessage());
        }
```

Handling various exceptions
```java
        try {
            // The ApiAuthorizationException will be thrown if you are not permitted to perform queries
            // This could be caused by invalid API key value or empty account balance
            ApiClient client1 = new ApiClient(System.getenv("API_KEY").replace('0', '9'));
            WhoisRecord record = client1.getWhois("whoisxmlapi.net");
        } catch (ApiAuthorizationException exception) {
            System.err.println(exception.getMessage());
        } catch (NetworkException exception) {
            // NetworkException means that there was something wrong with connection
            System.err.println(exception.getMessage());
        } catch (ApiEndpointException exception) {
            // In general, this exception means that API returned HTTP 5XX code
            System.err.println(exception.getMessage());
        } catch (EmptyApiKeyException exception) {
            System.err.println(exception.getMessage());
        } catch (ApiErrorMessageException exception) {
            // This exception means that API response contains ErrorMessage field
            System.err.println(exception.getMessage());
        }
```

Customizing request parameters:
```java
        try {
            // Creating a request parameters object
            RequestParameters rp = new RequestParameters(System.getenv("API_KEY"));
            // Setting domain availability check level to 2
            rp.setDa("2");
            WhoisRecord record3 = client.getWhois("whoisxmlapi.com", rp);
            record3.getDomainAvailability().ifPresent((da) -> System.out.println(da));
        } catch (BaseException exception) {
            System.err.println(exception.getMessage());
        }
```

Domain list processing is supported:
```java
        // Handling a list of domains
        String[] domains = new String[]{"whoisxmlapi.com", "whoisxmlapi.net", "incorrectdomain"};
        try {
            // Setting the number of concurrent requests
            // Allowed values are from 1 to 10, default is 5
            client.setPoolSize(2);
            // The auto-retry feature available
            client.setRetries(2);
            BaseRecord[] records = client.getWhois(domains);
            for (BaseRecord record: records) {
                if (record.getClass() == WhoisRecord.class) {
                    ((WhoisRecord) record).getDomainName().ifPresent((domain) -> System.out.println(domain));
                } else {
                    ((ErrorMessage) record).getMsg().ifPresent((message) -> System.out.println(message));
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
```
**Warning!** The internal executor service keeps running for some time after all requests have been completed.
You can force the service to stop using the following method:
```java
client.forceShutdown();
```
The client instance cannot be reused after this. You have to create a new one.

Getting raw API responses instead of parsed data
```java
        // Getting raw responses
        try {
            // Specifying response format
            String[] rawResponses = client.getRawResponse(domains, HttpClient.ResponseFormat.XML);
            for (String response: rawResponses) {
                System.out.println(response);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
```

Specifying custom timeouts and request parameters
```java
        RequestParameters rp = new RequestParameters(System.getenv("API_KEY"));
        rp.setOutputFormat(HttpClient.ResponseFormat.XML);
        rp.setIgnoreRawTexts("1");
        // Decreasing timeouts
        NetworkTimeouts timeouts = new NetworkTimeouts();
        timeouts.setConnectTimeout(2);
        timeouts.setWriteTimeout(2);
        timeouts.setReadTimeout(10);
        ApiClient client2 = new ApiClient(rp, timeouts);

        try {
            String response = client2.getRawResponse("whoisxmlapi.com");
            System.out.println(response);
        } catch (BaseException exception) {
            System.err.println(exception.getMessage());
        }
```

There is an option to provide a custom OkHttpClient object
```java
        client2.setOkHttpClient(new OkHttpClient);
```
This method could help to set up proxies. Further information available in the OkHttp [documentation](https://square.github.io/okhttp/)

