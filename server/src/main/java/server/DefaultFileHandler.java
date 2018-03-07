package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.file.Files;


public class DefaultFileHandler implements HttpHandler {

private static String pathToFolder = "/home/justin/AndroidStudioProjects/family_map_full_project_2/" +
        "family_map_full_project/server/src/main/web";

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        try {
            if (httpExchange.getRequestMethod().toLowerCase().equals("get")) {

                URI requestURI = httpExchange.getRequestURI();
                String request = requestURI.toString();
                if (request.equals("/") || request.equals("")) {
                    request = "/index.html";
                }
                String path = pathToFolder + request;

                File file = new File(path);
                if (file.exists() && file.canRead()) {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = httpExchange.getResponseBody();
                    Files.copy(file.toPath(), respBody);
                    respBody.close();
                }
                else {
                    path = pathToFolder + "/HTML/404.html";
                    File notFoundFile = new File(path);
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    OutputStream respBody = httpExchange.getResponseBody();
                    Files.copy(notFoundFile.toPath(), respBody);
                    respBody.close();
                }
            }
            else {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                httpExchange.getResponseBody().close();
            }
        }
        catch (FileNotFoundException e) {
            System.out.printf("FILE NOT FOUND %s", e.toString());
            httpExchange.getResponseBody().close();
            e.printStackTrace();

        }
        catch (IOException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            httpExchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
