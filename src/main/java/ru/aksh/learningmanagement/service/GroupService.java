package ru.aksh.learningmanagement.service;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.aksh.learningmanagement.model.request.GroupRequest;
import ru.aksh.learningmanagement.model.response.GroupResponse;

public interface GroupService {
    GroupResponse createGroup(@Valid GroupRequest request);

    Page<GroupResponse> findAll(Pageable pageable);

    GroupResponse findById(Long id);

    void updateGroup(Long id, @Valid GroupRequest request);

    void deleteById(Long id);
}
