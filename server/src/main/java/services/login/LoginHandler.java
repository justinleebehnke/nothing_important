package services.login;


import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import services.message.ErrorMessageException;
import services.message.InvalidInputException;
import services.message.MessageResponse;
import services.register.RegisterRequest;
import sun.net.www.protocol.http.HttpURLConnection;

public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        boolean success = false;
        MessageResponse errorMessage = new MessageResponse("User Login was unsuccessful");
        Gson gson = new Gson();
        try {
            if (httpExchange.getRequestMethod().toLowerCase().equals("post")) {

                InputStreamReader inputStreamReader = new InputStreamReader(httpExchange.getRequestBody());

                LoginRequest loginRequest;
                try {
                    loginRequest = gson.fromJson(inputStreamReader, LoginRequest.class);
                } catch (Exception e) {
                    throw new InvalidInputException("Failed to create object from JSON", e);
                }

                LoginService loginService = new LoginService();
                LoginResult loginResult;
                try {
                    loginResult = loginService.login(loginRequest);
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                    OutputStream outputStream = httpExchange.getResponseBody();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                    gson.toJson(loginResult, outputStreamWriter);
                    outputStreamWriter.flush();

                    outputStream.close();
                    success = true;
                } catch (ErrorMessageException e) {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    errorMessage = new MessageResponse(e.toString());
                    OutputStream outputStream = httpExchange.getResponseBody();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                    gson.toJson(errorMessage, outputStreamWriter);
                    outputStreamWriter.flush();
                    outputStream.close();
                }

            }

            if (!success) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                //send error services.message
                OutputStream outputStream = httpExchange.getResponseBody();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                gson.toJson(errorMessage, outputStreamWriter);
                outputStreamWriter.flush();
                outputStream.close();
            }
        }
        catch (IOException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            //send error services.message
            errorMessage = new MessageResponse(e.toString());
            OutputStream outputStream = httpExchange.getResponseBody();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            gson.toJson(errorMessage, outputStreamWriter);
            outputStreamWriter.flush();
            outputStream.close();

            e.printStackTrace();
        }
        catch (InvalidInputException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            //send error services.message
            errorMessage = new MessageResponse(e.toString());
            OutputStream outputStream = httpExchange.getResponseBody();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            gson.toJson(errorMessage, outputStreamWriter);
            outputStreamWriter.flush();
            outputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
