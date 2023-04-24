package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * This entity class defines RuleName objects. It has two constructors : one without parameter,
 * one with parameters 'name', 'description' , 'json', 'template', 'sql_str', 'sql_part'
 *
 * @author Emmanuelle Bonnemay
 * created on 23/04/2023
 */
@Entity
@Table(name = "rulename")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class RuleName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="rulename_id")
    Integer rulename_id;

    @Column(name="name")
    @NonNull
    @NotBlank(message = "name is mandatory")
    String name;
    @Column(name="description")
    @NonNull
    @NotBlank(message = "description is mandatory")
    String description;

    @Column(name = "json")
    @NonNull
    @NotBlank(message = "json is mandatory")
    String json;
    @Column(name="template")
    @NonNull
    @NotBlank(message = "template is mandatory")
    String template;
    @Column(name= "sql_str")
    @NonNull
    @NotBlank(message = "sql-str is mandatory")
    String sql_str;
    @Column(name="sql_part")
    @NonNull
    @NotBlank(message = "sql-part is mandatory")
    String sql_part;

    @Column(name= "rule_name")
    Integer rule_name;

    @Column(name="rulename")
    Integer rulename;
}
