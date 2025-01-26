package com.gointeractive.universityconsoleapp.service.impl;

import com.gointeractive.universityconsoleapp.dto.LectorSearchDTO;
import com.gointeractive.universityconsoleapp.entity.Lector;
import com.gointeractive.universityconsoleapp.mapper.LectorMapper;
import com.gointeractive.universityconsoleapp.repository.LectorRepository;
import com.gointeractive.universityconsoleapp.service.LectorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LectorServiceImpl implements LectorService {

    LectorRepository lectorRepository;
    LectorMapper lectorMapper;

    @Override
    public List<LectorSearchDTO> searchLectorsByTemplate(String template) {
        log.info("Searching lectors by template: '{}'", template);

        List<Lector> lectors = lectorRepository.searchLectorsByKeyword(template);

        if (lectors.isEmpty()) {
            log.warn("No lectors found for template: '{}'", template);
        } else {
            lectors.forEach(lector -> log.info("Found lector: {}", lector.getName()));
        }

        List<LectorSearchDTO> dtoList = lectors.stream()
                .map(lectorMapper::toSearchDto)
                .collect(Collectors.toList());

        dtoList.forEach(dto -> log.info("Mapped DTO: id={}, name={}", dto.getId(), dto.getName()));

        return dtoList;
    }

}