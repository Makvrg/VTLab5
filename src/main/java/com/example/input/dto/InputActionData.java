package com.example.input.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class InputActionData {

    private String id;

    public InputActionData setId(String id) {
        this.id = id;
        return this;
    }

    public List<String> containsEmpty() {
        List<String> listOfEmptyFields = new ArrayList<>();
        if (id != null && id.isEmpty()) {
            listOfEmptyFields.add("id");
        }
        return listOfEmptyFields;
    }

}
