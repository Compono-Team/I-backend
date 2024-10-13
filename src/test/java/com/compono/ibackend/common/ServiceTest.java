package com.compono.ibackend.common;

import com.compono.ibackend.common.builder.TestFixtureBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
// @Sql(value = {"/truncate.sql"})
public class ServiceTest {

    @Autowired protected TestFixtureBuilder testFixtureBuilder;
}
