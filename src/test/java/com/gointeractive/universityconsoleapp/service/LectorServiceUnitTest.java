package com.gointeractive.universityconsoleapp.service;

import com.gointeractive.universityconsoleapp.dto.LectorSearchDTO;
import com.gointeractive.universityconsoleapp.entity.Lector;
import com.gointeractive.universityconsoleapp.entity.enums.Degree;
import com.gointeractive.universityconsoleapp.mapper.LectorMapper;
import com.gointeractive.universityconsoleapp.repository.LectorRepository;
import com.gointeractive.universityconsoleapp.service.impl.LectorServiceImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LectorServiceUnitTest {

    @Mock
    LectorRepository lectorRepository;

    @Mock
    LectorMapper lectorMapper;

    @InjectMocks
    LectorServiceImpl lectorService;

    @Test
    public void testSearchLectorsByTemplate_Success() {
        String template = "Ivan";

        Lector lector1 = new Lector();
        lector1.setId(1L);
        lector1.setName("Ivan Ivanov");
        lector1.setDegree(Degree.ASSOCIATE_PROFESSOR);

        Lector lector2 = new Lector();
        lector2.setId(2L);
        lector2.setName("Ivan Petrov");
        lector2.setDegree(Degree.PROFESSOR);

        List<Lector> lectors = List.of(lector1, lector2);

        when(lectorRepository.searchLectorsByKeyword(template)).thenReturn(lectors);
        when(lectorMapper.toSearchDto(lector1)).thenReturn(LectorSearchDTO.builder()
                .id(1L)
                .name("Ivan Ivanov")
                .degree(Degree.ASSOCIATE_PROFESSOR)
                .build());

        when(lectorMapper.toSearchDto(lector2)).thenReturn(LectorSearchDTO.builder()
                .id(2L)
                .name("Ivan Petrov")
                .degree(Degree.PROFESSOR)
                .build());

        List<LectorSearchDTO> result = lectorService.searchLectorsByTemplate(template);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Ivan Ivanov", result.get(0).getName());
        assertEquals("Ivan Petrov", result.get(1).getName());

        verify(lectorRepository, times(1)).searchLectorsByKeyword(template);
        verify(lectorMapper, times(2)).toSearchDto(any(Lector.class));
    }

    @Test
    public void testSearchLectorsByTemplate_NoResults() {
        String template = "NonExistingName";
        when(lectorRepository.searchLectorsByKeyword(template)).thenReturn(List.of());

        List<LectorSearchDTO> result = lectorService.searchLectorsByTemplate(template);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(lectorRepository, times(1)).searchLectorsByKeyword(template);
        verifyNoInteractions(lectorMapper);
    }

}