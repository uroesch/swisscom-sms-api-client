# Swisscom SMS-API-Client
[![Build Status](https://travis-ci.org/rufer7/swisscom-sms-api-client.svg)](https://travis-ci.org/rufer7/swisscom-sms-api-client)
[![Coverage Status](https://coveralls.io/repos/rufer7/swisscom-sms-api-client/badge.svg?branch=master)](https://coveralls.io/r/rufer7/swisscom-sms-api-client?branch=master)
[![License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://github.com/rufer7/swisscom-sms-api-client/blob/master/LICENSE)
[![Releases](https://img.shields.io/github/release/rufer7/swisscom-sms-api-client.svg)](https://github.com/rufer7/swisscom-sms-api-client/releases)

A Java library for easy use of the Swisscom SMS API in Java.
The SMS-API of Swisscom is published and documented at the [Swisscom Developer Portal](https://developer.swisscom.com/).


## How to use

To use the API with this client you have to register at the [Swisscom developer portal](https://developer.swisscom.com/) and create an API-Key.

The following code snippet shows how to use the Swisscom API-Client


```java
import SwisscomSmsSender;

public class SmsSenderExample
{
    public static void main( String[] args )
    {
      // SENDER_NUMBER and RECEIVER_NUMBER1 in the following format: +41791234567
      SwisscomSmsSender smsSender = new SwisscomSmsSender("API_KEY", "SENDER_NUMBER");
      CommunicationWrapper response = smsSender.sendSms("MESSAGE", "RECEIVER_NUMBER1");
      // Sending a SMS to several receivers
      CommunicationWrapper response2 = smsSender.sendSms("MESSAGE", "RECEIVER_NUMBER1", "RECEIVER_NUMBER2");
    }
}
```
