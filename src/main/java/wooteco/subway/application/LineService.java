package wooteco.subway.application;

import wooteco.subway.dao.LineDao;
import wooteco.subway.di.annotaion.Peanut;
import wooteco.subway.di.annotaion.PeanutLifeCycle;
import wooteco.subway.domain.Line;
import wooteco.subway.exception.DuplicateException;
import wooteco.subway.exception.NotExistException;

@Peanut(PeanutLifeCycle.REQUEST)
public class LineService {

    public Line save(String name, String color) {
        if(LineDao.existByName(name)) {
            throw new DuplicateException();
        }
        return LineDao.save(new Line(name, color));
    }

    public Line findById(Long id) {
        return LineDao.findById(id)
                .orElseThrow(NotExistException::new);
    }

    public Line update(Long id, String name, String color) {
        return LineDao.update(new Line(id, name, color));
    }
}
