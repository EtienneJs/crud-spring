package com.crud.crud;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import com.crud.crud.config.ContainerBaseTest;
import com.crud.crud.config.MongoDBTestConfig;

@Import(MongoDBTestConfig.class)
class CrudApplicationTests extends ContainerBaseTest{

	@Test
	void contextLoads() {
	}

}
