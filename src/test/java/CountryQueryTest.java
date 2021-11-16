import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CountryQueryTest {

    @Test
    public void shouldReturnStatus200AndAllRequiredFields(){
        given().
                header("Content-Type", "application/json").
                body("{\"query\":\"query{\\n    country(code: \\\"BR\\\") {\\n    name\\n    native\\n    phone\\n    capital\\n    currency\\n    emoji\\n    emojiU\\n    languages{\\n        code\\n        name\\n        native\\n        rtl\\n    }\\n    }\\n}\\n \",\"variables\":{}}").
                when().
                post("http://countries.trevorblades.com/").
                then().
                assertThat().
                statusCode(200)
        .assertThat().body(containsString("{\"data\":{\"country\":{\"name\":\"Brazil\",\"native\":\"Brasil\",\"phone\":\"55\",\"capital\":\"Brasília\",\"currency\":\"BRL\",\"emoji\":\"\uD83C\uDDE7\uD83C\uDDF7\",\"emojiU\":\"U+1F1E7 U+1F1F7\",\"languages\":[{\"code\":\"pt\",\"name\":\"Portuguese\",\"native\":\"Português\",\"rtl\":false}]}}}"));
        //.assertThat().body("languages").contentType("Array");
    }

    @Test
    public void shouldReturnStatus400AndErrorMenssageInvalidField(){

        given().
                header("Content-Type", "application/json").
                body("{\"query\":\"query{\\n    country(code: \\\"BR\\\") {\\n    name\\n    native\\n    phone\\n    capital\\n    currency\\n    emoji\\n    emojiU\\n    languages{\\n        code\\n        name\\n        native\\n        rtl\\n    }\\n    }\\n}\\n \",\"variables\":{}}").
                when().
                post("http://countries.trevorblades.com/").
                then().
                assertThat().
                statusCode(400)
                .assertThat().body(containsString("{\"errors\":[{\"message\":\"Cannot query field \\\"lala\\\" on type \\\"Language\\\".\",\"locations\":[{\"line\":11,\"column\":9}],\"extensions\":{\"code\":\"GRAPHQL_VALIDATION_FAILED\"}}]}"));

    }
}
