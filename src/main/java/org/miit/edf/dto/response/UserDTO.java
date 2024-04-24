package org.miit.edf.dto.response;

import lombok.Data;
import org.miit.edf.models.Organisation;
import org.miit.edf.models.Role;
import org.miit.edf.models.User;

@Data
public class UserDTO {
    private String login;
    private String firstname;
    private String lastname;
    private String patronymic;
    private Organisation organisation;
    private Role role;
    public UserDTO (User user) {
        this.login = user.getLogin();
        this.firstname = user.getFirstName();
        this.lastname = user.getLastName();
        this.patronymic = user.getPatronymic();
        this.organisation = user.getOrganisation();
        this.role = user.getRole();
    }
}
