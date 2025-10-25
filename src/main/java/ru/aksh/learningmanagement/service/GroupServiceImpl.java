package ru.aksh.learningmanagement.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.aksh.learningmanagement.domain.Group;
import ru.aksh.learningmanagement.exception.GroupNotFoundException;
import ru.aksh.learningmanagement.mapper.GroupMapper;
import ru.aksh.learningmanagement.model.request.GroupRequest;
import ru.aksh.learningmanagement.model.response.GroupResponse;
import ru.aksh.learningmanagement.repository.GroupRepository;

@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Override
    public GroupResponse createGroup(GroupRequest request) {
        Group group = groupRepository.save(new Group(request.getGroupName()));
        return groupMapper.toGroupResponse(group);
    }

    @Override
    public Page<GroupResponse> findAll(Pageable pageable) {
        Page<Group> groups = groupRepository.findAll(pageable);
        return groupMapper.toGroupResponsePage(groups);
    }

    @Override
    public GroupResponse findById(Long id) {
        return groupMapper.toGroupResponse(getGroupById(id));
    }

    @Override
    public void updateGroup(Long id, GroupRequest request) {
        Group group = getGroupById(id);

        groupMapper.updateGroupRequest(group, request);
        groupRepository.save(group);
    }

    @Override
    public void deleteById(Long id) {
        groupRepository.delete(getGroupById(id));
    }

    private Group getGroupById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new GroupNotFoundException("Group id " + id + "is not found"));
    }
}
