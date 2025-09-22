package com.overflow.toy_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.overflow.toy_project.mapper.MemberMapper;
import com.overflow.toy_project.model.Member;

@Service
public class MemberService {
    
    private final MemberMapper memberMapper;

    @Autowired
    public MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    public Member validateDuplicateMember(Member member) {
        return memberMapper.selectByEmail(member.getEmail());
    }

    public Member loginMember(Member member) {
        return memberMapper.selectByEmailAndPassword(member);
    }

    public void saveMember(Member member) {
        memberMapper.insertMember(member);
    }

    public void updateMember(Member member) {
        memberMapper.updateMember(member);
    }
}