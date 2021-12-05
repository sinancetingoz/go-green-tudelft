package client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import pojos.Account;

public class ConnectEmail extends Connect {


    /**
     * Sends a recover password request.
     * @param email the email to be recovered
     * @return true if the adding was successful, false otherwise
     */
    public static boolean recoverPassword(String email) {
        String url = url_default + "recover/";
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestBody = new HttpEntity<>(email, httpHeaders);

        boolean response = restTemplate.postForObject(url, requestBody, Boolean.class);
        System.out.println(response);
        return response;
    }


    /**
     * Sends a recover password request.
     * @param account the account to be confirmed
     * @return true if the adding was successful, false otherwise
     */
    public static boolean confirmAccount(Account account) {
        String url = url_default + "confirm/";
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Account> requestBody = new HttpEntity<>(account, httpHeaders);

        boolean response = restTemplate.postForObject(url, requestBody, Boolean.class);
        System.out.println(response);
        return response;
    }




}