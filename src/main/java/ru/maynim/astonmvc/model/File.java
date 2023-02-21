package ru.maynim.astonmvc.model;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class File {
    private long id;
    private String name;
    private String url;
    private Note note;
}
