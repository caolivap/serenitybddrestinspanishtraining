import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SerenityRunner.class)
public class SerenityBddTests {

    private static final String restApiUrl = "http://localhost:5000/api";

    @Test
    public void getUsers(){
        Actor carlos = Actor.named("Carlos Oliva")
                .whoCan(CallAnApi.at(restApiUrl));

        carlos.attemptsTo(
                GetUsers.fromPage(3)
        );

        assertThat(SerenityRest.lastResponse().statusCode()).isEqualTo(200);
    }

    @Test
    public void getUsersFail(){
        Actor carlos = Actor.named("Carlos Oliva")
                .whoCan(CallAnApi.at(restApiUrl));

        carlos.attemptsTo(
                Get.resource("/users?page=2")
        );

        assertThat(SerenityRest.lastResponse().statusCode()).isEqualTo(400);
    }
}
