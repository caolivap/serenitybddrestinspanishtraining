package tasks;

import io.restassured.http.ContentType;
import models.users.UserInfo;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Put;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class UpdateUsers implements Task {

    private UserInfo userInfo;

    public UpdateUsers(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public static Performable withInfo(UserInfo userInfo) {
        return instrumented(UpdateUsers.class, userInfo);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Put.to("/users/2").with(
                        requestSpecification -> requestSpecification
                            .contentType(ContentType.JSON)
                            .body(userInfo))
        );
    }
}
