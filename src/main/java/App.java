import Request.Rq;
import config.AppContext;
import domain.system.controller.SystemController;
import domain.wisesaying.controller.WiseSayingController;

import java.util.Scanner;

public class App {
    private final Scanner scanner = new Scanner(System.in);

    void run() {
        System.out.println("== 명언 앱 ==");

        SystemController systemController = AppContext.getSystemController();
        WiseSayingController wiseSayingController = AppContext.getWiseSayingController();

        outer:
        while (true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine().trim();

            Rq rq = new Rq(cmd);

            switch (rq.getActionName()) {
                case "종료" -> {
                    systemController.actionExit();
                    break outer;
                }
                case "등록" -> wiseSayingController.actionWrite();
                case "목록" -> wiseSayingController.actionList();
                case "삭제" -> wiseSayingController.actionDelete(rq);
                case "수정" -> wiseSayingController.actionModify(rq);
                default -> System.out.println("명령어를 다시 입력해 주세요.");
            }
        }
        scanner.close();
    }
}
