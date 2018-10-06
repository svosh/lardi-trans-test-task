package com.example.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.google.common.base.MoreObjects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Entity
@Table(name = "users_phone_book_rows")
public class PhoneBookRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_user_users_phone_book_rows"))
    @JsonBackReference
    private User user;

    @Column(name = "first_name")
    @NotNull
    @Size(min = 4, max = 50)
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    @Size(min = 4, max = 50)
    private String lastName;

    @Column
    @NotNull
    @Size(min = 4, max = 50)
    private String patronymic;

    @Column
    @Pattern(regexp = "[A-Z|a-z|0-9]*@[A-Z|a-z|0-9]*\\.[A-Z|a-z|0-9]*" , message = "Email should match the mask aaa@bbb.ccc")
    private String email;

    @Column
    private String address;

    @Column(name = "mobile_phone_number")
    @NotNull
    @Pattern(regexp = "\\+380\\([0-9]{2}\\)[0-9]{7}", message = "Phone number should match the mask +380(xx)xxxxxxx")
    private String mobilePhoneNumber;

    @Column(name = "home_phone_number")
    @Pattern(regexp = "\\+380\\([0-9]{2}\\)[0-9]{7}", message = "Phone number should match the mask +380(xx)xxxxxxx")
    private String homePhoneNumber;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id:", this.id)
                .add("user:", this.user)
                .add("firstName:", this.firstName)
                .add("lastName:", this.lastName)
                .add("patronymic:", this.patronymic)
                .add("email:", this.email)
                .add("address:", this.address)
                .add("mobilePhoneNumber:", this.mobilePhoneNumber)
                .add("homePhoneNumber:", this.homePhoneNumber)
                .omitNullValues()
                .toString();
    }
}
