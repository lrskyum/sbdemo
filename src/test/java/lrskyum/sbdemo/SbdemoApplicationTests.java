package lrskyum.sbdemo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
class SbdemoApplicationTests {

	@Test
	void contextLoads() {
	}

}
