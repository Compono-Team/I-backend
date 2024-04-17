package com.compono.ibackend.common;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import com.compono.ibackend.common.builder.BuilderSupporter;
import com.compono.ibackend.common.builder.TestFixtureBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@Import(value = {TestFixtureBuilder.class, BuilderSupporter.class})
@Sql(value = {"/truncate.sql"})
public class RepositoryTest {
    @Autowired protected TestFixtureBuilder testFixtureBuilder;
}
