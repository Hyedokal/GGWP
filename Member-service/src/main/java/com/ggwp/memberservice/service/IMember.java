package com.ggwp.memberservice.service;

import com.ggwp.memberservice.domain.Member;
import com.ggwp.memberservice.dto.RequestCreateMemberDto;

public interface IMember {
    Member.Vo createUser(RequestCreateMemberDto requestCreateMemberDto);
}
