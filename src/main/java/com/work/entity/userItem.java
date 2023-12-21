package com.work.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class userItem {
    private Integer id;
    private String Username;
    private String Password;
    private String token;
    private Integer task_id;
    private Object header_img;
    private String level;
}
