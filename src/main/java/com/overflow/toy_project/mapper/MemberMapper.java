package com.overflow.toy_project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.overflow.toy_project.model.Member;

@Mapper
public interface MemberMapper {
    Member selectByEmail(@Param("email") String email);
    void insertMember(Member member);
    Member selectByEmailAndPassword(Member member);
}