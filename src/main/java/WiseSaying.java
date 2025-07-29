public class WiseSaying {

    private Long id;
    private String content;
    private String author;

    public WiseSaying(Long id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String toJson() {
        return "{\n" +
                "  \"id\": " + id + ",\n" +
                "  \"content\": \"" + content + "\",\n" +
                "  \"author\": \"" + author + "\"\n" +
                "}";
    }

    public static WiseSaying fromJson(String json) {
        Long id = null;
        String content = null;
        String author = null;

        String[] lines = json.replace("{", "")
                .replace("}", "")
                .trim()
                .split(",");

        for (String line : lines) {
            String[] pair = line.trim().split(":", 2);
            if (pair.length != 2) continue;

            String key = pair[0].trim().replace("\"", "");
            String value = pair[1].trim();

            value = value.replaceAll("^\"|\"$", "");

            switch (key) {
                case "id" -> id = Long.parseLong(value);
                case "content" -> content = value;
                case "author" -> author = value;
            }
        }

        return new WiseSaying(id, content, author);
    }
}
