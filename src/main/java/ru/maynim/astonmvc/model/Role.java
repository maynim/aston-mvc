package ru.maynim.astonmvc.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role {
    private long id;
    private String name;
    private String description;
    private List<User> users;
}
