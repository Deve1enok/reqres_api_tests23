package models;

import lombok.Data;

@Data
public class RegistrationAndLoginResponseModel {
    String email, password, error;
}
