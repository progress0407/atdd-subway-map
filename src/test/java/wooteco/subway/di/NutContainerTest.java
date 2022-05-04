package wooteco.subway.di;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.subway.application.LineService;
import wooteco.subway.application.StationService;
import wooteco.subway.di.exception.NoSuchNutDefinitionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NutContainerTest {

    private NutContainer nutContainer = new NutContainer();

    @BeforeEach
    void setUp() {
        nutContainer = new NutContainer();
    }

    @DisplayName("존재하는 땅콩을 찾을 수 있다")
    @Test
    void can_register_custom_bean() {
        NutContainer nutContainer = new NutContainer();
        nutContainer.container.put(StationService.class, StationService::new);
        nutContainer.container.put(LineService.class, LineService::new);

        StationService stationService = nutContainer.getNut(StationService.class);
        LineService lineService = nutContainer.getNut(LineService.class);

        assertThat(stationService).isNotNull();
        assertThat(lineService).isNotNull();
    }

    @DisplayName("존재하지 않는 땅콩을 찾는다")
    @Test
    void find_non_exist_nut() {
        NutContainer nutContainer = new NutContainer();

        assertThatThrownBy(() -> nutContainer.getNut(StationService.class))
                .isInstanceOf(NoSuchNutDefinitionException.class);
    }
}