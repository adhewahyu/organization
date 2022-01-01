package code.dan.organization.service;

import code.dan.organization.model.request.FindByIdAndReportingFlagRequest;
import code.dan.organization.model.response.EmployeeByIdResponse;
import code.dan.organization.model.response.EmployeeListResponse;
import code.dan.organization.model.response.EmployeeResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ExtendWith(MockitoExtension.class)
class GetEmployeeByIdServiceTest {

    @InjectMocks
    private GetEmployeeByIdService getEmployeeByIdService;

    @Mock
    private EmployeeListResponse defineEmployees;

    private void init(){
        List<EmployeeResponse> employeeResponseList = new ArrayList<>();
        employeeResponseList.add(EmployeeResponse.builder().employeeId(1).name("Jessica Cruz").managerId(3).build());
        employeeResponseList.add(EmployeeResponse.builder().employeeId(2).name("Kyle Rayner").managerId(3).build());
        employeeResponseList.add(EmployeeResponse.builder().employeeId(3).name("Clark Kent").managerId(null).build());
        Mockito.when(defineEmployees.getEmployeeResponseList()).thenReturn(employeeResponseList);
    }

    @Test
    void shouldSuccess(){
        init();
        MockitoAnnotations.openMocks(this);
        EmployeeByIdResponse employeeByIdResponse = getEmployeeByIdService.execute(FindByIdAndReportingFlagRequest.builder()
                .id(1)
                .includeReportingFlag(true)
                .build());
        log.info("result = {}",JSON.toJSONString(employeeByIdResponse));
        Assertions.assertNotNull(employeeByIdResponse);
    }

    @Test
    void shouldSuccessWithoutDirectReports(){
        init();
        MockitoAnnotations.openMocks(this);
        String expected = "{\"employeeId\":3,\"name\":\"Clark Kent\"}";
        EmployeeByIdResponse employeeByIdResponse = getEmployeeByIdService.execute(FindByIdAndReportingFlagRequest.builder()
                .id(3)
                .includeReportingFlag(false)
                .build());
        log.info("result = {}",JSON.toJSONString(employeeByIdResponse));
        Assertions.assertEquals(expected,JSON.toJSONString(employeeByIdResponse));
    }

    @Test
    void shouldErrorNotFound(){
        init();
        MockitoAnnotations.openMocks(this);
        try{
            getEmployeeByIdService.execute(FindByIdAndReportingFlagRequest.builder()
                    .id(99)
                    .includeReportingFlag(false)
                    .build());
        }catch (ResponseStatusException e){
            Assertions.assertEquals(HttpStatus.NOT_FOUND,e.getStatus());
        }
    }

    @Test
    void shouldErrorIdRequired(){
        MockitoAnnotations.openMocks(this);
        try{
            getEmployeeByIdService.execute(FindByIdAndReportingFlagRequest.builder()
                    .id(null)
                    .includeReportingFlag(false)
                    .build());
        }catch (ResponseStatusException e){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST,e.getStatus());
        }
    }

}
