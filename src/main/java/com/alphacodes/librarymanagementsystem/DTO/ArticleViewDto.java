package com.alphacodes.librarymanagementsystem.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class ArticleViewDto {
    private int articleID;
    private int userID;
    private String title;
    private String body;
    @Lob
    @Column(name = "article_img", columnDefinition = "LONGBLOB")
    private byte[] articleImg;
}
