package cyber.punks.wzas.auth;

import cyber.punks.wzas.entities.AccountEntity;
import cyber.punks.wzas.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<AccountEntity> account = accountRepository.findByLogin(s);
        account.orElseThrow(() -> new UsernameNotFoundException("Not found" + s));
        return account.map(UserDetailsImpl::new).get();
    }
}
