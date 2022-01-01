package code.dan.organization.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindByIdAndReportingFlagRequest {

    private Integer id;
    private Boolean includeReportingFlag;

}
