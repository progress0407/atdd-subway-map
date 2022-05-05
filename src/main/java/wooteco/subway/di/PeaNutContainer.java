package wooteco.subway.di;

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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PeaNutContainer {

    private static final String PATH_SEPARATOR = File.separator;
    private static final String PROJECT_PATH  = System.getProperty("user.dir");
    private static final String PROJECT_JAVA_PATH = PROJECT_PATH + "\\src\\main\\java\\".replace("\\", PATH_SEPARATOR);
    private static final String PACKAGE_PATH = PROJECT_JAVA_PATH + "\\wooteco\\subway".replace("\\", PATH_SEPARATOR);
    private static final String JAVA_EXTENSION = ".java";

    private final Map<Class<?>, Object> peanutContainer = new HashMap<>();

    public PeaNutContainer() {
        List<? extends Class<?>> classes = getClassesWithAnnotation();
        for (Class<?> clazz : classes) {
            peanutContainer.put(clazz, createInstanceDynamically(clazz));
        }
    }

    public <T> T getNut(Class<T> clazz) {
        T peanut = (T) peanutContainer.get(clazz);
        if (peanut == null) {
            throw new NoSuchNutDefinitionException(clazz.getSimpleName());
        }
        return peanut;
    }

    private Object createInstanceDynamically(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private List<? extends Class<?>> getClassesWithAnnotation() {
        try (Stream<Path> walkStream = Files.walk(Paths.get(PACKAGE_PATH))) {
            return walkStream
                    .filter(path -> path.toFile().isFile())
                    .filter(path -> path.toString().endsWith(".java"))
                    .map(this::toClassFromPath)
                    .filter(clazz -> clazz.getAnnotation(Peanut.class) != null)
                    .collect(Collectors.toUnmodifiableList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Class<?> toClassFromPath(Path path) {
        try {
            String simpleClassPath = path.toString()
                    .replace(PROJECT_JAVA_PATH, "")
                    .replace(JAVA_EXTENSION, "")
                    .replace(File.separator, ".");
            return Class.forName(simpleClassPath);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
