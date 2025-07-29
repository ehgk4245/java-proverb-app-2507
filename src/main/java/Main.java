import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner input = new Scanner(System.in);
    private static final List<Proverb> proverbs = new ArrayList<>();
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
                createProverb();
            } else if (cmd.equals("목록")) {
                showProverbs();
            } else if (cmd.contains("삭제?id=")) {
                String[] split = cmd.split("=");
                deleteProverb(Long.parseLong(split[1]));
            } else if (cmd.contains("수정?id=")) {
                String[] split = cmd.split("=");
                updateProverb(Long.parseLong(split[1]));
            } else {
                System.out.println("명령어를 다시 입력 해 주세요.");
            }

        }

        input.close();
    }

    private static void init() {
        FileUploader fileUploader = new FileUploader();
        List<Proverb> loaded = fileUploader.loadProverbs();
        proverbs.addAll(loaded);
        id = fileUploader.loadLastId();
    }

    private static void showProverbs() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        for (Proverb proverb : proverbs) {
            System.out.printf("%d / %s / %s \n", proverb.getId(), proverb.getAuthor(), proverb.getContent());
        }
    }

    private static void createProverb() {
        FileUploader fileUploader = new FileUploader();

        System.out.print("명언 : ");
        String content = input.nextLine();

        System.out.print("작가 : ");
        String author = input.nextLine();

        Proverb proverb = new Proverb(++id, content, author);

        fileUploader.saveProverb(proverb);
        fileUploader.saveLastId(id);
        proverbs.add(proverb);
        System.out.printf("%d번 명언이 등록되었습니다.\n", id);
    }

    private static void deleteProverb(Long id) {
        FileUploader fileUploader = new FileUploader();
        fileUploader.removeProverb(id);
        boolean isRemoved = proverbs.removeIf(proverb -> proverb.getId() == id);
        if (isRemoved) {
            System.out.printf("%d번 명언이 삭제되었습니다.\n", id);
            return;
        }
        System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
    }

    private static void updateProverb(Long id) {
        FileUploader fileUploader = new FileUploader();
        Proverb findProverb = proverbs.stream()
                .filter(proverb -> proverb.getId() == id)
                .findFirst()
                .orElse(null);
        if (findProverb == null) {
            System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
            return;
        }
        System.out.printf("명언(기존) : %s\n", findProverb.getContent());
        System.out.print("명언 : ");
        String content = input.nextLine();
        System.out.printf("작가(기존) : %s\n", findProverb.getAuthor());
        System.out.print("작가 : ");
        String author = input.nextLine();
        findProverb.setContent(content);
        findProverb.setAuthor(author);
        fileUploader.saveProverb(findProverb);
        System.out.println("수정이 완료되었습니다.");
    }
}
