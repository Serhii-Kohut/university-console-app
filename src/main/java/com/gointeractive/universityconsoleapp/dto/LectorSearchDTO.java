package com.gointeractive.universityconsoleapp.dto;

import com.gointeractive.universityconsoleapp.entity.enums.Degree;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LectorSearchDTO {
    Long id;
    String name;
    Degree degree;
}
