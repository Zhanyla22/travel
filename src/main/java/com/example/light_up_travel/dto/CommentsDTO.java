package com.example.light_up_travel.dto;

import com.example.light_up_travel.enums.Status;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDTO {

    private Long id;

    private String comment;

    private BasicUserDTO userId;

    private BasicPostDTO postId;

    private Status status;

}
