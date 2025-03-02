package wooteco.subway.domain;

import wooteco.subway.exception.EmptyNameException;

import java.util.Objects;

public class Station {
    private Long id;
    private String name;

    public Station(Long id, String name) {
        if (name.isBlank()) {
            throw new EmptyNameException();
        }

        this.id = id;
        this.name = name;
    }

    public Station(String name) {
        this(null, name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public boolean isSameName(String name) {
        return this.name.equals(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(id, station.id) && Objects.equals(name, station.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

