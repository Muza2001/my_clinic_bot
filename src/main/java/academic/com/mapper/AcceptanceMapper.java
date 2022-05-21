package academic.com.mapper;

import academic.com.dto.request.AcceptanceRequest;
import academic.com.dto.response.AcceptanceResponse;
import academic.com.dto.response.EmployeeResponse;
import academic.com.model.Acceptance;
import academic.com.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Random;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface AcceptanceMapper {

    @Mapping(target = "clientId", expression = "java(generateId())")
    @Mapping(target = "employees", source = "employees")
    @Mapping(target = "serviceType", source = "serviceType")
    Acceptance toEntity(AcceptanceRequest acceptanceRequest, Set<Employee> employees, String serviceType);

    default Long generateId(){
        Random random = new Random();
        return (long) random.nextInt(999999);
    }

    @Mapping(target = "employees", source = "employeeResponses")
    AcceptanceResponse toResponse(Acceptance acceptance, Set<EmployeeResponse> employeeResponses);

}
