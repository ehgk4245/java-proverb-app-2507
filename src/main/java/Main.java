import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner input = new Scanner(System.in);
    private static final List<WiseSaying> WISE_SAYINGS = new ArrayList<>();
    private static Long id;

    public static void main(String[] args) {
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
                String[] split = cmd.split("=");
                deleteWiseSaying(Long.parseLong(split[1]));
            } else if (cmd.contains("수정?id=")) {
                String[] split = cmd.split("=");
                updateWiseSaying(Long.parseLong(split[1]));
            } else {
                System.out.println("명령어를 다시 입력 해 주세요.");
            }

        }

        input.close();
    }

    private static void init() {
        FileUploader fileUploader = new FileUploader();
        List<WiseSaying> loaded = fileUploader.saveWiseSayings();
        WISE_SAYINGS.addAll(loaded);
        id = fileUploader.loadLastId();
    }

    private static void showWiseSayings() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        for (WiseSaying wiseSaying : WISE_SAYINGS) {
            System.out.printf("%d / %s / %s \n", wiseSaying.getId(), wiseSaying.getAuthor(), wiseSaying.getContent());
        }
    }

    private static void createWiseSaying() {
        FileUploader fileUploader = new FileUploader();

        System.out.print("명언 : ");
        String content = input.nextLine();

        System.out.print("작가 : ");
        String author = input.nextLine();

        WiseSaying wiseSaying = new WiseSaying(++id, content, author);

        fileUploader.saveWiseSaying(wiseSaying);
        fileUploader.saveLastId(id);
        WISE_SAYINGS.add(wiseSaying);
        System.out.printf("%d번 명언이 등록되었습니다.\n", id);
    }

    private static void deleteWiseSaying(Long id) {
        FileUploader fileUploader = new FileUploader();
        fileUploader.removeWiseSaying(id);
        boolean isRemoved = WISE_SAYINGS.removeIf(wiseSaying -> wiseSaying.getId() == id);
        if (isRemoved) {
            System.out.printf("%d번 명언이 삭제되었습니다.\n", id);
            return;
        }
        System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
    }

    private static void updateWiseSaying(Long id) {
        FileUploader fileUploader = new FileUploader();
        WiseSaying findWiseSaying = WISE_SAYINGS.stream()
                .filter(wiseSaying -> wiseSaying.getId() == id)
                .findFirst()
                .orElse(null);
        if (findWiseSaying == null) {
            System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
            return;
        }
        System.out.printf("명언(기존) : %s\n", findWiseSaying.getContent());
        System.out.print("명언 : ");
        String content = input.nextLine();
        System.out.printf("작가(기존) : %s\n", findWiseSaying.getAuthor());
        System.out.print("작가 : ");
        String author = input.nextLine();
        findWiseSaying.setContent(content);
        findWiseSaying.setAuthor(author);
        fileUploader.saveWiseSaying(findWiseSaying);
        System.out.println("수정이 완료되었습니다.");
    }
}
