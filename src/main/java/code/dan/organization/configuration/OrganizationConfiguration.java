package code.dan.organization.configuration;

import code.dan.organization.model.response.EmployeeListResponse;
import code.dan.organization.model.response.EmployeeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class OrganizationConfiguration {

    @EventListener(ApplicationReadyEvent.class)
    public void initEmployees(){
        log.info("initEmployees [start]");
        EmployeeListResponse data = defineEmployees();
        log.info("employee size {}", data.getEmployeeResponseList().size());
        log.info("initEmployees [end]");
    }

    @Bean(name = "defineEmployees")
    public EmployeeListResponse defineEmployees(){
        List<EmployeeResponse> employeeResponseList = new ArrayList<>();
        employeeResponseList.add(EmployeeResponse.builder().employeeId(1).name("Guy Gardner").managerId(15).build());
        employeeResponseList.add(EmployeeResponse.builder().employeeId(2).name("Arthur Curry").managerId(12).build());
        employeeResponseList.add(EmployeeResponse.builder().employeeId(3).name("John Steward").managerId(7).build());
        employeeResponseList.add(EmployeeResponse.builder().employeeId(4).name("Ray Palmer").managerId(6).build());
        employeeResponseList.add(EmployeeResponse.builder().employeeId(5).name("Jessica Cruz").managerId(15).build());
        employeeResponseList.add(EmployeeResponse.builder().employeeId(6).name("Shayera Hol").managerId(12).build());
        employeeResponseList.add(EmployeeResponse.builder().employeeId(7).name("Bruce Wayne").managerId(17).build());
        employeeResponseList.add(EmployeeResponse.builder().employeeId(8).name("Kyle Rayner").managerId(15).build());
        employeeResponseList.add(EmployeeResponse.builder().employeeId(9).name("Billy Batson").managerId(6).build());
        employeeResponseList.add(EmployeeResponse.builder().employeeId(10).name("Kiliwog").managerId(3).build());
        employeeResponseList.add(EmployeeResponse.builder().employeeId(11).name("Dinah Drake").managerId(7).build());
        employeeResponseList.add(EmployeeResponse.builder().employeeId(12).name("Diana Prince").managerId(17).build());
        employeeResponseList.add(EmployeeResponse.builder().employeeId(13).name("Sinestro").managerId(3).build());
        employeeResponseList.add(EmployeeResponse.builder().employeeId(14).name("J'onn J'onzz").managerId(12).build());
        employeeResponseList.add(EmployeeResponse.builder().employeeId(15).name("Hal Jordan").managerId(3).build());
        employeeResponseList.add(EmployeeResponse.builder().employeeId(16).name("Oliver Queen").managerId(7).build());
        employeeResponseList.add(EmployeeResponse.builder().employeeId(17).name("Clark Kent").managerId(null).build());
        employeeResponseList.add(EmployeeResponse.builder().employeeId(18).name("Zatanna Zatara").managerId(7).build());
        employeeResponseList.add(EmployeeResponse.builder().employeeId(19).name("Barry Allen").managerId(17).build());
        return EmployeeListResponse.builder()
                .employeeResponseList(employeeResponseList)
                .build();
    }

}
