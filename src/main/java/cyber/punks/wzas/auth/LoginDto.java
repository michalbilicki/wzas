package cyber.punks.wzas.auth;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LoginDto {

    private String login;
    private List<String> roles = new ArrayList<>();

}
