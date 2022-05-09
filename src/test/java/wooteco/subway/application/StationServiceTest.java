package wooteco.subway.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import wooteco.subway.dao.StationDao;
import wooteco.subway.domain.Station;
import wooteco.subway.exception.constant.BlankArgumentException;
import wooteco.subway.exception.constant.DuplicateException;
import wooteco.subway.exception.constant.NotExistException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
class StationServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private StationDao stationDao;

    private StationService stationService;

    @BeforeEach
    void setUp() {
        stationDao = new StationDao(jdbcTemplate);
        stationService = new StationService(stationDao);
    }

    @DisplayName("지하철역 저장")
    @Test
    void saveByName() {
        String stationName = "something";
        Station station = stationService.saveAndGet(stationName);
        assertThat(stationDao.findById(station.getId())).isNotEmpty();
    }

    @DisplayName("중복된 지하철역 저장")
    @Test
    void saveByDuplicateName() {
        String stationName = "something";
        stationService.saveAndGet(stationName);

        assertThatThrownBy(() -> stationService.saveAndGet(stationName))
            .isInstanceOf(DuplicateException.class);
    }

    @DisplayName("지하철 역 이름에 빈 문자열을 저장할 수 없다")
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "     "})
    void saveByEmptyName(String stationName) {
        assertThatThrownBy(() -> stationService.saveAndGet(stationName))
            .isInstanceOf(BlankArgumentException.class);
    }

    @DisplayName("지하철 역 삭제")
    @Test
    void deleteById() {
        Station station = stationService.saveAndGet("강남역");

        stationService.deleteById(station.getId());

        assertThat(stationDao.existByName("강남역")).isFalse();
    }

    @DisplayName("존재하지 않는 지하철 역 삭제")
    @Test
    void deleteNotExistStation() {
        assertThatThrownBy(() -> stationService.deleteById(50L))
            .isInstanceOf(NotExistException.class);
    }
}
