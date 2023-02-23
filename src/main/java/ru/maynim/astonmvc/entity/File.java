package ru.maynim.astonmvc.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dat_files", schema = "aston_trainee")
public class File {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dat_files_id_seq")
    @SequenceGenerator(name = "dat_files_id_seq", sequenceName = "dat_files_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dat_notes_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Note note;
}
