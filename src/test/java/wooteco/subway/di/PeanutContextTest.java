package wooteco.subway.di;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;
import wooteco.subway.acceptance.AcceptanceTest;
import wooteco.subway.application.LineService;
import wooteco.subway.application.StationService;
import wooteco.subway.di.exception.NoSuchPeanutDefinitionException;
import wooteco.subway.test.service.TestPrototypeService;
import wooteco.subway.test.service.TestSingletonService;
import wooteco.subway.ui.LineController;

import javax.annotation.PostConstruct;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PeanutContextTest extends AcceptanceTest {

    @Autowired
    private ApplicationContext springBeanContainer;

    private PeanutContext peanutContext;

    @PostConstruct
    public void initPeanutContext() {
        peanutContext = PeanutContext.getInstance(springBeanContainer);
    }

    @DisplayName("땅콩이 존재한다면 땅콩을 찾을 수 있다")
    @Test
    void can_register_custom_bean() {
        StationService stationService = peanutContext.getPeanut(StationService.class);
        LineService lineService = peanutContext.getPeanut(LineService.class);

        assertThat(stationService).isNotNull();
        assertThat(lineService).isNotNull();
    }

    @DisplayName("존재하지 않는 땅콩을 찾을 경우 예외를 반환한다")
    @Test
    void find_non_exist_nut() {
        assertThatThrownBy(() -> peanutContext.getPeanut(LineController.class))
                .isInstanceOf(NoSuchPeanutDefinitionException.class);
    }

    @DisplayName("땅콩은 싱글톤이다")
    @Test
    void peanut_is_singleton() {
        StationService stationService1 = peanutContext.getPeanut(StationService.class);
        StationService stationService2 = peanutContext.getPeanut(StationService.class);

        LineService lineService1 = peanutContext.getPeanut(LineService.class);
        LineService lineService2 = peanutContext.getPeanut(LineService.class);

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
        LineService injectedPeanut = (LineService) ReflectionTestUtils.getField(lineControllerBean, "lineService");
        assertThat(injectedPeanut).isNotNull();
    }

    @DisplayName("DI 주입이 싱글톤 스코프로 동작하는지를 검증한다")
    @Test
    void inject_singleton() {
        Request.GET.request("/test/singleton");
        TestSingletonService peanut1 = peanutContext.getPeanut(TestSingletonService.class);

        Request.GET.request("/test/singleton");
        TestSingletonService peanut2 = peanutContext.getPeanut(TestSingletonService.class);

        assertThat(peanut1).isSameAs(peanut2);
    }


    @DisplayName("DI 주입이 프로토타입 스코프로 동작하는지를 검증한다")
    @Test
    void inject_prototype() {
        Request.GET.request("/test/prototype");
        TestPrototypeService peanut1 = peanutContext.getPeanut(TestPrototypeService.class);

        Request.GET.request("/test/prototype");
        TestPrototypeService peanut2 = peanutContext.getPeanut(TestPrototypeService.class);

        assertThat(peanut1).isNotSameAs(peanut2);
    }

    private enum Request {
        GET((when, url) -> when.get(url)),
        POST((when, url) -> when.post(url)),
        PUT((when, url) -> when.put(url)),
        PATCH((when, url) -> when.patch(url)),
        DELETE((when, url) -> when.delete(url));

        private final BiConsumer<RequestSender, String> condition;

        Request(BiConsumer<RequestSender, String> condition) {
            this.condition = condition;
        }

        public void request(String path) {
            condition.accept(RestAssured.when(), path);
        }
    }
}
