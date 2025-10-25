package ru.aksh.learningmanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import ru.aksh.learningmanagement.domain.Group;
import ru.aksh.learningmanagement.model.request.GroupRequest;
import ru.aksh.learningmanagement.model.response.GroupResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GroupMapper {
    GroupResponse toGroupResponse(Group group);

    default Page<GroupResponse> toGroupResponsePage(Page<Group> groups) {
        return groups.map(this::toGroupResponse);
    }

    @Mapping(target = "id", ignore = true)
    void updateGroupRequest(@MappingTarget Group group, GroupRequest request);
}
