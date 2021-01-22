package com.example.chessapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.chessapi.Application;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = Application.class)
public class ChessControllerTest {

	@LocalServerPort
	int port;

	@Test
	public void testGetChessBoard() {
		String pgn = "[Event \"F/S Return Match\"]\n" + "[Site \"Belgrade, Serbia JUG\"]\n" + "[Date \"1992.11.04\"]\n"
				+ "[Round \"29\"]\n" + "[White \"Fischer, Robert J.\"]\n" + "[Black \"Spassky, Boris V.\"]\n"
				+ "[Result \"1/2-1/2\"]\n\n"
				+ "1. e4 e5 2. Nf3 Nc6 3. Bb5 a6 4. Ba4 Nf6 5. O-O Be7 6. Re1 b5 7. Bb3 d6 8. c3 O-O\n"
				+ "9. h3 Nb8 10. d4 Nbd7 11. c4 c6 12. cxb5 axb5 13. Nc3 Bb7 14. Bg5 b4 15. Nb1 h6\n"
				+ "16. Bh4 c5 17. dxe5 Nxe4 18. Bxe7 Qxe7 19. exd6 Qf6 20. Nbd2 Nxd6 21. Nc4 Nxc4 22. Bxc4 Nb6\n"
				+ "23. Ne5 Rae8 24. Bxf7+ Rxf7 25. Nxf7 Rxe1+ 26. Qxe1 Kxf7 27. Qe3 Qg5 28. Qxg5 hxg5\n"
				+ "29. b3 Ke6 30. a3 Kd6 31. axb4 cxb4 32. Ra5 Nd5 33. f3 Bc8 34. Kf2 Bf5 35. Ra7 g6\n"
				+ "36. Ra6+ Kc5 37. Ke1 Nf4 38. g3 Nxh3 39. Kd2 Kb5 40. Rd6 Kc5 41. Ra6 Nf2 42. g4 Bd3\n"
				+ "43. Re6 1/2-1/2\n";

		String url = "http://localhost:" + port + "/api/load_pgn";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<String>(pgn, headers);

		final RestTemplate restTemplate = new RestTemplateBuilder().setConnectTimeout(Duration.ofSeconds(500))
				.setReadTimeout(Duration.ofSeconds(500)).build();

		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
				String.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

}
