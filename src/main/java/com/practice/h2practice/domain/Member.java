package com.practice.h2practice.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member {

    private String id;
    private String password;
    private String name;
    private String email;

}
