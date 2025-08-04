package domain.wisesaying.repository;

import domain.wisesaying.entity.WiseSaying;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;

public class WiseSayingRepository {

    private int lastId = 0;
    private final List<WiseSaying> wiseSayingList = new ArrayList<>();

    public void delete(WiseSaying wiseSaying) {
        wiseSayingList.remove(wiseSaying);
    }

    public WiseSaying save(WiseSaying wiseSaying) {
        LocalDateTime now = now();
        if (wiseSaying.isNew()) {
            wiseSaying.setCreateDateTime(now);
            wiseSaying.setModifiedDateTime(now);
            wiseSaying.setId(++lastId);
            wiseSayingList.add(wiseSaying);
            return wiseSaying;
        }
        wiseSaying.setModifiedDateTime(now);
        return wiseSaying;
    }

    public WiseSaying findById(int id) {
        return wiseSayingList.stream()
                .filter(ws -> ws.getId() == id)
                .findFirst()
                .orElseGet(() -> null);
    }

    public List<WiseSaying> getWiseSayingList() {
        return new ArrayList<>(wiseSayingList);
    }
}
