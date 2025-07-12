package org.example.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import org.example.entities.UserInfo;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserInfoDto extends UserInfo {
    private String userName;
    private String lastName;
    private Long phoneNumber;
    private String email;
}
