package org.board.project.commons.configs;

import lombok.RequiredArgsConstructor;
import org.board.project.entities.Configs;
import org.board.project.repositories.ConfigsRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigDeleteService {
    //엔티티 상태로 불러온 다음에 삭제한다.
    private final ConfigsRepository repository;

    public void delete(String code) {
        Configs configs = repository.findById(code).orElse(null);
        if (configs == null) {
            return;
        }

        repository.delete(configs);
        repository.flush();
    }
}