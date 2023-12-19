package com.rntgroup.boot.tstapp.shell;

import org.junit.jupiter.api.Test;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
//		(properties = {
//		"spring.shell.interactive.enabled=false"
//}
//)
public class TestTurningOffTheSpringShell {
	@MockBean
	ApplicationRunner applicationRunner;

	@Test
	public void shouldFinishWell() {
	}
}
