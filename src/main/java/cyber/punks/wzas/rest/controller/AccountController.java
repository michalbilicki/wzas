package cyber.punks.wzas.rest.controller;


import cyber.punks.wzas.exceptions.AccountDoesNotExistException;
import cyber.punks.wzas.exceptions.IncorrectPasswordException;
import cyber.punks.wzas.exceptions.LoginAlreadyTakenException;
import cyber.punks.wzas.services.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import cyber.punks.wzas.rest.model.account.AccountDto;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PutMapping(value = "/change-login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeLogin(@RequestHeader(name = "new-login") String newLogin) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            accountService.editLogin((String) principal, newLogin);
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

    @GetMapping(value = "/own", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> getAccount() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            return ResponseEntity.ok(AccountDto.convertEntityToDto(accountService.getAccount((String) principal).get()));
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

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> removeAccount(@PathVariable String login) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            accountService.removeAccount((String) principal);
            return ResponseEntity.ok().build();
        } catch (AccountDoesNotExistException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}