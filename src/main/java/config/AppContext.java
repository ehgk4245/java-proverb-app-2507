package config;

import domain.system.controller.SystemController;
import domain.wisesaying.controller.WiseSayingController;
import domain.wisesaying.repository.WiseSayingRepository;
import domain.wisesaying.service.WiseSayingService;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AppContext {
    public static final Scanner scanner;
    public static final DateTimeFormatter FORMATTER;

    public static final SystemController systemController;
    public static final WiseSayingRepository wiseSayingRepository;
    public static final WiseSayingService wiseSayingService;
    public static final WiseSayingController wiseSayingController;

    static {
        scanner = new Scanner(System.in);
        FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        systemController = new SystemController();
        wiseSayingRepository = new WiseSayingRepository();
        wiseSayingService = new WiseSayingService(wiseSayingRepository);
        wiseSayingController = new WiseSayingController(wiseSayingService);
    }
}
