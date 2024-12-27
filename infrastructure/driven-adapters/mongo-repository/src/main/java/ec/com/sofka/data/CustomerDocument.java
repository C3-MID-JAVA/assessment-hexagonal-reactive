package ec.com.sofka.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Customer")
public class CustomerDocument {
    @Id
    private int id;
    @NotNull(message = "Customer's first name is required")
    @NotBlank(message = "Customer's first name cannot be blank")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;
    @NotNull(message = "Customer's last name is required")
    @NotBlank(message = "Customer's last name cannot be blank")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;
    @Email(message = "Invalid email format")
    @NotNull(message = "Customer's email is required")
    @NotBlank(message = "Customer's email cannot be blank")
    private String email;
    @NotNull(message = "Customer phone is required")
    @NotBlank(message = "Customer phone cannot be blank")
    private String phone;
    @Size(max = 200, message = "Address can be a maximum of 200 characters")
    private String address;
    @DBRef
    @NotNull(message = "Customer's accounts is required")
    private List<AccountDocument> accounts;
}
