# java-cardconnect-api-v2
CardConnect API JAVA Sample Client v2

### Usage of Example Application
[Download](https://github.com/CardConnect/java-cardconnect-api-v2/files/2220540/java-cardconnect-api-v2.one-jar.jar.zip) and unzip Example Application jar.

#### 1. Interactive mode
```bash
java -jar java-cardconnect-api-v2.one-jar.jar
====================================
CardConnect Gateway REST API Client
=====================================
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
~~~>     Main Menu
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
1. Show Example
2. Run Transaction
0. Exit
```

#### 2. Arguments mode
##### Usage:
```bash
$ java -jar java-cardconnect-api-v2.one-jar.jar -h
usage: java -jar java-cardconnect-api-v2-onejar.jar -<op> <option-value>
Options of Using CardConnect API
 -c,--credentials <arg>   credentials for transaction in '<username>:<password>' format
 -d,--data <arg>          transaction's body data
 -e,--endpoint <arg>      api endpoint
 -h,--help                show help
 -s,--sample              show sample request/response payload of transaction <type>
 -u,--url <arg>           api url
```

##### Run Transaction:
```bash
java -jar java-cardconnect-api-v2.one-jar.jar -u https://dev.cardconnect.com/cardconnect/rest -e auth -c testing:testing123 -d '{"merchid":"123456789012","account":"9627834000081451","expiry":"1199","amount":"300"}'
Response:
{
  "authcode" : "PPS375",
  "respproc" : "FNOR",
  "amount" : "3.00",
  "resptext" : "Approval",
  "cvvresp" : "N",
  "retref" : "204177150482",
  "avsresp" : "",
  "respstat" : "A",
  "respcode" : "000",
  "account" : "9627834000081451",
  "merchid" : "123456789012",
  "token" : "9627834000081451"
}
```
##### Show Example Payload:
```bash
$ java -jar java-cardconnect-api-v2.one-jar.jar -s -e auth
For detailed information visit: https://developer.cardconnect.com/cardconnect-api
-------------------------------------------------
---> Example Auth Request
-------------------------------------------------
Method: PUT
URL: https://dev.cardconnect.com/cardconnect/Auth
Headers:
                Content-Type: application/json
                Authorization: Basic
Example Auth Request:
                {
                  "merchid": "123456789012",
                  "accttype": "VISA",
                  "orderid": "AB-11-9876",
                  "account": "4111111111111111",
                  "expiry": "1218",
                  "amount": "0",
                  "currency": "USD",
                  "name": "TOM JONES",
                  "address": "123 MAIN STREET",
                  "city": "anytown",
                  "region": "NY",
                  "country": "US",
                  "postal": "55555",
                  "profile": "Y",
                  "ecomind": "E",
                  "cvv2": "123",
                  "track": null,
                  "tokenize": "Y",
                  "capture": "Y"
                }
Example Auth Response:
                {
                  "respstat": "A",
                  "account": "41XXXXXXXXXX1111",
                  "token": "9419786452781111",
                  "retref": "343005123105",
                  "amount": "111",
                  "merchid": "123456789012",
                  "respcode": "00",
                  "resptext": "Approved",
                  "avsresp": "9",
                  "cvvresp": "M",
                  "authcode": "046221",
                  "respproc": "FNOR",
                  "emv": "8A000030910A97E91E681E2734AC0012"
                }
```