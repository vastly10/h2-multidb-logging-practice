package com.practice.h2practice.dao.db1;

import com.practice.h2practice.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface Db1Mapper {

    @Select("select * from member")
    List<Member> getAllMembers();
}
