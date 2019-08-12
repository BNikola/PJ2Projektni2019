package classes.domain.extras;

public enum Height {
    VERY_LOW   (0),
    LOW        (1),
    MEDIUM     (2),
    HIGH       (3),
    VERY_HIGH  (4);

    private final Integer height;

    Height(Integer height) {
        this.height = height;
    }

    public Integer getHeight() {
        return height;
    }
}
