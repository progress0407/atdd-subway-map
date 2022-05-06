package wooteco.subway.config;

import org.springframework.web.servlet.HandlerInterceptor;
import wooteco.subway.di.PeaNutContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PeanutHandleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("PeanutHandleInterceptor.preHandle");
        PeaNutContext.updateState(request);
        return true;
    }
}
