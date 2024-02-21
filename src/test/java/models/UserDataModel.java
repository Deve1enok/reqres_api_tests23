package models;

import lombok.Data;

@Data
public class UserDataModel {
    private int id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;
    private String name;
    private String year;
    private String color;
    private String pantone_value;
}
