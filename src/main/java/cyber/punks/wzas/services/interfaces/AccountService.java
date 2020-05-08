package cyber.punks.wzas.services.interfaces;

import cyber.punks.wzas.entities.AccountEntity;
import cyber.punks.wzas.exceptions.AccountDoesNotExistException;
import cyber.punks.wzas.exceptions.IncorrectPasswordException;
import cyber.punks.wzas.exceptions.LoginAlreadyTakenException;
import cyber.punks.wzas.rest.model.account.AccountDto;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    void addAccount(String login, String password) throws LoginAlreadyTakenException;

    void editLogin(String login, String newLogin) throws LoginAlreadyTakenException, AccountDoesNotExistException;

    void editPassword(String login, String password, String newPassword) throws AccountDoesNotExistException, IncorrectPasswordException;

    void removeAccount(String login) throws AccountDoesNotExistException;

    Optional<AccountEntity> getAccount(String login) throws AccountDoesNotExistException;

    List<AccountEntity> getAllAccounts();
}
