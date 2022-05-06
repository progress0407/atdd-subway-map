package wooteco.subway.di.annotaion;

import javax.servlet.http.HttpServletRequest;
import java.util.function.BiPredicate;

public enum PeanutLifeCycle {

    SINGLETON((request, previousRequest) -> false),

    PROTOTYPE((request, previousRequest) -> true),

    REQUEST((request, previousRequest) -> request != previousRequest),

    SESSION((request, previousRequest) -> request.getRequestedSessionId() != previousRequest.getRequestedSessionId());

    private final BiPredicate<HttpServletRequest, HttpServletRequest> condition;

    /**
     * {@link Peanut}
     * @param condition : 땅콩 박스에 담긴 객체를 갱신할 조건을 나타냅니다
     */
    PeanutLifeCycle(BiPredicate<HttpServletRequest, HttpServletRequest> condition) {
        this.condition = condition;
    }

    public void recreateObjectByLifeCycleCondition(HttpServletRequest request, HttpServletRequest previousRequest, Runnable callbackFunction) {
        if (condition.test(request, previousRequest)) {
            callbackFunction.run();
        }
    }
}
