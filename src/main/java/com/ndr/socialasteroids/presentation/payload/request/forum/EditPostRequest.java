package com.ndr.socialasteroids.presentation.payload.request.forum;

import lombok.Data;

@Data
public class EditPostRequest
{
    private Long postId;
    private Long ownerId;
    private String content;
}