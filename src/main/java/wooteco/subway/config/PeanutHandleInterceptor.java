package wooteco.subway.config;

import org.springframework.web.servlet.HandlerInterceptor;
import wooteco.subway.di.PeaNutContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PeanutHandleInterceptor implements HandlerInterceptor {

    private final PeaNutContext peaNutContext;

    public PeanutHandleInterceptor(PeaNutContext peaNutContext) {
        this.peaNutContext = peaNutContext;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        peaNutContext.updateState(request);
        return true;
    }
}
