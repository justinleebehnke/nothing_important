package services.person;


import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import dao.AuthTokenDAO;
import model_classes.Event;
import model_classes.Person;
import services.event.EventService;
import services.message.ErrorMessageException;
import services.message.MessageResponse;
import sun.net.www.protocol.http.HttpURLConnection;

public class PersonHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        MessageResponse errorMessage = new MessageResponse("Person Command was unsuccessful");
        Gson gson = new Gson();
        boolean success = false;
        try {
            if (httpExchange.getRequestMethod().toLowerCase().equals("get")) {
                //verify the authToken is valid
                String username = "NONE";
                Headers reqHeaders = httpExchange.getRequestHeaders();
                try {
                    if (reqHeaders.containsKey("Authorization")) {
                        String auth = reqHeaders.getFirst("Authorization");
                        UUID authToken = UUID.fromString(auth);
                        AuthTokenDAO authTokenDAO = new AuthTokenDAO();
                        username = authTokenDAO.read(authToken);
                        if (username.equals("INVALID AUTH TOKEN")) {
                            throw new Exception();
                        }
                    }

                } catch (Exception e) {
                    throw new ErrorMessageException("Auth Token is invalid");
                }

                URI uri = httpExchange.getRequestURI();
                Scanner scanner = new Scanner(uri.toString());
                scanner.useDelimiter("/");
                scanner.delimiter();
                String person = scanner.next();
                String personID;
                String jsonStr;

                if (scanner.hasNext()) {
                    personID = scanner.next();
                    scanner.close();

                    PersonService personService = new PersonService();
                    Person personObject = personService.getSinglePerson(personID, username);

                    jsonStr = gson.toJson(personObject);

                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                    OutputStream outputStream = httpExchange.getResponseBody();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                    outputStreamWriter.write(jsonStr);
                    outputStreamWriter.flush();
                    outputStream.close();

                    success = true;

                } else {
                    //return event array
                    scanner.close();
                    PersonService personService = new PersonService();
                    ArrayList<Person> persons = personService.getAllPersons(username);

                    jsonStr = gson.toJson(persons);

                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream outputStream = httpExchange.getResponseBody();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                    outputStreamWriter.write(jsonStr);
                    outputStreamWriter.flush();
                    outputStream.close();

                    success = true;
                }
            }

            if (!success) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                httpExchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            httpExchange.getResponseBody().close();
            e.printStackTrace();
        }
        catch (Exception e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            //send error services.message
            errorMessage = new MessageResponse(e.toString());
            OutputStream outputStream = httpExchange.getResponseBody();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            gson.toJson(errorMessage, outputStreamWriter);
            outputStreamWriter.flush();
            outputStream.close();
        }

    }
}
