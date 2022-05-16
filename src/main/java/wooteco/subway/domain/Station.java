package wooteco.subway.domain;

import lombok.Getter;
import lombok.Setter;
import wooteco.subway.exception.constant.BlankArgumentException;

import java.util.Objects;

import static wooteco.subway.util.StringUtils.isBlank;

@Getter
@Setter
public class Station {

    private Long id;
    private String name;

    private Station() {
    }

    public Station(Long id) {
        this(id, null);
    }

    public Station(String name) {
        this(null, name);
    }

    public Station(Long id, String name) {
        if (isBlank(name)) {
            throw new BlankArgumentException();
        }
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

