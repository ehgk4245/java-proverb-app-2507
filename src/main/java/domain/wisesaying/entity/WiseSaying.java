package domain.wisesaying.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WiseSaying {

    private int id;
    private String content;
    private String author;
    private LocalDateTime createDateTime;
    private LocalDateTime modifyDateTime;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public WiseSaying(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getCreateDateTime() {
        return createDateTime.format(FORMATTER);
    }

    public String getModifiedDateTime() {
        return modifyDateTime.format(FORMATTER);
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public void setModifiedDateTime(LocalDateTime modifiedDateTime) {
        this.modifyDateTime = modifiedDateTime;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isNew() {
        return createDateTime == null;
    }
}
