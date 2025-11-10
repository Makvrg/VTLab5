package com.example.entity;

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

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }

}
