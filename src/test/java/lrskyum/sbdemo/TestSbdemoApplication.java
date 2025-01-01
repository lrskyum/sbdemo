package lrskyum.sbdemo;

import org.springframework.boot.SpringApplication;

public class TestSbdemoApplication {

	public static void main(String[] args) {
		SpringApplication.from(SbdemoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
