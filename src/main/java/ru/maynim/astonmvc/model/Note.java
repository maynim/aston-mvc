package ru.maynim.astonmvc.model;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Note {
    private long id;
    private String name;
    private String content;
    private User user;
}
