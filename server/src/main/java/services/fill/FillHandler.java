package services.fill;


import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.util.Scanner;

import services.message.ErrorMessageException;
import services.message.InvalidInputException;
import services.message.MessageResponse;
import services.register.RegisterRequest;
import sun.net.www.protocol.http.HttpURLConnection;

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        boolean success = false;
        MessageResponse errorMessage = new MessageResponse("Fill Command was unsuccessful");
        Gson gson = new Gson();
        try {
            if (httpExchange.getRequestMethod().toLowerCase().equals("post")) {

                URI uri = httpExchange.getRequestURI();
                Scanner scanner = new Scanner(uri.toString());
                scanner.useDelimiter("/");
                scanner.delimiter();
                scanner.next();
                String userName = scanner.next();
                int numGenerations;

                if (scanner.hasNext()) {
                    numGenerations = scanner.nextInt();
                } else {
                    numGenerations = 4;
                }
                if (numGenerations > 8) {
                    throw new ErrorMessageException("No more than 8 generations please");
                }
                scanner.close();

                FillRequest fillRequest = new FillRequest(numGenerations, userName);
                FillService fillService = new FillService();
                MessageResponse response = new MessageResponse(
                        fillService.fill(fillRequest.getUsername(), fillRequest.getGenerations()));
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                String jsonStr = gson.toJson(response);

                OutputStream outputStream = httpExchange.getResponseBody();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                outputStreamWriter.write(jsonStr);
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
            errorMessage = new MessageResponse(e.toString());
            OutputStream outputStream = httpExchange.getResponseBody();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            gson.toJson(errorMessage, outputStreamWriter);
            outputStreamWriter.flush();
            outputStream.close();

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
