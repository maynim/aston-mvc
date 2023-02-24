package ru.maynim.astonmvc.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dat_notes", schema = "aston_trainee")
public class Note {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dat_notes_id_seq")
    @SequenceGenerator(name = "dat_notes_id_seq", sequenceName = "dat_notes_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dat_users_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private User user;
}
