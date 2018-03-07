package services.event;


import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import services.message.MessageResponse;
import sun.net.www.protocol.http.HttpURLConnection;

public class EventHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        boolean success = false;
        try {
            if (httpExchange.getRequestMethod().toLowerCase().equals("get")) {

                //TODO Implement the EVENT HANDLERS
                MessageResponse response = new MessageResponse("EVENT HANDLER METHOD STUBBED");
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);


                Gson gson = new Gson();
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
            httpExchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
