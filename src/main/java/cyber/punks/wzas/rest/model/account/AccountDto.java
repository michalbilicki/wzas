package cyber.punks.wzas.rest.model.account;

import cyber.punks.wzas.entities.AccountEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AccountDto {

    @Size(max = 20, message = "Login maximum length is 20")
    @NotBlank
    @Getter
    @Setter
    private String login;

    @Size(max = 115, message = "Password maximum length is 115")
    @NotBlank
    @Getter
    @Setter
    private String password;

    @Column(name = "role", nullable = false, unique = false)
    @Getter
    @Setter
    private String role;

    public static AccountEntity convertDtoToEntity(AccountDto accountDto) {
        AccountEntity accountEntity = new AccountEntity(accountDto.getLogin());
        accountEntity.setPassword(accountDto.getPassword());
        accountEntity.setRole(accountDto.getRole());
        return accountEntity;
    }

    public static AccountDto convertEntityToDto(AccountEntity accountEntity) {
        AccountDto accountDto = new AccountDto();
        accountDto.login = accountEntity.getLogin();
        accountDto.password = accountEntity.getPassword();
        accountDto.role = accountEntity.getRole();
        return accountDto;
    }
}
