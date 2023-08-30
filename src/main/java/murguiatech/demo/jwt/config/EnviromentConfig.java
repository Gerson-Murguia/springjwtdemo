package murguiatech.demo.jwt.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class EnviromentConfig {


    private final String SECRET_KEY;

    public EnviromentConfig( @Value("${secret.jwt.key}") String SECRET_KEY) {
        this.SECRET_KEY = SECRET_KEY;
    }
}
