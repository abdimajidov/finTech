package uz.sardorbek.fintech.user.model.criteria;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@Data
@ParameterObject
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCriteria implements Criteria {
    StringFilter username;
    StringFilter name;
    StringFilter surname;
    StringFilter patronymic;
    StringFilter phoneNumber;
    StringFilter address;
    StringFilter email;
    LongFilter roleId;
    BooleanFilter active;

    public UserCriteria(UserCriteria userCriteria) {
        if (userCriteria != null) {
            this.username = userCriteria.username == null ? null : userCriteria.username.copy();
            this.name = userCriteria.name == null ? null : userCriteria.name.copy();
            this.surname = userCriteria.surname == null ? null : userCriteria.surname.copy();
            this.patronymic = userCriteria.patronymic == null ? null : userCriteria.patronymic.copy();
            this.phoneNumber = userCriteria.phoneNumber == null ? null : userCriteria.phoneNumber.copy();
            this.address = userCriteria.address == null ? null : userCriteria.address.copy();
            this.email = userCriteria.email == null ? null : userCriteria.email.copy();
            this.roleId = userCriteria.roleId == null ? null : userCriteria.roleId.copy();
            this.active = userCriteria.active == null ? null : userCriteria.active.copy();
        }
    }

    @Override
    public Criteria copy() {
        return new UserCriteria(this);
    }
}
