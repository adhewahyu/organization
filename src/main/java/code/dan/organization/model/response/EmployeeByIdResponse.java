package code.dan.organization.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeByIdResponse {

    private Integer employeeId;
    private String name;
    private EmployeeByIdResponse manager;
    private List<EmployeeResponse> directReports;

}
