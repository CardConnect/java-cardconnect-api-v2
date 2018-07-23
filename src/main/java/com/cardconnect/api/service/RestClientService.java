package com.cardconnect.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class RestClientService {

    private String rootURL, userPass;
    private enum OPERATIONS {GET, PUT, POST, DELETE}
    private final String AUTH_ENDPOINT = "auth";
    private final String CAPTURE_ENDPOINT = "capture";
    private final String VOID_ENDPOINT = "void";
    private final String REFUND_ENDPOINT = "refund";
    private final String INQUIRE_ENDPOINT = "inquire";
    private final String SETTLE_STAT_ENDPOINT = "setlstat";
    private final String FUNDNING_ENDPOINT = "funding";
    private final String PROFILE_ENDPOINT = "profile";
    private final String SIG_CAP_ENDPOINT = "sigcap";
    private final String OPEN_BATCH_ENDPOINT = "openbatch";
    private final String CLOSE_BATCH_ENDPOINT = "closebatch";
    private final String BIN_ENDPOINT = "bin";


    public RestClientService(String rootURL,String userPass){
        this.rootURL = StringUtils.stripEnd(rootURL,"/") + "/";
        this.userPass = userPass;
    }

    /**
     * Authorize trasaction
     * @param request JSONObject representing an Authorization transaction request
     * @return JSONObject representing an Authorization transaction response
     */
    public JSONObject authorizeTransaction(JSONObject request) {
        return (JSONObject)send(AUTH_ENDPOINT, OPERATIONS.PUT, request);
    }


    /**
     * Capture transaction
     * @param request JSONObject representing a Capture transaction request
     * @return JSONObject representing a Capture transaction response
     */
    public JSONObject captureTransaction(JSONObject request) {
        return (JSONObject)send(CAPTURE_ENDPOINT, OPERATIONS.PUT, request);
    }


    /**
     * Void transaction
     * @param request JSONObject representing a Void transaction request
     * @return JSONObject representing a Void transaction response
     */
    public JSONObject voidTransaction(JSONObject request) {
        return (JSONObject)send(VOID_ENDPOINT, OPERATIONS.PUT, request);
    }


    /**
     * Refund Transaction
     * @param request JSONObject representing a Refund transaction request
     * @return JSONObject represeting a Refund transactino response
     */
    public JSONObject refundTransaction(JSONObject request) {
        return (JSONObject)send(REFUND_ENDPOINT, OPERATIONS.PUT, request);
    }


    /**
     * Inquire Transaction
     * @param merchid Merchant ID
     * @param retref RetRef to inquire
     * @return JSONObject representing the request transaction
     * @throws IllegalArgumentException
     */
    public JSONObject inquireTransaction(String merchid, String retref) throws IllegalArgumentException {
        if (isEmpty(merchid)) throw new IllegalArgumentException("Missing required parameter: merchid");
        if (isEmpty(retref)) throw new IllegalArgumentException("Missing required parameter: retref");

        String url = INQUIRE_ENDPOINT + "/" + retref + "/" + merchid;
        return (JSONObject)send(url, OPERATIONS.GET, null);
    }

    /**
     * Gets the settlement status for transactions
     * @param merchid Mechant ID
     * @param date Date in MMDD format
     * @return JSONArray of JSONObjects representing Settlement batches, each batch containing a JSONArray of
     * JSONObjects representing the settlement status of each transaction
     * @throws IllegalArgumentException
     */
    public JSONArray settlementStatus(String merchid, String date) throws IllegalArgumentException {
        if ((!isEmpty(merchid) && isEmpty(date)) || (isEmpty(merchid) && !isEmpty(date)))
            throw new IllegalArgumentException("Both merchid and date parameters are required, or neither");

        String url;
        if (isEmpty(merchid) || isEmpty(date)) {
            url = SETTLE_STAT_ENDPOINT;
        } else {
            url = SETTLE_STAT_ENDPOINT + "?merchid=" + merchid + "&date=" + date;
        }

        return (JSONArray)send(url, OPERATIONS.GET, null);
    }

    /**
     * Gets the Funding status for transactions
     * @param merchid Mechant ID
     * @param date Date in MMDD format
     * @return three JSON arrays (txns, fundings, and adjustments)
     * along with other fields that help identify information about the funding.
     * @throws IllegalArgumentException
     */
    public JSONArray fundingStatus(String merchid, String date) throws IllegalArgumentException {
        if ((!isEmpty(merchid) && isEmpty(date)) || (isEmpty(merchid) && !isEmpty(date)))
            throw new IllegalArgumentException("Both merchid and date parameters are required, or neither");

        String url;
        if (isEmpty(merchid) || isEmpty(date)) {
            url = FUNDNING_ENDPOINT;
        } else {
            url = FUNDNING_ENDPOINT + "?merchid=" + merchid + "&date=" + date;
        }

        return (JSONArray)send(url, OPERATIONS.GET, null);
    }


    /**
     * Retrieves a profile
     * @param profileid ProfileID to retrieve
     * @param accountid Optional account id within profile
     * @param merchid Merchant ID
     * @return JSONArray of JSONObjects each represeting a profile
     * @throws IllegalArgumentException
     */
    public JSONArray profileGet(String profileid, String accountid, String merchid) throws IllegalArgumentException {
        if (isEmpty(profileid)) throw new IllegalArgumentException("Missing required parameter: profileid");
        if (isEmpty(merchid)) throw new IllegalArgumentException("Missing required parameter: merchid");
        if (accountid == null) accountid = "";

        String url = PROFILE_ENDPOINT + "/" + profileid + "/" + accountid + "/" + merchid;
        return (JSONArray)send(url, OPERATIONS.GET, null);
    }


    /**
     * Deletes a profile
     * @param profileid ProfileID to delete
     * @param accountid Optional accountID within the profile
     * @param merchid Merchant ID
     * @return
     * @throws IllegalArgumentException
     */
    public JSONObject profileDelete(String profileid, String accountid, String merchid) throws IllegalArgumentException {
        if (isEmpty(profileid)) throw new IllegalArgumentException("Missing required parameter: profileid");
        if (isEmpty(merchid)) throw new IllegalArgumentException("Missing required parameter: merchid");
        if (accountid == null) accountid = "";

        String url = PROFILE_ENDPOINT + "/" + profileid + "/" + accountid + "/" + merchid;
        return (JSONObject)send(url, OPERATIONS.DELETE, null);
    }


    /**
     * Creates a new profile
     * @param request JSONObject representing the Profile creation request
     * @return JSONObejct representing the newly created profile
     * @throws IllegalArgumentException
     */
    public JSONObject profileCreate(JSONObject request) throws IllegalArgumentException {
        return (JSONObject)send(PROFILE_ENDPOINT, OPERATIONS.PUT, request);
    }


    /**
     * Updates an existing profile
     * @param request JSONObject representing the Profile Update request
     * @return JSONObject representing the updated Profile
     */
    public JSONObject profileUpdate(JSONObject request) {
        return profileCreate(request);
    }

    /**
     * This signature capture service augments an existing authorization record
     * with the provided signature data.
     *
     * @param request the request
     * @return the json object
     * @throws IllegalArgumentException the illegal argument exception
     */
    public JSONObject signatureCapture(JSONObject request) throws IllegalArgumentException {
        return (JSONObject)send(SIG_CAP_ENDPOINT, OPERATIONS.PUT, request);
    }

    /**
     * The openBatch service opens a new batch associated the supplied merchid.
     * The batchsource value is intended to allow merchants to supply a client
     * cross-merchid batch identifier to logically link multiple CardConnect batches together.
     *
     * @param merchid
     * @param batchSource
     * @return
     * @throws IllegalArgumentException
     */
    public JSONObject openBatch(String merchid, String batchSource) throws IllegalArgumentException {
        if (isEmpty(merchid)) throw new IllegalArgumentException("Missing required parameter: merchid");
        String url = OPEN_BATCH_ENDPOINT + "/" + merchid + (isEmpty(batchSource) ? "" : "/" + batchSource);
        return (JSONObject)send(url, OPERATIONS.GET, null);
    }

    /**
     * The closeBatch service attempts to close the batch identified by the merchid and batchid.
     * Optionally provide the batchid to attempt to close a specific batch.
     * If no batchid is supplied, the open batch with the lowest batchid is closed.
     *
     * @param merchid
     * @param batchId
     * @return
     * @throws IllegalArgumentException
     */
    public JSONObject closeBatch(String merchid, String batchId) throws IllegalArgumentException {
        if (isEmpty(merchid)) throw new IllegalArgumentException("Missing required parameter: merchid");
        String url = CLOSE_BATCH_ENDPOINT + "/" + merchid + (isEmpty(batchId) ? "" : "/" + batchId);
        return (JSONObject) send(url, OPERATIONS.GET, null);
    }

    /**
     * The Bin service allows you to use a CardConnect token to determine what type of payment card is being used.
     * The first six (6) digits of a credit card are known as the Bank Identifier Number (BIN),also know as a Issuer
     * Identification Number (IIN). The bin service provides this detail using a merchant’s ID and the CardConnect token.
     * This service currently supports Visa, Mastercard, and Discover card brands.
     *
     * @param merchid
     * @param token
     * @return
     * @throws IllegalArgumentException
     */
    public JSONObject bin(String merchid, String token) throws IllegalArgumentException {
        if (isEmpty(merchid)) throw new IllegalArgumentException("Missing required parameter: merchid");
        if (isEmpty(token)) throw new IllegalArgumentException("Missing required parameter: token");
        String url = BIN_ENDPOINT + "/" + merchid + "/" + token;
        return (JSONObject) send(url, OPERATIONS.GET, null);
    }

    /**
     * Internal method to send an HTTP REST request
     * @param endpoint
     * @param operation
     * @param request
     * @return
     */
    private Object send(String endpoint, OPERATIONS operation, JSONObject request) {
        Object response = null;
        String resp = null;
        String url = this.rootURL + endpoint;
        CloseableHttpClient httpclient = null;

        try {
            httpclient = HttpClients.createDefault();
            HttpClientContext context = HttpClientContext.create();

            Header[] headers = getHeaders();

            StringEntity entity = null;
            if (request != null) {
                entity = new StringEntity(request.toJSONString(), "UTF-8");
                entity.setContentType("application/json");
            }

            ResponseHandler<String> responseHandler = getResponseHandler();

            // Send request over HTTP
            switch (operation) {
                case PUT:    {
                    HttpPut put = new HttpPut(url);
                    put.setHeaders(headers);
                    put.setEntity(entity);
                    resp = httpclient.execute(put, responseHandler, context);
                    break;
                }
                case POST:   {
                    HttpPost post = new HttpPost(url);
                    post.setHeaders(headers);
                    post.setEntity(entity);
                    resp = httpclient.execute(post, responseHandler, context);
                    break;
                }
                case GET:    {
                    HttpGet get = new HttpGet(url);
                    get.setHeaders(headers);
                    resp = httpclient.execute(get, responseHandler, context);
                    break;
                }
                case DELETE: {
                    HttpDelete delete = new HttpDelete(url);
                    delete.setHeaders(headers);
                    resp = httpclient.execute(delete, responseHandler, context);
                    break;
                }
            }
            if (resp != null) {
                response = JSONValue.parse(resp);
            }
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        } finally {
            try { if(httpclient != null){
                httpclient.close();
            }
            } catch (Exception ex) { }
        }
        return response;
    }

    /**
     * Creates a response handler for the HTTP responses
     * @return
     */
    private ResponseHandler<String> getResponseHandler() {
        return new ResponseHandler<String>() {
            public String handleResponse(final HttpResponse response) throws IOException {
                StatusLine statusLine = response.getStatusLine();
                HttpEntity entity = response.getEntity();
                if (statusLine.getStatusCode() >= 300) {
                    throw new HttpResponseException(
                            statusLine.getStatusCode(),
                            statusLine.getReasonPhrase());
                }
                if (entity == null) {
                    throw new ClientProtocolException("Response contains no content");
                }

                InputStream inputStream = entity.getContent();
                StringBuilder sb = new StringBuilder();
                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream), 2000);
                for (String line = r.readLine(); line != null; line = r.readLine()) {
                    sb.append(line);
                }
                inputStream.close();
                return sb.toString();
            }
        };
    }

    /**
     * Generates headers for the HTTP calls - for Authorization and JSON
     * @return
     */
    private Header[] getHeaders() {
        Header[] headers = new BasicHeader[3];
        headers[0] = new BasicHeader("Authorization", "Basic " + DatatypeConverter.printBase64Binary(userPass.getBytes()));
        headers[1] = new BasicHeader("Accept", "application/json");
        headers[2] = new BasicHeader("Content-Type", "application/json");
        return headers;
    }

    /**
     * Run transaction.
     *
     * @param option the option
     * @param string the string
     */
    public void runTransaction(int option, String string){
        if(isEmpty(string)) {
            throw new IllegalArgumentException(" API Data cannot be empty");
        }
        JSONObject jsonObject=null;
        String data1 = null, data2 = null, data3= null;
        String [] dataArray;
        try{
            jsonObject = (JSONObject)JSONValue.parse(string);
        }catch (Exception e){
            if(!string.contains(",")){
                throw new IllegalArgumentException("can not parse data as JSON or url params, please try again!");
            }else{
                dataArray = string.split(",");
                data1 = dataArray[0];
                if(dataArray.length >= 2){
                    data2 = dataArray[1];
                }
                if(dataArray.length >= 3){
                    data3 = dataArray[2];
                }
            }
        }
        runTransaction(option,jsonObject,data1,data2,data3);
    }
    /**
     * Run transaction.
     *
     * @param option     the option
     * @param jsonObject the json object
     * @param data1      the data 1
     * @param data2      the data 2
     * @param data3      the data 3
     */
    public void runTransaction(int option, JSONObject jsonObject,String data1, String data2, String data3){
        Object response = null;
        switch (option){
            case 1: case 2:
                response = authorizeTransaction(jsonObject);
                break;
            case 3:
                response = voidTransaction(jsonObject);
                break;
            case 4:
                response = refundTransaction(jsonObject);
                break;
            case 5:
                response = inquireTransaction(data1,data1);
                break;
            case 6:
                response = settlementStatus(data1,data2);
                break;
            case 7:
                response = fundingStatus(data1,data2);
                break;
            case 8:
                response = profileGet(data1,data2,data3);
                break;
            case 9:
                response = profileCreate(jsonObject);
                break;
            case 10:
                response =  profileDelete(data1,data2,data3);
                break;
            case 11:
                response = signatureCapture(jsonObject);
                break;
            case 12:
                response = openBatch(data1,data2);
                break;
            case 13:
                response = closeBatch(data1,data2);
                break;
            case 14:
                response = bin(data1,data2);
                break;
        }

        System.out.println("Response:");
        if(response != null){
            ObjectMapper mapper = new ObjectMapper();
            try {
                response = mapper.readValue(response.toString(), Object.class);
                System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(response);
            }
        }else {
            System.err.println("NULL Response!");
        }
        System.out.println();
    }
}
