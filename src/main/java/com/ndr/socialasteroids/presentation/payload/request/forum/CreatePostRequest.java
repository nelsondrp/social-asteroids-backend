package com.ndr.socialasteroids.presentation.payload.request.forum;

import lombok.Data;

@Data //TODO validation
public class CreatePostRequest
{
    private Long authorId;
    private Long threadId;
    private String content;
    
}
