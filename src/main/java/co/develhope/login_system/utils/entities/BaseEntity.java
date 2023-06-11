package co.develhope.login_system.utils.entities;

import co.develhope.login_system.user.entities.User;
import lombok.Data;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseEntity {

    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    @ManyToOne
    private User createdBy;
    @ManyToOne
    private User updateBy;

}