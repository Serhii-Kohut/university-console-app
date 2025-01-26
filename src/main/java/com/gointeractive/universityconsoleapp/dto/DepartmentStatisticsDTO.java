package com.gointeractive.universityconsoleapp.dto;

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
public class DepartmentStatisticsDTO {
    Long departmentId;
    String departmentName;
    Long assistantsCount;
    Long associateProfessorsCount;
    Long professorsCount;
}
