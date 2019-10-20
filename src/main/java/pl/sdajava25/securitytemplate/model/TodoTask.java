package pl.sdajava25.securitytemplate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import pl.sdajava25.securitytemplate.model.status.Status;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TodoTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String content;

    @CreationTimestamp
    private LocalDate dateAdd;

    private LocalDate dateFinished;
    private TaskStatus status;

    @ManyToOne
    private Account account;

}
