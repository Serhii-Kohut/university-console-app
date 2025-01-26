package com.gointeractive.universityconsoleapp.dto;

import com.gointeractive.universityconsoleapp.entity.enums.Degree;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LectorDTO {
    Long id;
    String name;
    Degree degree;
}
