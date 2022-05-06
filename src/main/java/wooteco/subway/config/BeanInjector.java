package wooteco.subway.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wooteco.subway.di.PeanutContext;

@Configuration
public class BeanInjector {

    private final ApplicationContext applicationContext;

    public BeanInjector(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public PeanutContext peaNutContext() {
        return PeanutContext.getInstance(applicationContext);
    }
}
