package code.dan.organization.service;

import code.dan.organization.model.request.FindByIdRequest;
import code.dan.organization.model.response.EmployeeListResponse;
import code.dan.organization.model.response.EmployeeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetEmployeeListByManagerIdService implements BaseService<FindByIdRequest, EmployeeListResponse>{

    private final EmployeeListResponse defineEmployees;

    @Override
    public EmployeeListResponse execute(FindByIdRequest input) {
        log.info("GetEmployeeListByManagerIdService [start]");
        List<EmployeeResponse> employeeResponseList = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(input.getId())) {
            employeeResponseList = defineEmployees.getEmployeeResponseList().stream()
                    .filter(employeeResponse -> ObjectUtils.isNotEmpty(employeeResponse.getManagerId())
                            && employeeResponse.getManagerId().compareTo(input.getId()) == 0)
                    .peek(employee->log.info("employee id = {} report directly to = {}",employee.getEmployeeId(),input.getId()))
                    .collect(Collectors.toList());
        }
        if(CollectionUtils.isEmpty(employeeResponseList)){
            employeeResponseList = Collections.emptyList();
        }
        log.info("GetEmployeeListByManagerIdService [end]");
        return EmployeeListResponse.builder()
                .employeeResponseList(employeeResponseList)
                .build();
    }

}
