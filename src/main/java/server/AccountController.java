package server;

import database.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pojos.Account;
import pojos.Session;
import services.AccountService;
import services.SessionService;
import util.SessionIdGenerator;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RestController
public class AccountController {

    private static HashMap<String, Account> confirmations = new HashMap<>();

    @Autowired
    private TemplateEngine templateEngine; // From Thymeleaf

    private AccountService ls = new AccountService();
    private AccountDao db = new AccountDao();
    private SessionService ss = new SessionService();


    /**
     * Trying to log a user in.
     *
     * @param credentials the username and password, concatenated with a :-sign.
     * @return the sessionID of the user if the logging in was successful, null otherwise
     */
    @PostMapping("/login")
    public String logIn(@RequestBody String credentials) {

        String sessionId = null;
        String[] userlogin = credentials.split(":", 0);
        String username = userlogin[0];
        String password = userlogin[1];

        if (ls.checkLogin(username, password)) {
            sessionId = new SessionIdGenerator().getAlphaNumericString(42);
        }
        ss.putSession(sessionId, new Session(username, LocalDateTime.now()));
        return sessionId;
    }

    /**
     * Gets account based on the sessionId.
     * @param sessionId The sessionId coupled to the account.
     * @return the account to be returned
     */
    @RequestMapping("/get_account/{sessionId}")
    public Account getAccounts(@PathVariable("sessionId") String sessionId) {
        if (ss.sessionExists(sessionId)) {
            String username = ss.getAllSessions().get(sessionId).getUsername();
            return ls.getAccount(username);
        }
        return new Account();
    }

    /**
     * Registers a user.
     * @param username the username
     * @param newuser the account to be registered.
     * @return true or false depending on whether the method was successful.
     */
    @PostMapping("/register")
    public boolean registerUser(@RequestParam(value = "username",
            defaultValue = "user") String username, @RequestBody Account newuser) {
        System.out.println(newuser.getPassword());
        return ls.createAccount(newuser);
    }



    /**
     * Registers a user.
     * @param newuser the account to be registered.
     * @return true or false depending on whether the method was successful.
     */
    @PostMapping("/confirm")
    public boolean confirmUser(@RequestBody Account newuser) {
        if (!ls.createAccount(newuser)) {
            return false;
        }
        ls.deleteAccount(newuser);
        SessionIdGenerator generator = new SessionIdGenerator();
        String confirmId = generator.getAlphaNumericString(20);
        confirmations.put(confirmId,newuser);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                sendMail(confirmId,newuser);
            }
        }, 1000);
        return true;
    }

    /**
     * Sets saved energy of a user.
     * @param energy amount for energy to be set to
     * @param sessionId SessionId coupled to the account having their energy set
     * @return true or false depending on whether the method was successful.
     */
    @RequestMapping("/set_energy/{sessionId}")
    public boolean setEnergy(@RequestBody int energy,
                             @PathVariable("sessionId") String sessionId) {
        if (ss.sessionExists(sessionId)) {
            String user = ss.getAllSessions().get(sessionId).getUsername();
            return ls.setEnergy(user, energy);
        }

        return false;
    }

    /**
     * Sets the heating status of a user.
     * @param hasHeating the status of the heating
     * @param sessionId the sessionId of the user
     * @return true if the update was successful, false otherwise
     */
    @RequestMapping("/set_heating/{sessionId}")
    public boolean setHeating(@RequestBody boolean hasHeating,
                              @PathVariable("sessionId") String sessionId) {
        if (ss.sessionExists(sessionId)) {
            String user = ss.getAllSessions().get(sessionId).getUsername();
            return ls.setHeating(user, hasHeating);
        }

        return false;
    }

    @RequestMapping("/reset_heating")
    public boolean resetHeating() {
        return ls.resetHeating();
    }

    public void setLs(AccountService ls) {
        this.ls = ls;
    }

    public void setDb(AccountDao db) {
        this.db = db;
    }

    public void setSs(SessionService ss) {
        this.ss = ss;
    }

    public static HashMap<String, Account> getConfirmations() {
        return confirmations;
    }

    public static void setConfirmations(HashMap<String, Account> confirmations1) {
        confirmations = confirmations1;
    }


    /**
     * Sends email from the gmail server.
     * @param confirmId of the account to be confirmed
     * @param newAccount of the account to be created
     * @throws MessagingException when the email was unable to be sent
     */
    public boolean sendMail(String confirmId,Account newAccount) {

        try {
            String user = newAccount.getUsername();
            String link = "http://localhost:8080/register/";
            link += confirmId;
            Context context = new Context();
            user = "Hello " + user;
            context.setVariable("name", user);
            context.setVariable("link", link);
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("smtp.gmail.com");
            mailSender.setPort(465);
            mailSender.setUsername("gogreen.group57");
            mailSender.setPassword("oopgroup57");

            //from email id and password

            Properties mailProp = mailSender.getJavaMailProperties();
            mailProp.put("mail.transport.protocol", "smtp");
            mailProp.put("mail.smtp.auth", "true");
            mailProp.put("mail.smtp.starttls.enable", "true");
            mailProp.put("mail.smtp.starttls.required", "true");
            mailProp.put("mail.debug", "true");
            mailProp.put("mail.smtp.ssl.enable", "true");

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            String toMailId = newAccount.getMail();
            helper.setTo(toMailId);
            helper.setSubject("Welcome to GoGreen");
            helper.setText(templateEngine.process("confirmation", context), true);
            //Checking Internet Connection and then sending the mail
            mailSender.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            return false;
        }

    }

}
