package ru.gb.catalogservice.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultOperation {
    private String resultDescription;
    private boolean result;
}
