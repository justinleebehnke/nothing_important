package services.load;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import services.login.LoginRequest;
import services.message.InvalidInputException;
import services.message.MessageResponse;
import sun.net.www.protocol.http.HttpURLConnection;

public class LoadHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        boolean success = false;
        MessageResponse errorMessage = new MessageResponse("User Login was unsuccessful");
        Gson gson = new Gson();
        try {
            if (httpExchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStreamReader inputStreamReader = new InputStreamReader(httpExchange.getRequestBody());

                LoadRequest loadRequest;
                try {
                    loadRequest = gson.fromJson(inputStreamReader, LoadRequest.class);
                } catch (Exception e) {
                    throw new InvalidInputException("Failed to create object from JSON", e);
                }

                LoadService loadService = new LoadService();
                MessageResponse response =  loadService.load(loadRequest);

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
                httpExchange.getResponseBody().close();
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
