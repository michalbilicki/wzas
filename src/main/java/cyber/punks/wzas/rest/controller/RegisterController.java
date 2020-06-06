package cyber.punks.wzas.rest.controller;

import cyber.punks.wzas.exceptions.LoginAlreadyTakenException;
import cyber.punks.wzas.services.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/register")
public class RegisterController {

    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/add")
    public ResponseEntity<?> createAccount(@RequestHeader String login,
                                           @RequestHeader String password) {
        try {
            accountService.addAccount(login, password);
            return ResponseEntity.ok().build();
        } catch (LoginAlreadyTakenException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
