package config;

import domain.system.controller.SystemController;
import domain.wisesaying.controller.WiseSayingController;
import domain.wisesaying.repository.WiseSayingRepository;
import domain.wisesaying.service.WiseSayingService;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AppContext {
    public static final Scanner scanner = new Scanner(System.in);
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static SystemController systemController = new SystemController();
    private static WiseSayingRepository wiseSayingRepository = new WiseSayingRepository();
    private static WiseSayingService wiseSayingService = new WiseSayingService(wiseSayingRepository);
    private static WiseSayingController wiseSayingController = new WiseSayingController(wiseSayingService);

    public static SystemController getSystemController() {
        return systemController;
    }

    public static WiseSayingRepository getWiseSayingRepository() {
        return wiseSayingRepository;
    }

    public static WiseSayingService getWiseSayingService() {
        return wiseSayingService;
    }

    public static WiseSayingController getWiseSayingController() {
        return wiseSayingController;
    }
}
