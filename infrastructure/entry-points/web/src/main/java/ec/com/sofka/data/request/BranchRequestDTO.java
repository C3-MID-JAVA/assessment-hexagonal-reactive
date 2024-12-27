package ec.com.sofka.data.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchRequestDTO {
    private int id;
    private String name;
    private String address;
    private String phone;
}
