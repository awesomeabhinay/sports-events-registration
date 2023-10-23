package IT;

import com.intuit.sportseventsregistration.SportsEventsRegistrationApplication;
import org.json.JSONException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Stream;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SportsEventsRegistrationApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTesting {
    @LocalServerPort
    private int port;
    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @ParameterizedTest
    @MethodSource("parameterProvider")
    public void testEventController(String uri, String input, HttpMethod httpMethod, String expected) throws JSONException {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(input, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort(uri),
                httpMethod, entity, String.class);
        JSONAssert.assertEquals(expected, response.getBody(),false);
    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    private static Stream<Arguments> parameterProvider(){
        return Stream.of(
                Arguments.of("/events/all", null, HttpMethod.GET,
                        "[{\"id\":1,\"eventName\":\"Event 1\",\"eventCategory\":\"Category A\",\"startTime\":\"2023-10-01T10:00:00+05:30\",\"endTime\":\"2023-10-01T11:00:00+05:30\",\"maxRegistrationLimit\":2,\"currentRegistrationCount\":2},{\"id\":2,\"eventName\":\"Event 2\",\"eventCategory\":\"Category B\",\"startTime\":\"2023-10-02T14:00:00+05:30\",\"endTime\":\"2023-10-02T16:00:00+05:30\",\"maxRegistrationLimit\":4,\"currentRegistrationCount\":1},{\"id\":3,\"eventName\":\"Event 3\",\"eventCategory\":\"Category A\",\"startTime\":\"2023-10-03T11:00:00+05:30\",\"endTime\":\"2023-10-03T12:00:00+05:30\",\"maxRegistrationLimit\":7,\"currentRegistrationCount\":1},{\"id\":4,\"eventName\":\"Event 4\",\"eventCategory\":\"Category C\",\"startTime\":\"2023-10-03T12:00:00+05:30\",\"endTime\":\"2023-10-03T13:00:00+05:30\",\"maxRegistrationLimit\":11,\"currentRegistrationCount\":8},{\"id\":5,\"eventName\":\"Event 5\",\"eventCategory\":\"Category D\",\"startTime\":\"2023-10-03T13:00:00+05:30\",\"endTime\":\"2023-10-03T14:00:00+05:30\",\"maxRegistrationLimit\":12,\"currentRegistrationCount\":9},{\"id\":6,\"eventName\":\"Event 6\",\"eventCategory\":\"Category A\",\"startTime\":\"2023-10-03T09:00:00+05:30\",\"endTime\":\"2023-10-03T11:00:00+05:30\",\"maxRegistrationLimit\":7,\"currentRegistrationCount\":7},{\"id\":7,\"eventName\":\"Event 7\",\"eventCategory\":\"Category B\",\"startTime\":\"2023-10-03T09:00:00+05:30\",\"endTime\":\"2023-10-03T11:00:00+05:30\",\"maxRegistrationLimit\":9,\"currentRegistrationCount\":3},{\"id\":8,\"eventName\":\"Event 8\",\"eventCategory\":\"Category C\",\"startTime\":\"2023-10-03T09:00:00+05:30\",\"endTime\":\"2023-10-03T11:00:00+05:30\",\"maxRegistrationLimit\":15,\"currentRegistrationCount\":1},{\"id\":9,\"eventName\":\"Event 9\",\"eventCategory\":\"Category C\",\"startTime\":\"2023-10-03T09:00:00+05:30\",\"endTime\":\"2023-10-03T11:00:00+05:30\",\"maxRegistrationLimit\":18,\"currentRegistrationCount\":18},{\"id\":10,\"eventName\":\"Event 10\",\"eventCategory\":\"Category C\",\"startTime\":\"2023-10-03T09:00:00+05:30\",\"endTime\":\"2023-10-03T11:00:00+05:30\",\"maxRegistrationLimit\":3,\"currentRegistrationCount\":2},{\"id\":11,\"eventName\":\"Event 11\",\"eventCategory\":\"Category B\",\"startTime\":\"2023-10-03T09:00:00+05:30\",\"endTime\":\"2023-10-03T11:00:00+05:30\",\"maxRegistrationLimit\":4,\"currentRegistrationCount\":4}]"),
                Arguments.of("/events/user1", null, HttpMethod.GET,
                        "[{\"id\":1,\"eventName\":\"Event 1\",\"eventCategory\":\"Category A\",\"startTime\":\"2023-10-01T10:00:00+05:30\",\"endTime\":\"2023-10-01T11:00:00+05:30\",\"maxRegistrationLimit\":2,\"currentRegistrationCount\":2},{\"id\":2,\"eventName\":\"Event 2\",\"eventCategory\":\"Category B\",\"startTime\":\"2023-10-02T14:00:00+05:30\",\"endTime\":\"2023-10-02T16:00:00+05:30\",\"maxRegistrationLimit\":4,\"currentRegistrationCount\":1}]"),
                Arguments.of("/login", "{\"username\":\"user1\"}", HttpMethod.POST,
                        "{\"username\":\"user1\",\"email\":\"user1@example.com\"}"),
                Arguments.of("/create/user", "{\"username\":\"abhinay\",\"email\":\"abhinay@test.com\"}", HttpMethod.POST,
                        "{\"username\":\"abhinay\",\"email\":\"abhinay@test.com\"}"),
                Arguments.of("/event/register", "{\"eventId\":2,\"username\":\"user3\"}", HttpMethod.POST,
                        "{\"registrationId\":5,\"eventId\":2,\"username\":\"user3\"}")
        );
    }
}
