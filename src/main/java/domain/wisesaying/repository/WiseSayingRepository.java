package domain.wisesaying.repository;

import domain.wisesaying.entity.WiseSaying;

import java.util.ArrayList;
import java.util.List;

public class WiseSayingRepository {

    private int lastId = 0;
    private final List<WiseSaying> wiseSayingList = new ArrayList<>();

    public WiseSaying write(String author, String content) {
        WiseSaying wiseSaying = new WiseSaying(++lastId, author, content);
        wiseSayingList.add(wiseSaying);
        return wiseSaying;
    }

    public void delete(WiseSaying wiseSaying) {
        wiseSayingList.remove(wiseSaying);
    }

    public void modify(WiseSaying wiseSaying, String content, String author) {
        wiseSaying.setContent(content);
        wiseSaying.setAuthor(author);
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
