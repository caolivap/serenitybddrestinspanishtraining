import facts.NetflixPlans;
import models.users.Datum;
import models.users.UserInfo;
import questions.GetUsersQuestion;
import questions.ResponseCode;
import questions.ResponseKey;
import tasks.GetUsers;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;
import org.junit.Test;
import org.junit.runner.RunWith;
import tasks.RegisterUser;
import tasks.UpdateUsers;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SerenityRunner.class)
public class SerenityBddTests {

    private static final String restApiUrl = "http://localhost:5000/api";

    @Test
    public void getUsersTest() {
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
    public void registerUserPostTest() {
        Actor carlos = Actor.named("Carlos Oliva")
                .whoCan(CallAnApi.at(restApiUrl));

        String registerUserInfo = "{\n" +
                "\t\"name\": \"Carlos\",\n" +
                "\t\"job\": \"Tester\",\n" +
                "    \"email\": \"charles.morris@reqres.in\",\n" +
                "    \"password\": \"serenity\"\n" +
                "}";

        carlos.attemptsTo(
                RegisterUser.withInfo(registerUserInfo)
        );

        carlos.should(
                seeThat("el código de respuesta", ResponseCode.was(), equalTo(200))
        );

    }

    @Test
    public void registerUserPostTestWithModel() {
        Actor carlos = Actor.named("Carlos Oliva")
                .whoCan(CallAnApi.at(restApiUrl));

        UserInfo userInfo = new UserInfo();
        userInfo.setName("Carlos");
        userInfo.setJob("Tester");
        userInfo.setEmail("charles.morris@reqres.in");
        userInfo.setPassword("serenity");

        carlos.attemptsTo(
                RegisterUser.withInfo(userInfo)
        );

        carlos.should(
                seeThat("el código de respuesta", ResponseCode.was(), equalTo(200))
        );

    }

    @Test
    public void updateUserPutTest() {
        Actor carlos = Actor.named("Carlos Oliva")
                .whoCan(CallAnApi.at(restApiUrl));

        UserInfo userInfo = new UserInfo();
        userInfo.setName("Carlos");
        userInfo.setJob("Tester");
        userInfo.setEmail("charles.morris@reqres.in");
        userInfo.setPassword("serenity");

        carlos.attemptsTo(
                UpdateUsers.withInfo(userInfo)
        );

        carlos.should(
                seeThat("el código de respuesta", ResponseCode.was(), equalTo(200))
        );

        UserInfo user = new ResponseKey().answeredBy(carlos);

        carlos.should(
                seeThat("el nombre del usuario", act -> user.getName(), notNullValue())
        );

        carlos.should(
                seeThat("el job del usuario", act -> user.getJob(), notNullValue())
        );

        carlos.should(
                seeThat("la fecha de actualización del usuario", act -> user.getUpdatedAt(), notNullValue())
        );

    }

/*    @Test
    public void getUsersFail() {
        Actor carlos = Actor.named("Carlos Oliva")
                .whoCan(CallAnApi.at(restApiUrl));

        carlos.attemptsTo(
                Get.resource("/users?page=2")
        );

        assertThat(SerenityRest.lastResponse().statusCode()).isEqualTo(400);
    }*/

    @Test
    public void factTest() {
        Actor carlos = Actor.named("Carlos Oliva")
                .whoCan(CallAnApi.at(restApiUrl));

        carlos.has(NetflixPlans.toViewSeries());

    }

}
