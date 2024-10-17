package com.tap.controller;

import java.io.IOException;
import java.net.URI;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.tap.model.CacheModel;
import com.tap.service.ProxyService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The {@code ProxyController} class serves as the main controller for a caching proxy server.
 * It leverages the Jetty server to handle HTTP requests and responses, allowing clients to
 * access resources from a specified origin server while caching the responses for future requests.
 *
 * <p>
 * This class provides a command-line interface for users to start the proxy server with the following
 * options:
 * </p>
 * <ul>
 * <li><strong>--port &lt;number&gt;</strong>: Specifies the port on which the proxy server will listen for incoming requests.</li>
 * <li><strong>--origin &lt;url&gt;</strong>: The URL of the origin server from which to fetch responses when requests are forwarded.</li>
 * <li><strong>--clear-cache</strong>: An optional flag that, when set, clears the cached responses.</li>
 * </ul>
 *
 * <p>
 * The flow of the application is as follows:
 * </p>
 * <ol>
 * <li>Upon starting, the class parses command-line arguments using Apache Commons CLI.</li>
 * <li>It initializes a {@link CacheModel} instance to manage cached responses.</li>
 * <li>A {@link ProxyService} instance is created to handle the logic for fetching responses from the origin server and managing the cache.</li>
 * <li>If the <code>--clear-cache</code> option is provided, the cache is cleared, and the application terminates.</li>
 * <li>Otherwise, the proxy server starts on the specified port:</li>
 * <ul>
 * <li>A new Jetty {@link Server} instance is created and configured to handle incoming HTTP requests.</li>
 * <li>An {@link AbstractHandler} is set up to process requests:</li>
 * <ol>
 * <li>The handler extracts the requested URI from the incoming request.</li>
 * <li>The full origin URL is constructed by appending the request URI to the origin server URL.</li>
 * <li>The handler calls {@link ProxyService#fetchFromOrigin(String)} to fetch the response from the origin server, utilizing caching as necessary.</li>
 * <li>Once a response is received, the handler writes the response back to the client, setting the appropriate content type and HTTP status code.</li>
 * <li>If an exception occurs during the processing, a 500 Internal Server Error response is sent to the client.</li>
 * </ol>
 * </ul>
 * <li>The Jetty server starts and listens for incoming requests, remaining active until explicitly stopped.</li>
 * </ol>
 *
 * <p>
 * The server enables clients to retrieve data efficiently by caching responses and minimizes redundant
 * requests to the origin server, thus improving response times and reducing network traffic.
 * </p>
 */
public class ProxyController {

    public static void main(String[] args) throws Exception {
        start(args);
    }

    public static void start(String[] args) throws Exception {
        Options options = new Options();
        options.addOption("p", true, "Port to run the proxy server on"); // Short option for port
        options.addOption("o", true, "URL of the origin server"); // Short option for origin
        options.addOption("c", false, "Clear the cache"); // Short option for clear cache

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println("Error parsing command-line arguments: " + e.getMessage());
            return;
        }

        CacheModel cacheModel = new CacheModel();
        ProxyService proxyService = new ProxyService(cacheModel);

        if (cmd.hasOption("c")) {
            proxyService.clearCache();
            System.out.println("Cache cleared");
            return;
        }

        int port = Integer.parseInt(cmd.getOptionValue("p")); // Use short option for port
        String origin = cmd.getOptionValue("o"); // Use short option for origin

        Server server = new Server(port);

        server.setHandler(new AbstractHandler() {
            @Override
            public void handle(String target, Request baseRequest, HttpServletRequest request,
                               HttpServletResponse response) throws IOException {
                try {
                    URI requestURI = URI.create(request.getRequestURI());
                    String originUrl = origin + requestURI.toString();

                    String proxyResponse = proxyService.fetchFromOrigin(originUrl);

                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write(proxyResponse);
                    baseRequest.setHandled(true);

                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write("Internal Server Error");
                    e.printStackTrace();
                }
            }
        });

        server.start();
        System.out.println("Caching proxy server running on port " + port);
        server.join();
    }
}
