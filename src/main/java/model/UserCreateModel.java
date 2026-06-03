package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreateModel {
    private String email;
    private String password;
    private String name;
}
