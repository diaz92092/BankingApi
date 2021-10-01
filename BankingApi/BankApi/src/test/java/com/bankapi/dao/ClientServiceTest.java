package com.bankapi.dao;
import com.bankapi.exceptions.ClientNotFoundException;
import com.bankapi.exceptions.DatabaseException;
import com.bankapi.model.Client;
import com.bankapi.service.ClientService;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
    For every unit test you will follow a similar procedure:
    You will identify the unit under test, and mock anything else.

     Our unit tests will follow the pattern of the 3 A's.
     Arrange - set up the test, identify the SUT, 'mock' and 'stub' any 'collaborators'
     Act - invoke the SUT, preform the actual test
     Assert - check to make sure the expected result holds true
 */


    @RunWith(MockitoJUnitRunner.class)
    public class ClientServiceTest {

        ClientRepository sut;

        @Mock
        Connection mockConn;

        @Mock
        PreparedStatement mockStatement;

        @Mock
        ResultSet mockResults;



        @Before
        public void setUp() {
            sut = new ClientRepository(mockConn);
        }

        @After
        public void tearDown() {
            sut = null;
        }

        @Rule
        public ExpectedException exceptionRule = ExpectedException.none();

        @Test
        public void GetClientEntryByIdSuccess() throws SQLException, ClientNotFoundException, DatabaseException {
            //Arrange
            int id = 1;
            String sql = "SELECT * FROM clients c WHERE c.id = ?";
            Mockito.when(mockConn.prepareStatement(sql)).thenReturn(mockStatement);
            Mockito.doNothing().when(mockStatement).setInt(1, id);
            Mockito.when(mockStatement.executeQuery()).thenReturn(mockResults);
            Mockito.when(mockResults.next()).thenReturn(true);
            Mockito.when(mockResults.getInt("id")).thenReturn(id);


            //Act
            Client row = sut.getClientById(1);

            //Assert
            //check equality, assert that the thing is what we want
            Assert.assertEquals(id, row.getId());
            //check invocation, assert that the method was invoked exactly the desired number of times
            verify(mockStatement, times(1)).executeQuery();

            //verify an exception is thrown, see @Rule annotation above class
            //we aren't expecting this, but just for example
            //exceptionRule.expect(NoSQLResultsException.class);
        }


    }
