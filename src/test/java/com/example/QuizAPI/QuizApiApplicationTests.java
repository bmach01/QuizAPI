package com.example.QuizAPI;


import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuizApiApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;

	@DirtiesContext
	@Test
	void shouldCreateAndGetNewSession() {
		String c_id = "65a95338e49dcea3426df5cd";
		String highestKey = "AAAAA";

		ResponseEntity<Void> createResponse = restTemplate
				.postForEntity("/sessions/new/{c_id}", null, Void.class, c_id);
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI locationOfSession = createResponse.getHeaders().getLocation();
		assertThat(locationOfSession).isNotNull();

		ResponseEntity<String> getResponse = restTemplate
				.getForEntity(locationOfSession, String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		String id = documentContext.read("$.id");
		String key = documentContext.read("$.key");

		assertThat(id).isNotNull();
		assertThat(key).isEqualTo(highestKey);
	}

}
