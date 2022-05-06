package wooteco.subway.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.subway.di.PeanutContext;
import wooteco.subway.test.service.TestPrototypeService;
import wooteco.subway.test.service.TestRequestService;
import wooteco.subway.test.service.TestSessionService;
import wooteco.subway.test.service.TestSingletonService;

import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    public static final String CLASS_PEANUT = "class method";
    public static final String PEANUT_ID = "peanut id";
    private final PeanutContext peanutContext;

    public TestController(PeanutContext peanutContext) {
        this.peanutContext = peanutContext;
    }

    @GetMapping("/singleton")
    public Map<String, String> singleton() {
        TestSingletonService testSingletonService = peanutContext.getPeanut(TestSingletonService.class);

        return Map.of(CLASS_PEANUT, "TestController.singleton", PEANUT_ID, testSingletonService.toString());
    }

    @GetMapping("/prototype")
    public Map<String, String> prototype() {
        TestPrototypeService testPrototypeService = peanutContext.getPeanut(TestPrototypeService.class);

        return Map.of(CLASS_PEANUT, "TestController.prototype", PEANUT_ID, testPrototypeService.toString());
    }

    @GetMapping("/request")
    public Map<String, String> request() {
        TestRequestService testRequestService = peanutContext.getPeanut(TestRequestService.class);

        return Map.of(CLASS_PEANUT, "TestController.request", PEANUT_ID, testRequestService.toString());
    }

    @GetMapping("/session")
    public Map<String, String> session() {
        TestSessionService testSessionService = peanutContext.getPeanut(TestSessionService.class);

        return Map.of(CLASS_PEANUT, "TestController.session", PEANUT_ID, testSessionService.toString());
    }
}
