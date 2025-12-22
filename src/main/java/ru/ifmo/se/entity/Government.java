package ru.ifmo.se.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Government {

    ARISTOCRACY("Аристократия"),
    ANARCHY("Анархия"),
    MONARCHY("Монархия"),
    OLIGARCHY("Олигархия"),
    TOTALITARIANISM("Тоталитаризм");

    private final String title;

    public static Government fromRussianString(String russianName) {
        if (russianName == null) {
            throw new IllegalArgumentException("Передано пустое значение");
        }
        for (Government government : Government.values()) {
            if (government.title.equals(russianName)) {
                return government;
            }
        }
        throw new IllegalArgumentException("Неизвестное значение: " + russianName);
    }

    @Override
    public String toString() {
        return title;
    }
}
