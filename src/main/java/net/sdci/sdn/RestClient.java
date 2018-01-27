package net.sdci.sdn;

import org.json.simple.JSONObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;

public class RestClient {

    private String baseUrl;

    public RestClient(String url) {
        this.baseUrl = url;
    }

    public String get(String url) {
        String output = "";
        try {
            Client client = Client.create();
            WebResource webResource = client.resource(baseUrl + url);

            System.out.println("sending get request to: " + baseUrl + url);

            ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

            int status = response.getStatus();
            if (status != 200) {
                throw new RuntimeException("Failed: HTTP error code: " + status);
            }

             output = response.getEntity(String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    public String post(String url, JSONObject input) {
        String output = "";
        try {
            Client client = Client.create();
            WebResource webResource = client.resource(baseUrl + url);

            System.out.println("sending post request to: " + baseUrl + url);
            System.out.println("with body: " + input.toString());

            ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, input);


            int status = response.getStatus();
            System.out.println(response.toString());
            if (status != 200) {
                throw new RuntimeException("Failed: HTTP error code: " + status);
            }

            output = response.getEntity(String.class);
            return output;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }
    
    public String delete(String url, JSONObject input) {
        String output = "";
        try {
            Client client = Client.create();
            WebResource webResource = client.resource(baseUrl + url);

            System.out.println("sending delete request to: " + baseUrl + url);
            System.out.println("with body: " + input.toString());

            ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class, input);


            int status = response.getStatus();
            System.out.println(response.toString());
            if (status != 200) {
                throw new RuntimeException("Failed: HTTP error code: " + status);
            }

            output = response.getEntity(String.class);
            return output;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

}
