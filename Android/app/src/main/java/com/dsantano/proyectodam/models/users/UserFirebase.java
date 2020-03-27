package com.dsantano.proyectodam.models.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFirebase {
    private String email;
    private String password;
    private String uid;
    private String username;

}
