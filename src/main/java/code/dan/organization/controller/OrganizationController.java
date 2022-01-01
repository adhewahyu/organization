package code.dan.organization.controller;

import code.dan.organization.model.request.EmptyRequest;
import code.dan.organization.model.request.FindByIdAndReportingFlagRequest;
import code.dan.organization.model.response.EmployeeByIdResponse;
import code.dan.organization.model.response.EmployeeListResponse;
import code.dan.organization.model.response.RestResponse;
import code.dan.organization.service.GetEmployeeByIdService;
import code.dan.organization.service.GetEmployeeService;
import code.dan.organization.util.Constants;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final GetEmployeeService getEmployeeService;
    private final GetEmployeeByIdService getEmployeeByIdService;

    @GetMapping("/v1/employees")
    public ResponseEntity<RestResponse> getEmployees(){
        EmployeeListResponse employeeListResponse = getEmployeeService.execute(new EmptyRequest());
        if(!CollectionUtils.isEmpty(employeeListResponse.getEmployeeResponseList())){
            return new ResponseEntity<>(new RestResponse(employeeListResponse.getEmployeeResponseList(), Constants.SUCCESS_MSG_DATA_FOUND, true), HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERR_MSG_DATA_NOT_FOUND);
    }

    @GetMapping("/v1/employees/{employeeId}")
    public ResponseEntity<RestResponse> getEmployees(@PathVariable Integer employeeId,
                                                     @RequestParam(value = "includeReportingTree", required = false) Boolean includeReportingTree){
        EmployeeByIdResponse employeeByIdResponse = getEmployeeByIdService.execute(FindByIdAndReportingFlagRequest.builder()
                        .id(employeeId)
                        .includeReportingFlag(includeReportingTree)
                        .build());
        if(ObjectUtils.isNotEmpty(employeeByIdResponse)){
            return new ResponseEntity<>(new RestResponse(employeeByIdResponse, Constants.SUCCESS_MSG_DATA_FOUND, true), HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERR_MSG_DATA_NOT_FOUND);
    }

}
