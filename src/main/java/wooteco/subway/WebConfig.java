package wooteco.subway;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wooteco.subway.config.PeanutHandleInterceptor;
import wooteco.subway.di.PeaNutContext;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final PeaNutContext peaNutContext;

    public WebConfig(PeaNutContext peaNutContext) {
        this.peaNutContext = peaNutContext;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*").allowedOriginPatterns("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PeanutHandleInterceptor(peaNutContext));
    }
}