package code.dan.organization.service;

import code.dan.organization.model.request.FindByIdAndReportingFlagRequest;
import code.dan.organization.model.response.EmployeeByIdResponse;
import code.dan.organization.model.response.EmployeeListResponse;
import code.dan.organization.model.response.EmployeeResponse;
import code.dan.organization.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetEmployeeByIdService implements BaseService<FindByIdAndReportingFlagRequest, EmployeeByIdResponse>{

    private final EmployeeListResponse defineEmployees;

    @Override
    public EmployeeByIdResponse execute(FindByIdAndReportingFlagRequest input) {
        log.info("GetEmployeeByIdService [start]");
        doValidateRequest(input);
        AtomicReference<EmployeeByIdResponse> employeeByIdResponse = new AtomicReference<>();
        defineEmployees.getEmployeeResponseList().stream()
                .filter(data -> data.getEmployeeId().compareTo(input.getId()) == 0)
                .findAny().ifPresentOrElse(data -> employeeByIdResponse.set(getEmployeeByIdResponse(data, input.getIncludeReportingFlag()))
                        , () -> {
                            log.error("Employee with id = {} not found", input.getId());
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERR_MSG_DATA_NOT_FOUND);
                        });
        log.info("GetEmployeeByIdService [end]");
        return employeeByIdResponse.get();
    }

    private void doValidateRequest(FindByIdAndReportingFlagRequest input){
        if(ObjectUtils.isEmpty(input.getId())){
            log.error("Id is empty");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_BAD_REQUEST);
        }
    }

    private EmployeeByIdResponse getEmployeeByIdResponse(EmployeeResponse employeeResponse,Boolean includeDirectReports){
        return EmployeeByIdResponse.builder()
                .employeeId(employeeResponse.getEmployeeId())
                .name(employeeResponse.getName())
                .manager(ObjectUtils.isNotEmpty(employeeResponse.getManagerId()) ? execute(FindByIdAndReportingFlagRequest.builder()
                        .id(employeeResponse.getManagerId())
                        .includeReportingFlag(includeDirectReports)
                        .build()) : null)
                .directReports(isAnyDirectReports(includeDirectReports, employeeResponse.getEmployeeId()) ?
                        getEmployeeListByManagerId(employeeResponse.getEmployeeId()).getEmployeeResponseList() : Collections.emptyList())
                .build();
    }

    private Boolean isAnyDirectReports(Boolean includeDirectReports, Integer managerId){
        return (ObjectUtils.isNotEmpty(includeDirectReports) && includeDirectReports && ObjectUtils.isNotEmpty(managerId));
    }

    private EmployeeListResponse getEmployeeListByManagerId(Integer managerId){
        List<EmployeeResponse> employeeResponseList = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(managerId)) {
            employeeResponseList = defineEmployees.getEmployeeResponseList().stream()
                    .filter(employeeResponse -> ObjectUtils.isNotEmpty(employeeResponse.getManagerId())
                            && employeeResponse.getManagerId().compareTo(managerId) == 0)
                    .peek(employee->log.info("employee id = {} report directly to = {}",employee.getEmployeeId(),managerId))
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
