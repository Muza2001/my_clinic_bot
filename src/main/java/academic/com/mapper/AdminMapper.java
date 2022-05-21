package academic.com.mapper;

import academic.com.dto.request.AdminRequest;
import academic.com.dto.response.AdminResponse;
import academic.com.model.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    @Mapping(target = "expiration", expression = "java(java.time.Instant.now())")
    @Mapping(target = "enabled", expression = "java(true)")
    Admin toEntity(AdminRequest register);

    AdminResponse toDto(Admin admin);

}
