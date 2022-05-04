package wooteco.subway.di;

import org.springframework.core.annotation.AnnotationUtils;
import wooteco.subway.di.annotaion.Peanut;
import wooteco.subway.di.exception.NoSuchNutDefinitionException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PeaNutContainer {

    private static final String PROJECT_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator;
    private static final String DETAIL_PROJECT_PATH = PROJECT_PATH + "wooteco" + File.separator + "subway";
    private static final String JAVA_EXTENSION = ".java";
    private Map<Class<?>, Supplier<Object>> container = new HashMap<>();

    public PeaNutContainer() {
        List<? extends Class<?>> classes = getClassesWithAnnotation();

        for (Class<?> clazz : classes) {
            Object finalCreatedClass = getConstructedClass(clazz);
            container.put(clazz, () -> finalCreatedClass);
        }
    }

    public <T> T getNut(Class<T> nut) {
        Supplier<Object> findNutSupplier = container.get(nut);
        if (findNutSupplier == null) {
            throw new NoSuchNutDefinitionException(nut.getSimpleName());
        }
        return (T) findNutSupplier.get();
    }

    private List<? extends Class<?>> getClassesWithAnnotation() {
        try (Stream<Path> walkStream = Files.walk(Paths.get(DETAIL_PROJECT_PATH))) {
            return walkStream
                    .filter(path -> path.toFile().isFile())
                    .filter(path -> path.toString().endsWith(".java"))
                    .map(this::toClass)
                    .filter(clazz -> AnnotationUtils.findAnnotation(clazz, Peanut.class) != null)
                    .collect(Collectors.toUnmodifiableList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Object getConstructedClass(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private Class<?> toClass(Path path) {
        try {
            String simpleClassPath = path.toString()
                    .replace(PROJECT_PATH, "")
                    .replace(JAVA_EXTENSION, "")
                    .replace(File.separator, ".");
            return Class.forName(simpleClassPath);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
