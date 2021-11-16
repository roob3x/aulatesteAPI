import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class LanguageQueryTest {

    @Test
    public void shouldReturnStatus200AndAllRequiredFields(){
        given().
                header("Content-Type", Constants.CONTENT_TYPE).
                body("{\"query\":\"query{\\nlanguage(code: \\\"pt\\\") {\\n  name\\n  native\\n  rtl\\n}\\n}\",\"variables\":{}}").
        when().
                post(Constants.BASE_URL).
        then().
                assertThat().
                statusCode(200)
                .assertThat().body(containsString("{\"data\":{\"language\":{\"name\":\"Portuguese\",\"native\":\"Português\",\"rtl\":false}}}"));
    }

    @Test
    public void shouldReturnStatus200AndEmptyResult(){
        given().
                header("Content-Type", Constants.CONTENT_TYPE).
                body("{\"query\":\"query{\\nlanguage(code: \\\"\\\") {\\n  name\\n  native\\n  rtl\\n}\\n}\",\"variables\":{}}").
        when().
                post(Constants.BASE_URL).
        then().
                assertThat().
                statusCode(200)
                .assertThat().body(containsString("{\"data\":{\"language\":null}}"));
    }

    @Test
    public void shouldReturnStatus400AndErrorMenssageInvalidField(){
        given().
                header("Content-Type", Constants.CONTENT_TYPE).
                body("{\"query\":\"query{\\nlanguage(code: \\\"pt\\\") {\\n  na\\n  native\\n  rtl\\n}\\n}\",\"variables\":{}}").
        when().
                post(Constants.BASE_URL).
        then().
                assertThat().
                statusCode(400)
                .assertThat().body(containsString(Constants.ERROR_CODE));
    }
}
