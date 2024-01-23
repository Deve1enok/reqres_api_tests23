package models;

import lombok.Data;

@Data
public class RegistrationResponseModel {
    String email, password, error;
}
