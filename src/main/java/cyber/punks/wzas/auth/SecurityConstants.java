package cyber.punks.wzas.auth;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer_";
    public static final String COOKIE_NAME = "Authorization";
    public static final String SIGN_UP_URL = "/api/register/add";
}