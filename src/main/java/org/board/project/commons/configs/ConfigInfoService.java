package org.board.project.commons.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.board.project.entities.Configs;
import org.board.project.repositories.ConfigsRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ConfigInfoService {

    private final ConfigsRepository repository;

    public <T> T get(String code, Class<T> clazz) {
        return get(code, clazz, null);
    }

    public <T> T get(String code, TypeReference<T> typeReference) {
        return get(code, null, typeReference);
    }

    public <T> T get(String code, Class<T> clazz, TypeReference<T> typeReference) {
        // 단일 객체 일 때   // 여러 개의 객체일 때

            Configs config = repository.findById(code).orElse(null);
            if (config == null || StringUtils.hasText(config.getValue())) {
                return null;
            }

            //데이터가 있으면 가져옴
            String json = config.getValue();

            ObjectMapper om = new ObjectMapper();
            om.registerModule(new JavaTimeModule());

            try {
            T data = clazz == null ? om.readValue(json, typeReference) : om.readValue(json, clazz);
                return data;

            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
    }
}