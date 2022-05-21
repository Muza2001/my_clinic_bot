package academic.com.mapper;

import academic.com.dto.request.EmployeeRequest;
import academic.com.dto.response.EmployeeResponse;
import academic.com.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {


    EmployeeResponse toDto(Employee employee);

    Set<EmployeeResponse> toResponseList(Set<Employee> employees);

    @Mapping(target = "withdrawableMoney", ignore = true)
    Employee toEntity(EmployeeRequest register);

}
