package cyber.punks.wzas.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @Column(name = "login", nullable = false, unique = true, length = 20)
    @Size(max = 20, message = "Login maximum length is 20")
    @NotBlank
    @Getter
    @Setter
    private String login;

    @Column(name = "password", nullable = false, unique = false, length = 115)
    @Size(max = 115, message = "Password maximum length is 115")
    @NotBlank
    @Getter
    @Setter
    private String password;

    @Column(name = "role", nullable = false, unique = false)
    @Getter
    @Setter
    private String role;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_position", referencedColumnName = "id")
    @Getter
    @Setter
    private PositionEntity positionEntity;

    public AccountEntity() {

    }

    public AccountEntity(String login) {
        this.login = login;
    }

    public AccountEntity(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public AccountEntity(String login, String password, String role, PositionEntity positionEntity) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.positionEntity = positionEntity;
    }
}
