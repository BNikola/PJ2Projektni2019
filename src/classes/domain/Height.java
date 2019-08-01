package classes.domain;

public enum Height {
    VERY_LOW   (300),
    LOW        (400),
    MEDIUM     (500),
    HIGH       (600),
    VERY_HIGH  (700);

    private final Integer height;

    Height(Integer height) {
        this.height = height;
    }

    public Integer getHeight() {
        return height;
    }
}
