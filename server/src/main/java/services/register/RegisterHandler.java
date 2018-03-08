package services.register;

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
import sun.net.www.protocol.http.HttpURLConnection;

public class RegisterHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        boolean success = false;
        MessageResponse errorMessage = new MessageResponse("User Register was unsuccessful");
        Gson gson = new Gson();
        try {
            if (httpExchange.getRequestMethod().toLowerCase().equals("post")) {

                InputStreamReader inputStreamReader = new InputStreamReader(httpExchange.getRequestBody());

                RegisterRequest registerRequest;
                try {
                    registerRequest = gson.fromJson(inputStreamReader, RegisterRequest.class);
                }
                catch (Exception e) {
                    throw new InvalidInputException("Failed to create object from JSON", e);
                }

                RegisterService registerService = new RegisterService();
                RegisterResult registerResult = registerService.register(registerRequest);

                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream outputStream = httpExchange.getResponseBody();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                gson.toJson(registerResult, outputStreamWriter);
                outputStreamWriter.flush();

                outputStream.close();
                success = true;
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
            OutputStream outputStream = httpExchange.getResponseBody();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            gson.toJson(errorMessage, outputStreamWriter);
            outputStreamWriter.flush();
            outputStream.close();
        }
        catch (ErrorMessageException e) {
            //return the error message
            errorMessage = new MessageResponse(e.toString());
            OutputStream outputStream = httpExchange.getResponseBody();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            gson.toJson(errorMessage, outputStreamWriter);
            outputStreamWriter.flush();
            outputStream.close();
        }
    }
}
