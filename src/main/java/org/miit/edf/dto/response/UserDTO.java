package org.miit.edf.dto.response;

import lombok.Data;
import org.miit.edf.models.Organisation;
import org.miit.edf.models.Role;

@Data
public class UserDTO {
    private String login;
    private String firstname;
    private String lastname;
    private String patronymic;
    private Organisation organisation;
    private Role role;
}
