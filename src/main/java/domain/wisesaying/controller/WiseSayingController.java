package domain.wisesaying.controller;

import Request.Rq;
import domain.wisesaying.entity.WiseSaying;
import domain.wisesaying.service.WiseSayingService;

import java.util.List;
import java.util.Scanner;

public class WiseSayingController {
    private final Scanner scanner;
    private final WiseSayingService wiseSayingService;

    public WiseSayingController(Scanner scanner, WiseSayingService wiseSayingService) {
        this.scanner = scanner;
        this.wiseSayingService = wiseSayingService;
    }

    public void actionWrite() {
        System.out.print("명언: ");
        String content = scanner.nextLine().trim();

        System.out.print("작가: ");
        String author = scanner.nextLine().trim();

        WiseSaying wiseSaying = wiseSayingService.write(author, content);

        System.out.printf("%d번 명언이 등록되었습니다.%n", wiseSaying.getId());
    }

    public void actionList() {
        System.out.println("번호 / 작가 / 명언 / 작성 / 수정");
        System.out.println("----------------------");
        List<WiseSaying> wiseSayingList = wiseSayingService.getWiseSayingList();

        for (int i = wiseSayingList.size() - 1; i >= 0; i--) {
            WiseSaying wiseSaying = wiseSayingList.get(i);
            System.out.printf("%d / %s / %s / %s / %s%n", wiseSaying.getId(), wiseSaying.getAuthor(), wiseSaying.getContent(), wiseSaying.getCreateDateTime(), wiseSaying.getModifiedDateTime());
        }
    }

    public void actionDelete(Rq rq) {
        int id = rq.getParamAsInt("id", -1);

        if (id == -1) {
            System.out.println("숫자를 입력해주세요.");
            return;
        }

        WiseSaying wiseSaying = wiseSayingService.findById(id);

        if (wiseSaying == null) {
            System.out.println("해당 아이디는 존재하지 않습니다.");
            return;
        }

        wiseSayingService.delete(wiseSaying);

        System.out.printf("%d번 명언이 삭제되었습니다.%n", id);
    }

    public void actionModify(Rq rq) {
        int id = rq.getParamAsInt("id", -1);

        if (id < -1) {
            System.out.println("숫자를 입력해주세요.");
            return;
        }

        WiseSaying wiseSaying = wiseSayingService.findById(id);

        if (wiseSaying == null) {
            System.out.println("해당 아이디는 존재하지 않습니다.");
            return;
        }

        System.out.printf("명언(기존) : %s\n", wiseSaying.getContent());
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();

        System.out.printf("작가(기존) : %s\n", wiseSaying.getAuthor());
        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();

        wiseSayingService.modify(wiseSaying, content, author);

        System.out.printf("%d번 명언이 수정 되었습니다.%n", id);
    }

}
