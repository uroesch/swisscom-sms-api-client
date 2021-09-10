// vim: set shiftwidth=4 tabstop=4 softtabstop=4 expandtab :
/*
 * Copyright (C) 2015 Urs Roesch (github@bun.ch)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.rufer.swisscom.sms.cli.sender;

import be.rufer.swisscom.sms.api.client.SwisscomSmsSender;
import gnu.getopt.Getopt;

/**
 * For every api-key an instance of this class can be created. The swisscom sms sender provides
 * methods to send sms messages to one or several receivers.
 */
public class SmsSender {

    private static String apiKey;
    private static String receiverNumber;
    private static String senderName   = null;
    private static String callbackUrl  = null;
    private static String senderNumber = "+41791234567";
    private static String message;

    /**
     * Constructor
     *
     * @param apiKey the API-key generated by http://developer.swisscom.com
     * @param senderNumber the number of the sender (i.e. +41791234567)
     */
    public static void main(String[] args) {
        OptionsParser(args);
        SwisscomSmsSender smsSender = new SwisscomSmsSender(apiKey, senderNumber, senderName, callbackUrl);
        smsSender.sendSms(message, receiverNumber);
    }

    private static void OptionsParser(String[] args) {
        Getopt opt = new Getopt("testprog", args, "k:n:m:N:U:S:");
        int c;
        while ((c = opt.getopt()) != -1) {
            switch(c) {
            case 'k':
                apiKey = opt.getOptarg();
                break;
            case 'n':
                receiverNumber = opt.getOptarg();
                break;
            case 'm':
                message = opt.getOptarg();
                break;
            case 'N':
                senderName = opt.getOptarg();
                break;
            case 'U':
                callbackUrl = opt.getOptarg();
                break;
            case 'S':
                senderNumber = opt.getOptarg();
                break;
            default:
                System.out.print("getopt() returned " + (char)c + "\n");
                System.exit(1);
            }
        }
    }
}

// vim: set shiftwidth=4 softtabstop=4 expandtab :
