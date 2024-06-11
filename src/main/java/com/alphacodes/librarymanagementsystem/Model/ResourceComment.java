package com.alphacodes.librarymanagementsystem.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ResourceComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resourceCommentId;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "member", nullable = false)
    private User member;

    @ManyToOne
    @JoinColumn(name = "book", nullable = false)
    private Resource book;


}

