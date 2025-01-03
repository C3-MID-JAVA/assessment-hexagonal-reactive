package ec.com.sofka.exception.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.Map;

@Schema(description = "Details of validation errors")
public class ValidationErrorDetails {

    @Schema(description = "Timestamp of the validation error", example = "2023-01-01T10:00:00")
    private Date timestamp;

    @Schema(description = "General validation error message", example = "Validation failed for one or more fields.")
    private String message;

    @Schema(description = "Specific field validation errors")
    private Map<String, String> fieldErrors;

    public ValidationErrorDetails(Date timestamp, String message, Map<String, String> fieldErrors) {
        this.timestamp = timestamp;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
