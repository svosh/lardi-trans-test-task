package com.example.demo.model.entity;

import com.google.common.base.MoreObjects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.MoreObjects.toStringHelper;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id", "userPhoneBook"})
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    @Size(min = 4, max = 50)
    @Pattern(regexp = "[A-Z|a-z]*", message = "The login should contain English characters only")
    private String login;

    @Column
    @NotNull
    @Size(min = 5, max = 50)
    private String password;

    @Column
    @NotNull
    @Size(min = 5, max = 50)
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhoneBookRow> userPhoneBook = new ArrayList<>();

    protected MoreObjects.ToStringHelper formToStringHelper() {
        return toStringHelper(this)
                .add("id:", this.id)
                .add("login:", this.login)
                .add("name:", this.name)
                .omitNullValues();
    }

    @Override
    public String toString() {
        return formToStringHelper().toString();
    }
}
