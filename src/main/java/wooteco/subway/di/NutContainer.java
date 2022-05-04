package wooteco.subway.di;

import wooteco.subway.di.exception.NoSuchNutDefinitionException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class NutContainer {

    public Map<Class, Supplier<Object>> container = new HashMap<>();


    public <T> T getNut(Class<T> nut) {
        Supplier<Object> findNutSupplier = container.get(nut);
        if (findNutSupplier == null) {
            throw new NoSuchNutDefinitionException(nut.getSimpleName());
        }
        return (T) findNutSupplier.get();
    }

}
