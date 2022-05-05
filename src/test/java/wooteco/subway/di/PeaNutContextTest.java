package wooteco.subway.di;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;
import wooteco.subway.acceptance.AcceptanceTest;
import wooteco.subway.application.LineService;
import wooteco.subway.application.StationService;
import wooteco.subway.di.exception.NoSuchNutDefinitionException;
import wooteco.subway.ui.LineController;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PeaNutContextTest extends AcceptanceTest {

    @Autowired
    private ApplicationContext springBeanContainer;

    private PeaNutContext peaNutContext;

    @PostConstruct
    public void initPeanutContext() {
        peaNutContext = PeaNutContext.getInstance(springBeanContainer);
    }


    @DisplayName("땅콩이 존재한다면 땅콩을 찾을 수 있다")
    @Test
    void can_register_custom_bean() {
        StationService stationService = peaNutContext.getPeanut(StationService.class);
        LineService lineService = peaNutContext.getPeanut(LineService.class);

        assertThat(stationService).isNotNull();
        assertThat(lineService).isNotNull();
    }

    @DisplayName("존재하지 않는 땅콩을 찾을 경우 예외를 반환한다")
    @Test
    void find_non_exist_nut() {
        assertThatThrownBy(() -> peaNutContext.getPeanut(LineController.class))
                .isInstanceOf(NoSuchNutDefinitionException.class);
    }

    @DisplayName("땅콩은 싱글톤이다")
    @Test
    void peanut_is_singleton() {
        StationService stationService1 = peaNutContext.getPeanut(StationService.class);
        StationService stationService2 = peaNutContext.getPeanut(StationService.class);

        LineService lineService1 = peaNutContext.getPeanut(LineService.class);
        LineService lineService2 = peaNutContext.getPeanut(LineService.class);

        assertThat(stationService1).isSameAs(stationService2);
        assertThat(lineService1).isSameAs(lineService2);
    }

    /**
     * peaNutContext 에서 run() 메서드를 실행하지 않더라도
     * 부트 빈 초기화 구현체인 StartupApplicationListener 에서 실행을 하기 때문에 이곳에서 하지 않습니다
     */
    @DisplayName("DI 의존 주입이 되었는지를 테스트한다")
    @Test
    void di_injection() {
        LineController lineControllerBean = springBeanContainer.getBean(LineController.class);
        LineService lineServicePeanut = (LineService) ReflectionTestUtils.getField(lineControllerBean, "lineService");
        assertThat(lineServicePeanut).isNotNull();
    }
}
