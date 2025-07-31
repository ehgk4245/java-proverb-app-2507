import domain.wisesaying.entity.WiseSaying;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final Scanner input = new Scanner(System.in);
    private static final List<WiseSaying> WISE_SAYINGS = new ArrayList<>();
    private static Long id;

    public void run() {
        init();

        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String cmd = input.nextLine().trim();

            if (cmd.equals("종료")) {
                break;
            } else if (cmd.equals("등록")) {
                createWiseSaying();
            } else if (cmd.equals("목록")) {
                showWiseSayings();
            } else if (cmd.contains("삭제?id=")) {
                deleteWiseSaying(cmd);
            } else if (cmd.contains("수정?id=")) {
                updateWiseSaying(cmd);
            } else if (cmd.equals("빌드")) {
                build();
            } else {
                System.out.println("명령어를 다시 입력 해 주세요.");
            }

        }

        input.close();
    }

    private void init() {
        FileUploader fileUploader = new FileUploader();
        List<WiseSaying> loaded = fileUploader.loadWiseSayings();
        WISE_SAYINGS.addAll(loaded);
        id = fileUploader.loadLastId();
    }

    private void showWiseSayings() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        sortWiseSayings();
        for (WiseSaying wiseSaying : WISE_SAYINGS) {
            System.out.printf("%d / %s / %s \n", wiseSaying.getId(), wiseSaying.getAuthor(), wiseSaying.getContent());
        }
    }

    private static void sortWiseSayings() {
        WISE_SAYINGS.sort((a, b) -> Long.compare(b.getId(), a.getId()));
    }

    private void createWiseSaying() {
        System.out.print("명언 : ");
        String content = input.nextLine();

        System.out.print("작가 : ");
        String author = input.nextLine();

        create(content, author);
        System.out.printf("%d번 명언이 등록되었습니다.\n", id);
    }

    private void create(String content, String author) {
        FileUploader fileUploader = new FileUploader();
        WiseSaying wiseSaying = new WiseSaying(++id, content, author);

        fileUploader.saveWiseSaying(wiseSaying);
        fileUploader.saveLastId(id);
        WISE_SAYINGS.add(wiseSaying);
    }

    private void deleteWiseSaying(String cmd) {
        String[] split = cmd.split("=");
        Long id = Long.parseLong(split[1]);
        boolean isRemoved = delete(id);
        if (isRemoved) {
            System.out.printf("%d번 명언이 삭제되었습니다.\n", id);
            return;
        }
        System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
    }

    private boolean delete(Long id) {
        FileUploader fileUploader = new FileUploader();
        fileUploader.removeWiseSaying(id);
        return WISE_SAYINGS.removeIf(wiseSaying -> wiseSaying.getId() == id);
    }

    private void updateWiseSaying(String cmd) {
        String[] split = cmd.split("=");
        Long id = Long.parseLong(split[1]);
        WiseSaying findWiseSaying = findById(id);
        if (findWiseSaying == null) {
            return;
        }
        System.out.printf("명언(기존) : %s\n", findWiseSaying.getContent());
        System.out.print("명언 : ");
        String content = input.nextLine();
        System.out.printf("작가(기존) : %s\n", findWiseSaying.getAuthor());
        System.out.print("작가 : ");
        String author = input.nextLine();
        update(findWiseSaying, content, author);
        System.out.println("수정이 완료되었습니다.");
    }

    private void update(WiseSaying findWiseSaying, String content, String author) {
        FileUploader fileUploader = new FileUploader();

        findWiseSaying.setContent(content);
        findWiseSaying.setAuthor(author);
        fileUploader.saveWiseSaying(findWiseSaying);
    }

    private WiseSaying findById(Long id) {
        return WISE_SAYINGS.stream()
                .filter(wiseSaying -> wiseSaying.getId() == id)
                .findFirst()
                .orElseGet(() -> {
                    System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
                    return null;
                });
    }

    private void build() {
        FileUploader fileUploader = new FileUploader();
        fileUploader.saveWiseSayings(WISE_SAYINGS);
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }
}
