package Enums;

public enum Language {
    RUSSIAN("Русский", 0),
    ENGLISH("English",1);

    private int id;
    private String description;

    Language(String description, int id) {
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public static Language getByStr(String description) {
        for (Language entry : Language.values())
            if (entry.description.equals(description))
                return entry;
        return null;
    }
}
