package questions;

import models.users.UserInfo;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ResponseKey implements Question<UserInfo> {

    public static ResponseKey contains() {
        return new ResponseKey();
    }

    @Override
    public UserInfo answeredBy(Actor actor) {
        return SerenityRest.lastResponse().as(UserInfo.class);
    }
}
