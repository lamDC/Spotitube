package oose.dea.controllerTests;

import oose.dea.resources.controllers.LoginController;
import oose.dea.resources.dataresources.LoginDAO;
import oose.dea.resources.dto.LoginRequestDto;
import oose.dea.resources.exceptions.LoginException;
import oose.dea.resources.models.UserModel;
import oose.dea.resources.services.LoginService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

public class LoginControllerTest {

    private LoginController sut;

    @Mock
    private LoginService loginServiceMock;

    @Mock
    private LoginDAO loginDAOMock;

//    @InjectMocks
//    private LoginController sut;

    @BeforeEach
    void setup(){
        loginServiceMock = Mockito.mock(LoginService.class);
        loginDAOMock = Mockito.mock(LoginDAO.class);
        sut = new LoginController();
        loginServiceMock.setLoginDAO(loginDAOMock);
        sut.setLoginService(loginServiceMock);
    }

    @Test
    void doesEndpointDelegateCorrectWorkToDAO() throws LoginException {
        //Setup
        UserModel usermodel =  new UserModel();
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUser("diegocup");
        loginRequestDto.setPassword("cupdiego");

        usermodel.setUser("Diego Cup");
        usermodel.setToken("32994f0e-e466-4c8b-9744-5e8774575e52");

        Mockito.when(loginServiceMock.performLogin(loginRequestDto)).thenReturn(usermodel);

        //Test
        Response response = sut.login(loginRequestDto);

        //Verify
        Mockito.verify(loginServiceMock).performLogin(loginRequestDto);
        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    void doesLoginReturnErrorWithWrongCredentials() throws LoginException {
        // Setup
        UserModel usermodel = new UserModel();
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUser("diedie");
        loginRequestDto.setPassword("wrongPassword");

        usermodel.setUser(null);
        usermodel.setToken(null);

        Mockito.when(loginServiceMock.performLogin(loginRequestDto)).thenReturn(usermodel);

        //Test
        Response response = sut.login(loginRequestDto);

        //Verify
        Mockito.verify(loginServiceMock).performLogin(loginRequestDto);
        Assertions.assertEquals(401, response.getStatus());
    }

}
