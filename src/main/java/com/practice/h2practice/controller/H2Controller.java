package com.practice.h2practice.controller;

import com.practice.h2practice.dao.db1.Db1Mapper;
import com.practice.h2practice.dao.db2.Db2Mapper;
import com.practice.h2practice.domain.Member;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("h2-multidb")
public class H2Controller {

    Db1Mapper db1Mapper;
    Db2Mapper db2Mapper;

    public H2Controller(Db1Mapper db1Mapper, Db2Mapper db2Mapper) {
        this.db1Mapper = db1Mapper;
        this.db2Mapper = db2Mapper;
    }

    @GetMapping
    public Map<String, List<Member>> getData() {
        Map<String, List<Member>> result = new HashMap<>();
        result.put("db1", db1Mapper.getAllMembers());
        result.put("db2", db2Mapper.getAllMembers());
        return result;
    }

}
