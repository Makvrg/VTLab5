package com.example.entity;

import lombok.Getter;

@Getter
public enum Government {
    ARISTOCRACY("Аристократия"),
    ANARCHY("Анархия"),
    MONARCHY("Монархия"),
    OLIGARCHY("Олигархия"),
    TOTALITARIANISM("Тоталитаризм");

    private final String title;

    Government(String title) {
        this.title = title;
    }

    public static Government fromString(String text) {
        if (text == null) {
            return null;
        }

        for (Government government : Government.values()) {
            if (government.title.equals(text)) {
                return government;
            }
        }
        throw new IllegalArgumentException("Неизвестное значение: " + text);
    }

    @Override
    public String toString() {
        return title;
    }

}
