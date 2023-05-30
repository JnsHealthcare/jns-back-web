package com.jns.backweb.member.domain;

import com.jns.backweb.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String oauthId;
    private String nickname;
    private String email;
    private String profileImage;
    private String name;
    private String phoneNumber;


    public static Member ofSimpleMember(String oauthId, String nickname, String email, String profileImage) {
        return new Member(null, oauthId, nickname, email, profileImage, null, null);
    }

    public static Member of(String oauthId, String nickname, String email,
                            String profileImage, String name, String phoneNumber) {

        return new Member(null, oauthId, nickname, email, profileImage,
                name, phoneNumber);
    }
}
