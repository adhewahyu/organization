package code.dan.organization.service;

import code.dan.organization.model.request.EmptyRequest;
import code.dan.organization.model.response.EmployeeListResponse;
import code.dan.organization.model.response.EmployeeResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class GetEmployeeServiceTest {

    @InjectMocks
    private GetEmployeeService getEmployeeService;

    @Mock
    private EmployeeListResponse defineEmployees;

    @BeforeEach
    private void init(){
        List<EmployeeResponse> employeeResponseList = new ArrayList<>();
        for(int i = 0; i < 19; i++){
            employeeResponseList.add(EmployeeResponse.builder().employeeId(i).name("data ke "+i).managerId(i).build());
        }
        Mockito.when(defineEmployees.getEmployeeResponseList()).thenReturn(employeeResponseList);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSuccess(){
        EmployeeListResponse employeeListResponse = getEmployeeService.execute(new EmptyRequest());
        Assertions.assertEquals(19, employeeListResponse.getEmployeeResponseList().size());
    }

}
