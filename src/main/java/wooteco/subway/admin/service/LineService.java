package wooteco.subway.admin.service;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
import org.springframework.stereotype.Service;
import wooteco.subway.admin.domain.Line;
import wooteco.subway.admin.domain.LineStation;
import wooteco.subway.admin.domain.Station;
import wooteco.subway.admin.dto.LineResponse;
import wooteco.subway.admin.dto.LineStationCreateRequest;
import wooteco.subway.admin.repository.LineRepository;
import wooteco.subway.admin.repository.StationRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class LineService {
    private final LineRepository lineRepository;
    private final StationRepository stationRepository;

    public LineService(final LineRepository lineRepository, final StationRepository stationRepository) {
        this.lineRepository = lineRepository;
        this.stationRepository = stationRepository;
    }

    public Line save(Line line) {
        if(isDuplicateName(line)) {
            throw new IllegalArgumentException("중복된 이름입니다!");
        }
        return lineRepository.save(line);
    }

    public List<Line> showLines() {
        return lineRepository.findAll();
    }

    public Line updateLine(Long id, Line line) {
        Line persistLine = findById(id);
        persistLine.update(line);
        return lineRepository.save(persistLine);
    }

    public void deleteLineById(Long id) {
        lineRepository.deleteById(id);
    }

    private boolean isDuplicateName(Line line) {
        return lineRepository.findAllName().stream()
                .anyMatch(lineName -> lineName.equals(line.getName()));
    }

    public Line addLineStation(Long id, LineStation lineStation) {
        Line persistLine = findById(id);
        persistLine.addLineStation(lineStation);
        return lineRepository.save(persistLine);
    }

    public void removeLineStation(Long lineId, Long stationId) {
        Line line = findById(lineId);
        line.removeLineStationById(stationId);
        updateLine(lineId, line);
    }

    public LineResponse findLineWithStationsById(Long id) {
        Line line = findById(id);
        List<Long> lineStationsId = line.getLineStationsId();
        Set<Station> stations = stationRepository.findAllById(lineStationsId);

        return LineResponse.of(line ,stations);
    }

    public Line findById(Long id){
        return lineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("노선을 찾을수 없습니다."));
    }
}
