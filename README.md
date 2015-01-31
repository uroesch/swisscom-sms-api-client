# Swisscom SMS-API-Client
[![Build Status](https://travis-ci.org/rufer7/swisscom-sms-api-client.svg)](https://travis-ci.org/rufer7/swisscom-sms-api-client) [![Coverage Status](https://coveralls.io/repos/rufer7/swisscom-sms-api-client/badge.svg?branch=master)](https://coveralls.io/r/rufer7/swisscom-sms-api-client?branch=master)

A Java library for easy use of the Swisscom SMS API in Java.
The SMS-API of Swisscom is published and documented at the [Swisscom Developer Portal](https://developer.swisscom.com/).


## How to use

To use the API with this client you have to register at the [Swisscom developer portal](https://developer.swisscom.com/) and create an API-Key.

```java
public class SmsSenderExample
{
    public static void main( String[] args )
    {
      // SENDER_NUMBER and RECEIVER_NUMBER1 in the following format: +41791234567
      SwisscomSmsSender smsSender = new SwisscomSmsSender("API_KEY", "SENDER_NUMBER");
      smsSender.sendSms("MESSAGE", "RECEIVER_NUMBER1");
    }
}
```