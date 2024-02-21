package models;

import lombok.Data;

@Data
public class LoginUserRequestModel {
    String email, password;
}
