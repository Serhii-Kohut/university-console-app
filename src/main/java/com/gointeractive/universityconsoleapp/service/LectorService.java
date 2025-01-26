package com.gointeractive.universityconsoleapp.service;

import com.gointeractive.universityconsoleapp.dto.LectorSearchDTO;

import java.util.List;

public interface LectorService {

    List<LectorSearchDTO> searchLectorsByTemplate(String template);

}
