import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileUploader {
    private final static String DEFAULT_FILE_PATH = "db/wiseSaying/";
    private final static String LAST_ID_PATH = DEFAULT_FILE_PATH + "lastId.txt";

    public void saveProverb(Proverb proverb) {
        Long id = proverb.getId();
        String json = proverb.toJson();

        Path path = Path.of(DEFAULT_FILE_PATH + id + ".json");
        try {
            Files.writeString(path, json);
        } catch (IOException e) {
            System.err.println("파일 저장 중 오류 발생: " + e.getMessage());
        }
    }

    public Proverb loadProverb(Long id) {
        Path path = Path.of(DEFAULT_FILE_PATH + id + ".json");
        try {
            String json = Files.readString(path);
            return Proverb.fromJson(json);
        } catch (IOException e) {
            System.err.println("파일 로드 중 오류 발생: " + e.getMessage());
            return null;
        }
    }

    public List<Proverb> loadProverbs() {
        try {
            return
                    Files.list(Path.of(DEFAULT_FILE_PATH))
                            .filter(path -> path.toString().endsWith(".json"))
                            .map(path -> {
                                try {
                                    String json = Files.readString(path);
                                    return Proverb.fromJson(json);
                                } catch (IOException e) {
                                    System.err.println("파일 로드 중 오류 발생: " + e.getMessage());
                                    return null;
                                }
                            })
                            .filter(Objects::nonNull)
                            .toList();
        } catch (IOException e) {
            System.err.println("파일 로드 중 오류 발생: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveLastId(Long id) {
        Path path = Path.of(LAST_ID_PATH);
        try {
            Files.writeString(path, String.valueOf(id));
        } catch (IOException e) {
            System.err.println("파일 저장 중 오류 발생: " + e.getMessage());
        }
    }

    public Long loadLastId() {
        Path path = Path.of(LAST_ID_PATH);
        try {
            String id = Files.readString(path);
            return Long.valueOf(id);
        } catch (IOException e) {
            System.err.println("파일 로드 중 오류 발생: " + e.getMessage());
            return 0L;
        }
    }

    public void removeProverb(Long id) {
        Path path = Path.of(DEFAULT_FILE_PATH + id + ".json");
        try {
            boolean deleted = Files.deleteIfExists(path);
            if (!deleted) {
                System.err.println(id + "번 명언 파일이 존재하지 않습니다.");
            }
        } catch (IOException e) {
            System.err.println("파일 삭제 중 오류 발생: " + e.getMessage());
        }
    }
}
