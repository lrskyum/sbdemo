package lrskyum.sbdemo;

import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

//	@Bean
//	@ServiceConnection
//	KafkaContainer kafkaContainer() {
//		return new KafkaContainer(DockerImageName.parse("apache/kafka-native:latest"));
//	}
//
//	@Bean
//	@ServiceConnection
//	PostgreSQLContainer<?> postgresContainer() {
//		return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
//	}

}
