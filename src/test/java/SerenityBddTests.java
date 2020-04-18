import models.users.Datum;
import questions.GetUsersQuestion;
import questions.ResponseCode;
import tasks.GetUsers;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SerenityRunner.class)
public class SerenityBddTests {

    private static final String restApiUrl = "http://localhost:5000/api";

    @Test
    public void getUsers(){
        Actor carlos = Actor.named("Carlos Oliva")
                .whoCan(CallAnApi.at(restApiUrl));

        carlos.attemptsTo(
                GetUsers.fromPage(1)
        );

        carlos.should(
                seeThat("el código de respuesta", ResponseCode.was(), equalTo(200))
        );

        Datum user = new GetUsersQuestion().answeredBy(carlos)
                .getData().stream().filter(x -> x.getId() == 1).findFirst().orElse(null);

        carlos.should(
                seeThat("Usuario no es nulo", act -> user, notNullValue())
        );

        carlos.should(
                seeThat("El email del usuario", act -> user.getEmail(), is("george.bluth@reqres.in")),
                seeThat("El avatar del usuario", act -> user.getAvatar(), is("https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg"))
        );

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
