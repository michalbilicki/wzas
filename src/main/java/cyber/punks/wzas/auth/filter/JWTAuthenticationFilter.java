package cyber.punks.wzas.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import cyber.punks.wzas.auth.LoginDto;
import cyber.punks.wzas.auth.SecurityConstants;
import cyber.punks.wzas.auth.UserDetailsImpl;
import cyber.punks.wzas.entities.AccountEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import com.auth0.jwt.JWT;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;



public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            AccountEntity creds = new ObjectMapper()
                    .readValue(req.getInputStream(), AccountEntity.class);


            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getLogin(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {


        LoginDto loginDto = new LoginDto();
        loginDto.setLogin(((UserDetailsImpl) auth.getPrincipal()).getUsername());
        for(GrantedAuthority grantedAuthority : auth.getAuthorities()){
            loginDto.getRoles().add(grantedAuthority.getAuthority());
        }

        String token = JWT.create()
                .withSubject(((UserDetailsImpl) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .withAudience(loginDto.getRoles().get(0))
                .sign(HMAC512(SecurityConstants.SECRET.getBytes()));

        Cookie cookie = new Cookie(SecurityConstants.COOKIE_NAME, SecurityConstants.TOKEN_PREFIX + token);
        res.addCookie(cookie);
        res.getWriter().write(new ObjectMapper().writeValueAsString(loginDto));
    }
}
