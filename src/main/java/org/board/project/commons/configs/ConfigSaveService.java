package org.board.project.commons.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.board.project.entities.Configs;
import org.board.project.repositories.ConfigsRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigSaveService {

    private final ConfigsRepository repository;

    public <T> void save(String code, T value) { //들어올 수 있는 객체가 다양하므로 generic class 사용

        //(없으면)추가 및 (있으면)수정
        Configs configs = repository.findById(code).orElseGet(Configs::new);

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        String json = null;
        try {
            json = om.writeValueAsString(value); //getter를 가져와서 문자열로 변형
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }

        configs.setCode(code);
        configs.setValue(json);

        repository.saveAndFlush(configs);
    }
}
