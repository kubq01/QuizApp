package com.example.QuizApp.data.quizes;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "QuizResult")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private Float mark;
    private QuizStatus status;
    private Integer pointsGained;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

}
