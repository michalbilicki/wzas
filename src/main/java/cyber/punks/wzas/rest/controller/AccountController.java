package cyber.punks.wzas.rest.controller;


import cyber.punks.wzas.exceptions.AccountDoesNotExistException;
import cyber.punks.wzas.exceptions.IncorrectPasswordException;
import cyber.punks.wzas.exceptions.LoginAlreadyTakenException;
import cyber.punks.wzas.services.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import cyber.punks.wzas.rest.model.account.AccountDto;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PutMapping(value = "/change-login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeLogin(@RequestHeader(name = "login") String login,
                                         @RequestHeader(name = "new-login") String newLogin) {
        try {
            accountService.editLogin(login, newLogin);
            return ResponseEntity.ok().build();
        } catch (LoginAlreadyTakenException | AccountDoesNotExistException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/change-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@RequestHeader(name = "login") String login,
                                            @RequestHeader(name = "password") String password,
                                            @RequestHeader(name = "new-password") String newPassword) {
        try {
            accountService.editPassword(login, password, newPassword);
            return ResponseEntity.ok().build();
        } catch (AccountDoesNotExistException | IncorrectPasswordException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> getAccountByLogin(@PathVariable String login) {
        try {
            return ResponseEntity.ok(AccountDto.convertEntityToDto(accountService.getAccount(login).get()));
        } catch (AccountDoesNotExistException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AccountDto> getAllAccounts() {
        return accountService.getAllAccounts()
                .stream()
                .map(AccountDto::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping(value = "/delete/{login}")
    public ResponseEntity<?> removeAccount(@PathVariable String login) {
        try {
            accountService.removeAccount(login);
            return ResponseEntity.ok().build();
        } catch (AccountDoesNotExistException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}