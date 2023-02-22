package ru.maynim.astonmvc.entity;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRole {
    private long userId;
    private long roleId;
}
