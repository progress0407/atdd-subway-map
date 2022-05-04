package wooteco.subway.di;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.subway.application.LineService;
import wooteco.subway.application.StationService;
import wooteco.subway.di.exception.NoSuchNutDefinitionException;
import wooteco.subway.ui.LineController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PeaNutContainerTest {

    private PeaNutContainer peaNutContainer = new PeaNutContainer();

    @DisplayName("존재하는 땅콩을 찾을 수 있다")
    @Test
    void can_register_custom_bean() {
        StationService stationService = peaNutContainer.getNut(StationService.class);
        LineService lineService = peaNutContainer.getNut(LineService.class);

        assertThat(stationService).isNotNull();
        assertThat(lineService).isNotNull();
    }

    @DisplayName("존재하지 않는 땅콩을 찾는다")
    @Test
    void find_non_exist_nut() {
        assertThatThrownBy(() -> peaNutContainer.getNut(LineController.class))
                .isInstanceOf(NoSuchNutDefinitionException.class);
    }
}