package wooteco.subway.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.subway.dao.StationDao;
import wooteco.subway.domain.Station;
import wooteco.subway.exception.constant.DuplicateException;
import wooteco.subway.exception.constant.NotExistException;

import java.util.List;

@Service
@Transactional
public class StationService {

    private final StationDao stationDao;

    public StationService(StationDao stationDao) {
        this.stationDao = stationDao;
    }

    public Station saveAndGet(String name) {
        if (stationDao.existByName(name)) {
            throw new DuplicateException();
        }
        long savedStationId = stationDao.save(new Station(name));
        return new Station(savedStationId, name);
    }

    public List<Station> findAll() {
        return stationDao.findAll();
    }

    public void deleteById(Long id) {
        if (!stationDao.existById(id)) {
            throw new NotExistException();
        }
        stationDao.deleteById(id);
    }
}
