package com.ssafy.hungry.domain.game.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "units")
public class UnitEntity {
    @Id
    @Column(name = "unit_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String skill;


//    @OneToMany(mappedBy = "unitId")
//    private List<QuestionEntity> questionList = new ArrayList<>();

}
