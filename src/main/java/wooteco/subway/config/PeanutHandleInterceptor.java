package wooteco.subway.config;

import org.springframework.web.servlet.HandlerInterceptor;
import wooteco.subway.di.PeanutContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PeanutHandleInterceptor implements HandlerInterceptor {

    private final PeanutContext peaNutContext;

    public PeanutHandleInterceptor(PeanutContext peaNutContext) {
        this.peaNutContext = peaNutContext;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Class<? extends HttpServletRequest> aClass = request.getClass();
        peaNutContext.updateState(request);
        return true;
    }
}
