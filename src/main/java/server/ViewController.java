package server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;
import pojos.Account;
import services.AccountService;

import java.util.HashMap;

@Controller
public class ViewController {

    AccountService ls = new AccountService();

    /**
     * Sets a new password.
     * @param recoverId the id of the recovered account
     * @return a html page
     */
    @GetMapping("/recover/{recoverId}")
    public ModelAndView setNewPassword(@PathVariable("recoverId") String recoverId) {
        ModelAndView mv = new ModelAndView();
        if (ResetPasswordController.recoverRequests.containsKey(recoverId)) {
            mv.setViewName("gogreen.html");
        }
        return mv;

    }

    /**
     * Registers the account.
     * @param confirmId the confirmation Id of the new created account
     * @return and Html page
     */
    @GetMapping("/register/{confirmId}")
    public ModelAndView registerConfirmation(@PathVariable("confirmId") String confirmId) {
        HashMap<String, Account> confirmations = AccountController.getConfirmations();
        ModelAndView mv = new ModelAndView();
        if (confirmations.containsKey(confirmId)) {
            if (ls.createAccount(confirmations.get(confirmId))) {
                confirmations.remove(confirmId);
                AccountController.setConfirmations(confirmations);
                mv.setViewName("success.html");
                return mv;
            }
        }
        mv.setViewName("failed.html");
        return mv;
    }

    /**
     * Changes the password.
     * @param recoverId the recoverId
     * @param newPassword the newPassword
     */
    @PostMapping("/recover/{recoverId}")
    public ModelAndView changePassword(@PathVariable("recoverId") String recoverId,
                                       @RequestBody String newPassword) {
        ModelAndView mv = new ModelAndView();
        if (!ResetPasswordController.recoverRequests.containsKey(recoverId)) {
            mv.setViewName("failed.html");
        }
        if (newPassword == null || newPassword.length() < 6) {

            mv.setViewName("gogreen.html");
            return  mv;
        }
        System.out.println(newPassword);
        String user = ResetPasswordController.recoverRequests.get(recoverId);
        String[] piece = newPassword.split("=");
        String password = piece[1];
        System.out.println(password);
        ls.updatePassword(user,password);
        mv.setViewName("redirect.html");
        ResetPasswordController.recoverRequests.remove(recoverId);
        return mv;
    }
}
