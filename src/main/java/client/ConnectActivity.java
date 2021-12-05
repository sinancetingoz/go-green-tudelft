package client;

//import org.graalvm.compiler.lir.LIRInstruction;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
//import pojos.Activity;
import pojos.Category;
import pojos.DefaultValue;

import java.util.ArrayList;

public class ConnectActivity extends Connect {

    /**
    * Gets all the food related activities.
    * @param category The category
    * @return The arraylist of the activities
    */
    public static ArrayList<DefaultValue> getFood(Category category) {
        String url = url_default + "get_descriptions_by_category";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Category> requestEntity = new HttpEntity<>(category);


        ResponseEntity<ArrayList<DefaultValue>> response =
                restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                        new ParameterizedTypeReference<ArrayList<DefaultValue>>() {
                        });
        return response.getBody();
    }

    /**
     * Gets the consumption of a defaultvalue.
     * @param description The description of the Defaultvalue
     * @return The defaultvalue
     */
    public static DefaultValue getConsumption(String description) {
        String url = url_default + "get_consumption_by_description";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> requestEntity = new HttpEntity<>(description);


        ResponseEntity<DefaultValue> response =
                restTemplate.exchange(url, HttpMethod.POST, requestEntity, DefaultValue.class);
        return response.getBody();
    }
}
