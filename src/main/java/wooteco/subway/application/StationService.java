package wooteco.subway.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.subway.dao.StationDao;
import wooteco.subway.domain.Station;
import wooteco.subway.exception.DuplicateException;
import wooteco.subway.exception.NotExistException;

@Service
@Transactional
public class StationService {

    public StationService() {
    }

    public Station save(String name) {
        if (StationDao.existByName(name)) {
            throw new DuplicateException();
        }
        return StationDao.save(new Station(name));
    }

    public void deleteById(Long id) {
        if (!StationDao.existById(id)) {
            throw new NotExistException();
        }
        StationDao.deleteById(id);
    }
}
