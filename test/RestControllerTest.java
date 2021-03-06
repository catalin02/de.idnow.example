import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import play.libs.Json;
import play.libs.ws.WS;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

/**
 *
 * Simple (JUnit) tests that can call all parts of a play app.
 * If you are interested in mocking a whole application, see the wiki for more details.
 *
 */
public class RestControllerTest {

	JsonNode identifications;

	@Test
	public void getIdentifications() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				assertEquals(WS.url("http://localhost:3333/api/v1/identifications").get().get(10000).getStatus(), OK);
			}
		});

	}

	@Test
	public void postIdentification() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
			@Override
			public void run() {
				JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"slatime\": 60, \"slapercentage\": 0.9, \"currentslapercentage\": 0.95}");
				assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

				JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waitingtime\": 10, \"companyid\": 1}");
				assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);
			}
		});

	}

}
