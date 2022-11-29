package software.pinklady.controltableadmin;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@Configuration
@EnableJdbcRepositories
public class ApplicationConfiguration {

    //@Value("${DEPLOY_LOCATION}")
    private String deployLocation;
    //@Value("${JSON_LOCATION}")
    private String jsonLocation;


}