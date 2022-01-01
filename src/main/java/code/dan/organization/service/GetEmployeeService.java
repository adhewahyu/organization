package code.dan.organization.service;

import code.dan.organization.model.request.EmptyRequest;
import code.dan.organization.model.response.EmployeeListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetEmployeeService implements BaseService<EmptyRequest, EmployeeListResponse> {

    private final EmployeeListResponse defineEmployees;

    @Override
    public EmployeeListResponse execute(EmptyRequest input) {
        log.info("GetEmployeeService [called]");
        return EmployeeListResponse.builder()
                .employeeResponseList(defineEmployees.getEmployeeResponseList())
                .build();
    }
}
