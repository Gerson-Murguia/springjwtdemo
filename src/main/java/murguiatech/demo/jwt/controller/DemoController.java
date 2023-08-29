package murguiatech.demo.jwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class DemoController {

    @PostMapping("demo")
    public String welcome(){
        return "welcome from secure endpoint";
    }

}
