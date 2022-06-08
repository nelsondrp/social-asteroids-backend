package com.ndr.socialasteroids.dealer.payload.request;

import lombok.Data;

@Data
public class FriendInviteAnswerReq
{
    private Long requestedId;
    private Long requesterId;
    private boolean accepted;

    public FriendInviteAnswerReq(){}
}
