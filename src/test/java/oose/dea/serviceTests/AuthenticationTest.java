package oose.dea.serviceTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import oose.dea.resources.services.AuthenticationService;

import java.sql.SQLException;

public class AuthenticationTest {

    private AuthenticationService service;
    private static final String TOKEN = "7057a9bd-a879-43df-8bec-604ead563e7c";

    @BeforeEach
    void setup() throws SQLException {
        service = new AuthenticationService();
    }

    @Test
    public void doesAuthenticationReturnCorrectBooleanCorrectToken() throws SQLException {
        //Setup
        boolean returnValue = true;

        //Test
        boolean actualReturn = service.performAuthentication(TOKEN);

        //Verify
        Assertions.assertEquals(returnValue, actualReturn);

    }


    @Test
    public void doesAuthenticationReturnCorrectBooleanFalseToken() throws SQLException {
        //Setup
        boolean returnValue = false;

        //Test
        boolean actualReturn = service.performAuthentication("False token");

        //Verify
        Assertions.assertEquals(returnValue, actualReturn);

    }
}
