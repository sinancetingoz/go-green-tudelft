package client;

//import org.springframework.http.*;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pojos.Account;
import pojos.Activity;

import java.util.ArrayList;

//import server.Application;
//142.93.230.132:8080

public class ConnectAccount extends Connect {
    public static int temperature = 0;
    public static int hours = 0;
    private static String USERNAME = "";
    private static String SESSION_ID = "";
    private static Account myAccount;


    /**
     * Gets own account object.
     */
    public static Account getMyAccount() {
        return myAccount;
    }

    /**
     * Get the sessionId of the local user.
     */
    public static String getSessionId() {
        return SESSION_ID;
    }

    public static String getUsername() {
        return USERNAME;
    }


    /**
     * Logs out the user with a sessionID.
     */
    public static void logOut() {
        String url = url_default + "logout/";
        url += SESSION_ID;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestBody = new HttpEntity<>(headers);
        restTemplate.exchange(url, HttpMethod.GET, requestBody, String.class);
    }

    /**
     * Gets the account to display.
     * @return The account.
     */
    public static Account getAccount() {
        String url = url_default + "get_account/";
        url += SESSION_ID;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestBody = new HttpEntity<>(headers);
        ResponseEntity<Account> response = restTemplate.exchange(url, HttpMethod.GET, requestBody,
                Account.class);
        return response.getBody();
    }
    
    /**
     * Registers a account.
     *
     * @param account the account to register
     * @return true if the account is registered successfully, false otherwise
     */
    public static Boolean serverRegister(Account account) {
        String url = url_default + "register";
        url += "?username=" + account.getUsername();
        HttpHeaders headers = new HttpHeaders();
        // headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<Account> requestBody = new HttpEntity<>(account, headers);

        // Send request with POST method.
        boolean response = restTemplate.postForObject(url, requestBody, Boolean.class);
        return response;
    }

    /**
     * Tries to log a user in.
     *
     * @param name username of the user
     * @param pass password of the user
     * @return returns whether the log in was successful or not
     */
    public static boolean serverLogin(String name, String pass) {

        String url = url_default + "login";
        String credentials = name + ":" + pass;

        HttpHeaders headers = new HttpHeaders();
        //  headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        // Data attached to the request.
        HttpEntity<String> requestBody = new HttpEntity<>(credentials, headers);
        // Send request with POST method.
        String response = restTemplate.postForObject(url, requestBody, String.class);
        if (response != null) {
            USERNAME = name;
            SESSION_ID = response;
            ConnectAccount.myAccount = ConnectAccount.getAccount();
            ConnectFriends.setFriends(ConnectFriends.getFriends());
        }

        return response != null;
    }

    /**
     * Adds an activity to the user.
     *
     * @param activity the activity to be added
     * @return true if the adding was successful, false otherwise
     */
    public static boolean addActivity(Activity activity) {
        String url = url_default + "add_activity/";
        url += SESSION_ID;
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Activity> requestBody = new HttpEntity<>(activity, httpHeaders);

        boolean response = restTemplate.postForObject(url, requestBody, Boolean.class);
        System.out.println(response);
        return response;
    }

    /**
     * Gets all the activities of the user.
     *
     * @return a list containing all the activities of a particular user
     */
    public static ArrayList<Activity> getActivities() {
        String url = url_default + "get_activity/";
        url += SESSION_ID;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();

        //HttpEntity<String> requestBody = new HttpEntity<>(headers);


        ResponseEntity<ArrayList<Activity>> response =
                restTemplate.exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<ArrayList<Activity>>() {
                        });
        return response.getBody();
    }

    /**
     * Returns all users whose username matches a certain pattern.
     *
     * @param match the matching pattern
     * @return an array list containing all the users that match it
     */
    public static ArrayList<String> getMatchingUsers(String match) {
        String url = url_default + "get_match/";
        url += SESSION_ID;
        url += "/";
        url += match;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();

        //HttpEntity<String> requestBody = new HttpEntity<>(headers);
        ResponseEntity<ArrayList<String>> response = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<ArrayList<String>>() {
                });
        return response.getBody();
    }

    /**
     * Returns the account of the friend with the given username.
     * @param username the username of the account we are looking for
     * @return the account of this user
     */
    public static Account getFriendAccount(String username) {
        String url = url_default + "get_friendAccount/";
        url += SESSION_ID;
        url += "/";
        url += username;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestBody = new HttpEntity<>(headers);
        ResponseEntity<Account> response = restTemplate.exchange(url, HttpMethod.GET, null,
                Account.class);
        return response.getBody();
    }

    /**
     * Sets energy of the user.
     * @param saved The number to have set
     * @return response.
     */
    public static boolean setEnergy(int saved) {
        String url = url_default + "set_energy/";
        url += SESSION_ID;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Integer> requestEntity = new HttpEntity<>(saved, headers);

        boolean response = restTemplate.postForObject(url, requestEntity, Boolean.class);
        return response;
    }

    /**
     * Sets the heating of the current user.
     * @param hasHeating the heating status of the user
     * @return true if the heating status was updated successfully, false otherwise
     */
    public static boolean setHeating(boolean hasHeating) {
        String url = url_default + "set_heating/";
        url += SESSION_ID;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Boolean> reqEntity = new HttpEntity<>(hasHeating, headers);

        boolean resp = restTemplate.postForObject(url, reqEntity, Boolean.class);
        return resp;
    }

    /**
     * Resets the heating status to false when restarting the server.
     * @return true if the resetting was successful, false otherwise
     */
    public static boolean resetHeating() {
        String url = url_default + "reset_heating";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Boolean> reqEntity = new HttpEntity<>(headers);

        boolean resp = restTemplate.postForObject(url, reqEntity, Boolean.class);
        return resp;
    }
}
