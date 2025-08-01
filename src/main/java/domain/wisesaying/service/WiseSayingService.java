package domain.wisesaying.service;

import domain.wisesaying.entity.WiseSaying;
import domain.wisesaying.repository.WiseSayingRepository;

import java.util.List;

public class WiseSayingService {

    private final WiseSayingRepository wiseSayingRepository;

    public WiseSayingService(WiseSayingRepository wiseSayingRepository) {
        this.wiseSayingRepository = wiseSayingRepository;
    }

    public WiseSaying write(String author, String content) {
        return wiseSayingRepository.write(author, content);
    }

    public void delete(WiseSaying wiseSaying) {
        wiseSayingRepository.delete(wiseSaying);
    }

    public void modify(WiseSaying wiseSaying, String content, String author) {
        wiseSayingRepository.modify(wiseSaying, content, author);
    }

    public WiseSaying findById(int id) {
        return wiseSayingRepository.findById(id);
    }

    public List<WiseSaying> getWiseSayingList() {
        return wiseSayingRepository.getWiseSayingList();
    }
}
