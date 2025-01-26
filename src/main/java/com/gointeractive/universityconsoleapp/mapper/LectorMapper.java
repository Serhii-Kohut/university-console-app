package com.gointeractive.universityconsoleapp.mapper;

import com.gointeractive.universityconsoleapp.dto.LectorDTO;
import com.gointeractive.universityconsoleapp.dto.LectorSearchDTO;
import com.gointeractive.universityconsoleapp.entity.Lector;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface LectorMapper {
    LectorDTO toDto(Lector lector);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "degree", target = "degree")
    LectorSearchDTO toSearchDto(Lector lector);
}