# Swisscom SMS-API-Client
[![Build Status](https://travis-ci.org/rufer7/swisscom-sms-api-client.svg)](https://travis-ci.org/rufer7/swisscom-sms-api-client)
[![Coverage Status](https://coveralls.io/repos/rufer7/swisscom-sms-api-client/badge.svg?branch=master)](https://coveralls.io/r/rufer7/swisscom-sms-api-client?branch=master)
[![License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://github.com/rufer7/swisscom-sms-api-client/blob/master/LICENSE)
[![Releases](https://img.shields.io/github/release/rufer7/swisscom-sms-api-client.svg)](https://github.com/rufer7/swisscom-sms-api-client/releases)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/be.rufer.swisscom.sms/api-client/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/be.rufer.swisscom.sms/api-client)

[marketplace]: https://digital.swisscom.com/

A Java library for easy use of the Swisscom SMS API in Java.
The SMS-API of Swisscom is published and documented at the 
[Swisscom Smart Messaging](https://docs.developer.swisscom.com/api-service-offerings/smart-messaging.html)
page.


## How to use

To use the API with this client you have to register at the 
[Swisscom Digital Marketplace](![marketplace]) and create an API-Key.

### Maven dependency

    <dependency>
        <groupId>be.rufer.swisscom.sms</groupId>
        <artifactId>api-client</artifactId>
        <version>1.9.1</version>
    </dependency>


### Example code snippet

The following code snippet shows how to use the Swisscom API-Client


#### Basic usage

The most basic usage required 4 parameters the API Key, the sender's number,
a message text and the recipient's number.

```java
import be.rufer.swisscom.sms.api.client.SwisscomSmsSender;
import be.rufer.swisscom.sms.api.domain.CommunicationWrapper;

public class SmsSenderExample
{
    public static void main( String[] args )
    {
      // SENDER_NUMBER and RECEIVER_NUMBER in the following format: +41791234567
      SwisscomSmsSender smsSender = new SwisscomSmsSender("API_KEY", "SENDER_NUMBER");
      CommunicationWrapper response = smsSender.sendSms("MESSAGE", "RECEIVER_NUMBER");
    }
}
```


#### Partner mode usage

Partner mode only works if Swisscom has contractually agreed to it. 
In partner mode the name of the sender can be overriden. 

```java
import be.rufer.swisscom.sms.api.client.SwisscomSmsSender;
import be.rufer.swisscom.sms.api.domain.CommunicationWrapper;

public class SmsSenderExample
{
    public static void main( String[] args )
    {
      // SENDER_NUMBER and RECEIVER_NUMBER in the following format: +41791234567
      SwisscomSmsSender smsSender = new SwisscomSmsSender("API_KEY", "SENDER_NUMBER", "SENDER_NAME");
      CommunicationWrapper response = smsSender.sendSms("MESSAGE", "RECEIVER_NUMBER");
    }
}
```


#### With callback URL

If status updates are required about the SMS state (e.g. SMS was received), 
are required one can define a `callBackUrl` parameter. 
Updates are sent as `HTTP PUT` requests the provided URL.

```java
import be.rufer.swisscom.sms.api.client.SwisscomSmsSender;
import be.rufer.swisscom.sms.api.domain.CommunicationWrapper;

public class SmsSenderExample
{
    public static void main( String[] args )
    {
      // SENDER_NUMBER and RECEIVER_NUMBER in the following format: +41791234567
      SwisscomSmsSender smsSender = new SwisscomSmsSender("API_KEY", "SENDER_NUMBER", null, "https://example.com/callback");
      CommunicationWrapper response = smsSender.sendSms("MESSAGE", "RECEIVER_NUMBER");
    }
}
```

## Release

For releasing consider the [maven central manual](http://central.sonatype.org/pages/apache-maven.html)

The SMS-API of Swisscom is published and documented at the [Swisscom Developer Portal](https://developer.swisscom.com/).


## Todo

* JSON parser for the error message being sent back.
