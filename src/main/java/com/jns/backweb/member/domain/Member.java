package com.jns.backweb.member.domain;

import com.jns.backweb.auth.exception.InvalidLoginException;
import com.jns.backweb.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    private LocalDate birthDate;
    private String phoneNumber;
    private String password;


    public static Member ofSimpleMember(String email, String name) {
        return new Member(null, email, name, null, null, null);
    }

    public static Member of(String email, String name, LocalDate birthDate, String phoneNumber, String password) {

        return new Member(null, email, name, birthDate, phoneNumber, password);
    }

    public void validatePassword(String password) {

        if( this.password == null || !this.password.equals(password)) {
            throw new InvalidLoginException();
        }
    }

}
