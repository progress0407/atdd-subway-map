package wooteco.subway.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import wooteco.subway.di.PeaNutContext;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private ApplicationContext springBeanContainer;

    public StartupApplicationListener(ApplicationContext springBeanContainer) {
        this.springBeanContainer = springBeanContainer;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        PeaNutContext peaNutContext = PeaNutContext.getInstance(springBeanContainer);
        peaNutContext.run();
    }
}
