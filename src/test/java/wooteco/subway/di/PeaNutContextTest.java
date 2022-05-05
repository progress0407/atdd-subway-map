package wooteco.subway.di;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import wooteco.subway.acceptance.AcceptanceTest;
import wooteco.subway.application.LineService;
import wooteco.subway.application.StationService;
import wooteco.subway.di.exception.NoSuchNutDefinitionException;
import wooteco.subway.ui.LineController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

//@SpringBootTest
class PeaNutContextTest extends AcceptanceTest {

    @Autowired
    private ApplicationContext springBeanContainer;

    private final PeaNutContext peaNutContext = PeaNutContext.getInstance(springBeanContainer);


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

    @DisplayName("메서드 테스트")
    @Test
    void tt() {
        peaNutContext.run();
    }
}
