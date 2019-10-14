package oose.dea.controllerTests;

import oose.dea.resources.dataresources.LoginDAO;
import oose.dea.resources.controllers.LoginController;
import oose.dea.resources.dto.LoginRequestDto;
import oose.dea.resources.models.UserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.sql.SQLException;

public class LoginControllerTest {

    private LoginController sut;
    private LoginDAO loginDAOMock;

    @BeforeEach
    void setup() throws SQLException {
        loginDAOMock = Mockito.mock(LoginDAO.class);
        sut = new LoginController();
        sut.setLoginDAO(loginDAOMock);
    }

    @Test
    void doesEndpointDelegateCorrectWorkToDAO() throws SQLException, ClassNotFoundException {
        //Setup
        UserModel usermodel =  new UserModel();
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUser("diegocup");
        loginRequestDto.setPassword("cupdiego");

        usermodel.setUser("Diego Cup");
        usermodel.setToken("f4few18kbngdbWYe3k");

        Mockito.when(loginDAOMock.login(loginRequestDto)).thenReturn(usermodel);

        //Test
        Response response = sut.login(loginRequestDto);

        //Verify
        Mockito.verify(loginDAOMock).login(loginRequestDto);
        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    void doesLoginReturnErrorWithWrongCredentials() throws SQLException, ClassNotFoundException {
        // Setup
        UserModel usermodel = new UserModel();
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUser("diedie");
        loginRequestDto.setPassword("wrongPassword");

        usermodel.setUser("User");
        usermodel.setToken("error");

        Mockito.when(loginDAOMock.login(loginRequestDto)).thenReturn(usermodel);

        //Test
        Response response = sut.login(loginRequestDto);

        //Verify
        Mockito.verify(loginDAOMock).login(loginRequestDto);
        Assertions.assertEquals(403, response.getStatus());
    }

}
