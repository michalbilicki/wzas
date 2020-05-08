package cyber.punks.wzas.services;

import cyber.punks.wzas.entities.AccountEntity;
import cyber.punks.wzas.exceptions.AccountDoesNotExistException;
import cyber.punks.wzas.exceptions.IncorrectPasswordException;
import cyber.punks.wzas.exceptions.LoginAlreadyTakenException;
import cyber.punks.wzas.rest.model.account.AccountDto;
import cyber.punks.wzas.repository.AccountRepository;
import cyber.punks.wzas.services.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void addAccount(String login, String password) throws LoginAlreadyTakenException {
        if (accountRepository.findByLogin(login).isPresent())
            throw new LoginAlreadyTakenException("loginAlreadyTaken");

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        accountRepository.save(new AccountEntity(
                login,
                bCryptPasswordEncoder.encode(password),
                "ROLE_USER"
        ));
    }

    @Override
    public void editLogin(String login, String newLogin) throws LoginAlreadyTakenException, AccountDoesNotExistException {
        Optional<AccountEntity> accountEntity = accountRepository.findByLogin(login);

        if (accountEntity.isEmpty())
            throw new AccountDoesNotExistException("accountDoesNotExists");

        if (accountRepository.findByLogin(newLogin).isEmpty())
            accountEntity.get().setLogin(newLogin);
        else
            throw new LoginAlreadyTakenException("loginAlreadyTaken");

        accountRepository.save(accountEntity.get());
    }

    @Override
    public void editPassword(String login, String password, String newPassword) throws AccountDoesNotExistException, IncorrectPasswordException {
        Optional<AccountEntity> accountEntity = accountRepository.findByLogin(login);

        if (accountEntity.isEmpty())
            throw new AccountDoesNotExistException("accountDoesNotExists");

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (bCryptPasswordEncoder.encode(accountEntity.get().getPassword()).equals(bCryptPasswordEncoder.encode(password)))
            accountEntity.get().setPassword(bCryptPasswordEncoder.encode(newPassword));
        else
            throw new IncorrectPasswordException("passwordIsNotCorrect");

        accountRepository.save(accountEntity.get());
    }

    @Override
    public void removeAccount(String login) throws AccountDoesNotExistException {
        Optional<AccountEntity> accountEntity = accountRepository.findByLogin(login);

        if (accountEntity.isEmpty())
            throw new AccountDoesNotExistException("accountDoesNotExists");

        accountRepository.delete(accountEntity.get());
    }

    @Override
    public Optional<AccountEntity> getAccount(String login) throws AccountDoesNotExistException {
        Optional<AccountEntity> accountEntity = accountRepository.findByLogin(login);

        if (accountEntity.isEmpty())
            throw new AccountDoesNotExistException("accountDoesNotExists");

        return accountEntity;
    }

    @Override
    public List<AccountEntity> getAllAccounts() {
        return accountRepository.findAll();
    }
}
