package wooteco.subway.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import wooteco.subway.di.PeanutContext;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private ApplicationContext springBeanContainer;

    public StartupApplicationListener(ApplicationContext springBeanContainer) {
        this.springBeanContainer = springBeanContainer;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        PeanutContext peanutContext = PeanutContext.getInstance(springBeanContainer);
        peanutContext.run();
    }
}
