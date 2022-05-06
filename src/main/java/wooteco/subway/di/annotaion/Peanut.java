package wooteco.subway.di.annotaion;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

import static wooteco.subway.di.annotaion.PeanutLifeCycle.SINGLETON;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Peanut {

    @AliasFor("scopeName")
    PeanutLifeCycle value() default SINGLETON;

    @AliasFor("value")
    PeanutLifeCycle scopeName() default SINGLETON;
}
