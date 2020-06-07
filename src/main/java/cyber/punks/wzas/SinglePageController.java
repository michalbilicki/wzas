package cyber.punks.wzas;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SinglePageController {

    @RequestMapping(value = {"/", "/loginpage", "/main-page"})
    public String index() {
        return "index.html";
    }
}
