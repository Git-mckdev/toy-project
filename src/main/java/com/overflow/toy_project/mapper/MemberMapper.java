package com.overflow.toy_project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.overflow.toy_project.model.Member;

@Mapper
public interface MemberMapper {
    // 회원 기능
    Member selectByEmail(@Param("email") String email);
    Member selectByEmailAndPassword(Member member);
    void insertMember(Member member);
    void updateMember(Member member);
}