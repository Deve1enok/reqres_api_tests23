package data;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)

public class DataSingleUserId2 {
    int id = 2;
    String email = "janet.weaver@reqres.in";
    String avatar = "https://reqres.in/img/faces/2-image.jpg";
    @JsonProperty("first_name")
    String firstName = "Janet";
    @JsonProperty("last_name")
    String lastName = "Weaver";

}
