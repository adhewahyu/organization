package code.dan.organization.service;

import code.dan.organization.model.request.FindByIdAndReportingFlagRequest;
import code.dan.organization.model.request.FindByIdRequest;
import code.dan.organization.model.response.EmployeeByIdResponse;
import code.dan.organization.model.response.EmployeeListResponse;
import code.dan.organization.model.response.EmployeeResponse;
import code.dan.organization.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetEmployeeByIdService implements BaseService<FindByIdAndReportingFlagRequest, EmployeeByIdResponse>{

    private final EmployeeListResponse defineEmployees;
    private final GetEmployeeListByManagerIdService getEmployeeListByManagerIdService;

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
                .directReports(isAnyDirectReports(includeDirectReports, employeeResponse.getEmployeeId()) ? getEmployeeListByManagerIdService.execute(FindByIdRequest.builder()
                                .id(employeeResponse.getEmployeeId())
                        .build()).getEmployeeResponseList() : null)
                .build();
    }

    private Boolean isAnyDirectReports(Boolean includeDirectReports, Integer managerId){
        return (ObjectUtils.isNotEmpty(includeDirectReports) && includeDirectReports && ObjectUtils.isNotEmpty(managerId));
    }

}
