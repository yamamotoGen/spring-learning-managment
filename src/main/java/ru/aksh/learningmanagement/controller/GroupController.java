package ru.aksh.learningmanagement.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.aksh.learningmanagement.model.request.GroupRequest;
import ru.aksh.learningmanagement.model.response.GroupResponse;
import ru.aksh.learningmanagement.service.GroupService;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/groups")
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/create")
    public GroupResponse createGroup(@Valid @RequestBody GroupRequest request) {
        return groupService.createGroup(request);
    }

    @GetMapping("/all")
    public Page<GroupResponse> findAllGroups(
            @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,
            @RequestParam(value = "size", defaultValue = "20") @Min(1) @Max(100) Integer size,
            @RequestParam(value = "sort", defaultValue = "id") String sort) {
        return groupService.findAll(PageRequest.of(page, size, Sort.by(sort)));
    }

    @GetMapping("/{id}")
    public GroupResponse findById(@PathVariable Long id) {
        return groupService.findById(id);
    }

    @PutMapping("/{id}")
    public void updateGroup(@PathVariable Long id, @Valid @RequestBody GroupRequest request) {
        groupService.updateGroup(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable Long id) {
        groupService.deleteById(id);
    }
}
