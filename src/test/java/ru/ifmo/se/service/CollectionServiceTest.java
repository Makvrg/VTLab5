package ru.ifmo.se.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ifmo.se.entity.City;
import ru.ifmo.se.entity.Coordinates;
import ru.ifmo.se.entity.Government;
import ru.ifmo.se.entity.Human;
import ru.ifmo.se.io.output.formatter.OutputStringFormatter;
import ru.ifmo.se.repository.CollectionRepository;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CollectionServiceTest {

    @Mock
    private CollectionRepository collectionRepository;
    @Mock
    private OutputStringFormatter formatter;
    @InjectMocks
    private CollectionService collectionService;

    @Test
    void head_getExistsCity() {
        Optional<City> existsCity = Optional.of(
                new City(
                        Long.valueOf(34L),
                        "New York",
                        new Coordinates(
                                23.9,
                                -8.2f
                        ),
                        new Date(),
                        Long.valueOf(56036780L),
                        Integer.valueOf(34569),
                        Float.valueOf(45.8f),
                        358L,
                        Integer.valueOf(2638),
                        Government.ANARCHY,
                        new Human(
                                1.79,
                                new Date()
                        )
                )
        );
        when(collectionRepository.findFirst()).thenReturn(existsCity);
        Assertions.assertEquals(existsCity, collectionService.head());
    }
}
