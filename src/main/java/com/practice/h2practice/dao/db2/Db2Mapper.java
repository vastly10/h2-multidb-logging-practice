package com.practice.h2practice.dao.db2;

import com.practice.h2practice.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface Db2Mapper {

    @Select("select * from member")
    List<Member> getAllMembers();

}
