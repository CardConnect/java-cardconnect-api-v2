package com.cardconnect.api.service;

import static java.lang.System.out;

/**
 * The type Example service.
 */
public class ExampleService {


    /**
     * Show auth example.
     *
     * @param endpoint the endpoint
     * @param request  the request
     * @param response the response
     * @param get      the get
     */
    private static void showExample(String endpoint,String request, String response, boolean get){
        out.println("-------------------------------------------------");
        out.println("---> Example " + endpoint + " Request");
        out.println("-------------------------------------------------");
        out.println("Method: " + (get ? "GET" : "PUT"));
        out.println("URL: https://dev.cardconnect.com/cardconnect/" + (get ? endpoint + request : endpoint));
        out.println("Headers:");
        out.println("\t\tContent-Type: application/json\n\t\t" + "Authorization: Basic");
        if(!get) {
            out.println("Example " + endpoint + " Request:");
            out.println("\t\t" + request);
        }
        out.println("Example " + endpoint + " Response:");
        out.println("\t\t" + response);
    }

    /**
     * Show delete example.
     *
     * @param endpoint the endpoint
     * @param request  the request
     * @param response the response
     */
    private static void showDeleteExample(String endpoint,String request, String response){
        out.println("-------------------------------------------------");
        out.println("---> Example " + endpoint + " Request");
        out.println("-------------------------------------------------");
        out.println("Method: DELETE");
        out.println("URL: https://dev.cardconnect.com/cardconnect/" + endpoint);
        out.println("Headers:");
        out.println("\t\tContent-Type: application/json\n\t\t" + "Authorization: Basic");
        out.println("Example " + endpoint + " Request:");
        out.println("\t\t" + request);
        out.println("Example " + endpoint + " Response:");
        out.println("\t\t" + response);
    }

    /**
     * Show example.
     *
     * @param option the option
     */
    public static void showExample(int option){
        out.println("For detailed information visit: https://developer.cardconnect.com/cardconnect-api");
        switch (option){
            case 1:
                showExample("Auth",
                        "{\n\t\t" + "  \"merchid\": \"123456789012\",\n\t\t" + "  \"accttype\": \"VISA\",\n\t\t" + "  \"orderid\": \"AB-11-9876\",\n\t\t" + "  \"account\": \"4111111111111111\",\n\t\t" + "  \"expiry\": \"1218\",\n\t\t" + "  \"amount\": \"0\",\n\t\t" + "  \"currency\": \"USD\",\n\t\t" + "  \"name\": \"TOM JONES\",\n\t\t" + "  \"address\": \"123 MAIN STREET\",\n\t\t" + "  \"city\": \"anytown\",\n\t\t" + "  \"region\": \"NY\",\n\t\t" + "  \"country\": \"US\",\n\t\t" + "  \"postal\": \"55555\",\n\t\t" + "  \"profile\": \"Y\",\n\t\t" + "  \"ecomind\": \"E\",\n\t\t" + "  \"cvv2\": \"123\",\n\t\t" + "  \"track\": null,\n\t\t" + "  \"tokenize\": \"Y\",\n\t\t" + "  \"capture\": \"Y\"\n\t\t" + "}",
                        "{\n\t\t" + "  \"respstat\": \"A\",\n\t\t" + "  \"account\": \"41XXXXXXXXXX1111\",\n\t\t" + "  \"token\": \"9419786452781111\",\n\t\t" + "  \"retref\": \"343005123105\",\n\t\t" + "  \"amount\": \"111\",\n\t\t" + "  \"merchid\": \"123456789012\",\n\t\t" + "  \"respcode\": \"00\",\n\t\t" + "  \"resptext\": \"Approved\",\n\t\t" + "  \"avsresp\": \"9\",\n\t\t" + "  \"cvvresp\": \"M\",\n\t\t" + "  \"authcode\": \"046221\",\n\t\t" + "  \"respproc\": \"FNOR\",\n\t\t" + "  \"emv\": \"8A000030910A97E91E681E2734AC0012\"\n\t\t" + "}", false);
                break;
            case 2:
                showExample("capture",
                        "{\n\t\t" + "    \"retref\":\"227082251218\",\n\t\t" + "    \"merchid\":\"496160876666\"\n\t\t" + "}",
                        "{\n\t\t" + "    \"respproc\": \"FNOR\",\n\t\t" + "    \"amount\": \"0.01\",\n\t\t" + "    \"resptext\": \"Approval\",\n\t\t" + "    \"setlstat\": \"Queued for Capture\",\n\t\t" + "    \"commcard\": \" C \",\n\t\t" + "    \"retref\": \"227082251218\",\n\t\t" + "    \"respstat\": \"A\",\n\t\t" + "    \"respcode\": \"00\",\n\t\t" + "    \"batchid\": \"1900941262\",\n\t\t" + "    \"account\": \"9441149619831111\",\n\t\t" + "    \"merchid\": \"123456789012\",\n\t\t" + "    \"token\": \"9441149619831111\"\n\t\t" + "}",false);
                break;
            case 3:
                showExample("void",
                        "{\n\t\t" + "  \"retref\": \"288013185633\",\n\t\t" + "  \"merchid\": \"123456789012\",\n\t\t" + "}",
                        "{\n\t\t" + "\t\"merchid\": \"123456789012\",\n\t\t" + "\t\"amount\": \"0.01\",\n\t\t" + "\t\"currency\": \"USD\",\n\t\t" + "\t\"retref\": \"227082251218\",\n\t\t" + "\t\"authcode\": \"REVERS\",\n\t\t" + "\t\"respcode\": \"00\",\n\t\t" + "\t\"respproc\": \"FNOR\",\n\t\t" + "    \"respstat\": \"A\",\n\t\t" + "    \"resptext\": \"Approval\", \n\t\t" + "}",false);
                break;
            case 4:
                showExample("refund",
                        "{\n\t\t" + "  \"retref\": \"288009185241\",\n\t\t" + "  \"merchid\": \"123456789012\",\n\t\t" + "  \"amount\": \"59.60\"\n\t\t" + "}",
                        "{\n\t\t" + "  \"amount\": \"59.60\",\n\t\t" + "  \"resptext\": \"Approval\",\n\t\t" + "  \"authcode\": \"REFUND\",\n\t\t" + "  \"respcode\": \"00\",\n\t\t" + "  \"retref\": \"288010185242\",\n\t\t" + "  \"merchid\": \"000000927996\",\n\t\t" + "  \"cvvresp\": \"M\",\n\t\t" + "  \"account\": \"41XXXXXXXXXX4113\",\n\t\t" + "  \"avsresp\": \"Z\",\n\t\t" + "  \"respproc\": \"PPS\",\n\t\t" + "  \"respstat\": \"A\"\n\t\t" + "}",false);
                break;
            case 5:
                showExample("inquire",
                        "/<merchid>/<retref>",
                        "{\n\t\t" + "    \"amount\": \"0.01\",\n\t\t" + "    \"resptext\": \"Approval\",\n\t\t" + "    \"setlstat\": \"Queued for Capture\",\n\t\t" + "    \"capturedate\": \"20170815142832\",\n\t\t" + "    \"respcode\": \"00\",\n\t\t" + "    \"batchid\": \"1900941262\",\n\t\t" + "    \"merchid\": \"496160873888\",\n\t\t" + "    \"token\": \"9441149619831111\",\n\t\t" + "    \"respproc\": \"FNOR\",\n\t\t" + "    \"authdate\": \"20170815\",\n\t\t" + "    \"lastfour\": \"1111\",\n\t\t" + "    \"name\": \"CC TEST\",\n\t\t" + "    \"currency\": \"USD\",\n\t\t" + "    \"retref\": \"227145252112\",\n\t\t" + "    \"respstat\": \"A\",\n\t\t" + "    \"account\": \"9441149619831111\"\n\t\t" + "}",true);
                break;
            case 6:
                showExample("settlestat",
                        "?merchid=<merchid>&date=<MMYY>   OR   merchid=<merchid>&batchid=<batchid>",
                        "[\n\t\t" + "  {\n\t\t" + "\t \"respproc\": \"FNOR\",\n\t\t" + "     \"hostbatch\": \"1900941820\",\n\t\t" + "     \"refundtotal\": \"0.00\",\n\t\t" + "     \"batchid\": \"1900941259\",\n\t\t" + "     \"chargetotal\": \"253.43\",\n\t\t" + "     \"hoststat\": \"GB\",\n\t\t" + "     \"merchid\": \"123456789012\",\t\t\n\t\t" + "\t\t\"txns\": [\n\t\t" + "         {\n\t\t" + "             \"setlamount\": \"53.43\",\n\t\t" + "             \"setlstat\": \"Y\",\n\t\t" + "             \"salesdoc\": \"MPIP10001589\",\n\t\t" + "             \"retref\": \"200081244288\"\n\t\t" + "         },\n\t\t" + "         {\n\t\t" + "             \"setlamount\": \"200.00\",\n\t\t" + "             \"setlstat\": \"Y\",\n\t\t" + "             \"salesdoc\": \"423625\",\n\t\t" + "             \"retref\": \"224604245655\"\n\t\t" + "            }\n\t\t" + "  }\n\t\t" + "]",true);
                break;
            case 7:
                showExample("funding",
                        "?merchid=<merchid>&date=<MMDD>",
                        "{\n\t\t" + "   \"txns\":    [\n\t\t" + "            {\n\t\t" + "         \"cardtype\": \"Credit\",\n\t\t" + "         \"status\": \"Processed\",\n\t\t" + "         \"retref\": null,\n\t\t" + "         \"interchangeunitfee\": null,\n\t\t" + "         \"cardnumber\": \"44XXXXXXXXXX4329\",\n\t\t" + "         \"type\": \"Sale\",\n\t\t" + "         \"fundingid\": 13899,\n\t\t" + "         \"interchangepercentfee\": null,\n\t\t" + "         \"date\": \"2014-03-31\",\n\t\t" + "         \"currency\": \"USD\",\n\t\t" + "         \"cardbrand\": \"Visa\",\n\t\t" + "         \"amount\": 995,\n\t\t" + "         \"fundingtxnid\": 25\n\t\t" + "      },\n\t\t" + "            {\n\t\t" + "         \"cardtype\": \"Credit\",\n\t\t" + "         \"status\": \"Processed\",\n\t\t" + "         \"retref\": null,\n\t\t" + "         \"interchangeunitfee\": null,\n\t\t" + "         \"cardnumber\": \"37XXXXXXXXXX003\",\n\t\t" + "         \"type\": \"Sale\",\n\t\t" + "         \"fundingid\": 13900,\n\t\t" + "         \"interchangepercentfee\": null,\n\t\t" + "         \"date\": \"2014-03-31\",\n\t\t" + "         \"currency\": \"USD\",\n\t\t" + "         \"cardbrand\": \"American Express\",\n\t\t" + "         \"amount\": 1495,\n\t\t" + "         \"fundingtxnid\": 25\n\t\t" + "      },\n\t\t" + "            {\n\t\t" + "         \"cardtype\": \"Credit\",\n\t\t" + "         \"status\": \"Processed\",\n\t\t" + "         \"retref\": null,\n\t\t" + "         \"interchangeunitfee\": null,\n\t\t" + "         \"cardnumber\": \"52XXXXXXXXXX7030\",\n\t\t" + "         \"type\": \"Sale\",\n\t\t" + "         \"fundingid\": 13907,\n\t\t" + "         \"interchangepercentfee\": null,\n\t\t" + "         \"date\": \"2014-03-31\",\n\t\t" + "         \"currency\": \"USD\",\n\t\t" + "         \"cardbrand\": \"Mastercard\",\n\t\t" + "         \"amount\": 1145,\n\t\t" + "         \"fundingtxnid\": 25\n\t\t" + "      },\n\t\t" + "            {\n\t\t" + "         \"cardtype\": \"Credit\",\n\t\t" + "         \"status\": \"Processed\",\n\t\t" + "         \"retref\": null,\n\t\t" + "         \"interchangeunitfee\": null,\n\t\t" + "         \"cardnumber\": \"45XXXXXXXXXX1081\",\n\t\t" + "         \"type\": \"Sale\",\n\t\t" + "         \"fundingid\": 13909,\n\t\t" + "         \"interchangepercentfee\": null,\n\t\t" + "         \"date\": \"2014-03-31\",\n\t\t" + "         \"currency\": \"USD\",\n\t\t" + "         \"cardbrand\": \"Visa\",\n\t\t" + "         \"amount\": 1495,\n\t\t" + "         \"fundingtxnid\": 25\n\t\t" + "      }\n\t\t" + "   ],\n\t\t" + "   \"fundingdate\": \"2014-03-31\",\n\t\t" + "   \"merchid\": \"123456789012\",\n\t\t" + "   \"datechanged\": null,\n\t\t" + "   \"fundings\": [   {\n\t\t" + "      \"totalfunding\": 3635,\n\t\t" + "      \"ddanumber\": \"5XXXXX1756\",\n\t\t" + "      \"datechanged\": \"2014-04-11\",\n\t\t" + "      \"interchangefee\": 0,\n\t\t" + "      \"fundingid\": 405,\n\t\t" + "      \"fundingmasterid\": 25,\n\t\t" + "      \"servicecharge\": 0,\n\t\t" + "      \"currency\": \"USD\",\n\t\t" + "      \"fee\": 0,\n\t\t" + "      \"dateadded\": \"2014-04-11\",\n\t\t" + "      \"adjustment\": 0,\n\t\t" + "      \"netsales\": 5130,\n\t\t" + "      \"thirdparty\": -1495,\n\t\t" + "      \"otheradjustment\": 0,\n\t\t" + "      \"reversal\": 0,\n\t\t" + "      \"abanumber\": \"054000030\"\n\t\t" + "   }],\n\t\t" + "   \"fundingmasterid\": 25,\n\t\t" + "   \"adjustments\": [   {\n\t\t" + "      \"amount\": -1495,\n\t\t" + "      \"category\": \"THIRD PARTY\",\n\t\t" + "      \"dateadded\": \"2014-04-11\",\n\t\t" + "      \"description\": \"THIRD PARTY ADJUSTMENTS\",\n\t\t" + "      \"merchid\": \"496202840880\",\n\t\t" + "      \"datechanged\": \"2014-04-11\",\n\t\t" + "      \"type\": \"THIRD PARTY ADJUSTMENTS\",\n\t\t" + "      \"fundingmasterid\": 25,\n\t\t" + "      \"fundingadjustmentid\": 1081,\n\t\t" + "      \"currency\": \"USD\"\n\t\t" + "   }]\n\t\t" + "}",true);
                break;
            case 8:
                showExample("profile",
                        "{\n\t\t" + "  \"region\": \"AK\",\n\t\t" + "  \"phone\": \"7778789999\",\n\t\t" + "  \"accttype\": \"VISA\",\n\t\t" + "  \"postal\": \"19090\",\n\t\t" + "  \"ssnl4\": \"3655\",\n\t\t" + "  \"expiry\": \"0214\",\n\t\t" + "  \"city\": \"ANYTOWN\",\n\t\t" + "  \"country\": \"US\",\n\t\t" + "  \"address\": \"123 MAIN STREET\",\n\t\t" + "  \"merchid\": \"496400000840\",\n\t\t" + "  \"name\": \"TOM JONES\",\n\t\t" + "  \"account\": \"4444333322221111\",\n\t\t" + "  \"license\": \"123451254\",\n\t\t" + "}",
                        "{\n\t\t" + "   \"country\": \"US\",\n\t\t" + "   \"address\": \"123 MAIN STREET\",\n\t\t" + "   \"resptext\": \"Profile Saved\",\n\t\t" + "   \"city\": \"ANYTOWN\",\n\t\t" + "   \"acctid\": \"1\",\n\t\t" + "   \"respcode\": \"09\",\n\t\t" + "   \"defaultacct\": \"Y\",\n\t\t" + "   \"accttype\": \"VISA\",\n\t\t" + "   \"token\": \"9443270804721111\",\n\t\t" + "   \"license\": \"123451254\",\n\t\t" + "   \"respproc\": \"PPS\",\n\t\t" + "   \"phone\": \"7778789999\",\n\t\t" + "   \"profileid\": \"12189499957389276999\",\n\t\t" + "   \"name\": \"TOM JONES\",\n\t\t" + "   \"auoptout\": \"N\",\n\t\t" + "   \"postal\": \"19090\",\n\t\t" + "   \"expiry\": \"0214\",\n\t\t" + "   \"region\": \"AK\",\n\t\t" + "   \"ssnl4\": \"3655\",\n\t\t" + "   \"respstat\": \"A\"\n\t\t" + "}",false);
                break;
            case 9:
                showExample("profile",
                        "/<profileid>/<merchid>/<acctid>",
                        "[\n\t\t" + "  {\n\t\t" + "    \"region\": \"AK\",\n\t\t" + "    \"postal\": \"19090\",\n\t\t" + "    \"address\": \"123 MAIN STREET\",\n\t\t" + "    \"accttype\": \"VISA\",\n\t\t" + "    \"token\": \"9440670166031111\",\n\t\t" + "    \"name\": \"TOM JONES\",\n\t\t" + "    \"license\": \"123451254\",\n\t\t" + "    \"country\": \"US\",\n\t\t" + "    \"city\": \"ANYTOWN\",\n\t\t" + "    \"expiry\": \"0214\",\n\t\t" + "    \"profileid\": \"12345678901234567890\",\n\t\t" + "    \"acctid\": \"1\"\n\t\t" + "  }\n\t\t" + "]",true);
                break;
            case 10:
                showDeleteExample("profile",
                        "/<profileid>/<merchid>/<acctid>",
                        "{\n\t\t" + "  \"resptext\": \"Profile Deleted\",\n\t\t" + "  \"respcode\": \"08\",\n\t\t" + "  \"profileid\": \"12345678901234567890\",\n\t\t" + "  \"acctid\": \"1\",\n\t\t" + "  \"respproc\": \"PPS\",\n\t\t" + "  \"respstat\": \"A\"\n\t\t" + "}");
                break;
            case 11:
                showExample("sigcap",
                        "{\n\t\t" + "  \"merchid\": \"020594000000\",\n\t\t" + "  \"retref\": \"343005123105\",\n\t\t" + "  \"signature\": \"\"\n\t\t" + "}",
                        "{\n\t\t" + "  \"resptext\":\"signature stored\",\n\t\t" + "  \"respcode\":\"02\",\n\t\t" + "  \"retref\":\"320121156263\",\n\t\t" + "  \"merchid\":\"496400000840\"\n\t\t" + "}",false);
                break;
            case 12:
                showExample("openbatch",
                        "/<merchid>/<batchsource>",
                        "{\n\t\t" + "\t\"batchid\":\"2628\"    \n\t\t" + "\t\"respcode\":\"success\"\n\t\t" + "\t\n\t\t" + "}",true);
                break;
            case 13:
                showExample("closebatch",
                        "/<merchid>/<batchid>",
                        "{\n\t\t" + "    \"batchid\": \"2568\"\n\t\t" + "\t\"respcode\":\"success\"\n\t\t" + "}",true);
                break;
            case 14:
                showExample("bin",
                        "/<merchid>/<token>",
                        "{\n\t\t\"country\": \"USA\",\n\t\t\"product\": \"M\",\n\t\t\"purchase\":false,\n\t\t\"flags\": \"  N B\",\n\t\t\"prepaid\":false,\n\t\t\"issuer\": \"CHASE BANK USA, N.A.\",\n\t\t\"network\": \"\",\n\t\t\"carduse\": \"X \",\n\t\t\"cardusestring\": \"True credit\",\n\t\t\"gsa\":false,\n\t\t\"corporate\":true,\n\t\t\"fsa\":false,\n\t\t\"subtype\": \"World MasterCard for Business Card\",\n\t\t\"binlen\":6,\n\t\t\"cardtype\": \"MWB\",\n\t\t\"binlo\": \"558821XXX\",\n\t\t\"binhi\": \"558821XXX\"\n\t\t}",true);
                break;
        }
    }
}
