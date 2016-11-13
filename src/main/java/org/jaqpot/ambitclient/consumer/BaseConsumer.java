/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jaqpot.ambitclient.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import org.asynchttpclient.AsyncHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.HttpResponseHeaders;
import org.asynchttpclient.HttpResponseStatus;
import org.asynchttpclient.request.body.multipart.Part;

/**
 * @author Angelos Valsamis
 * @author Charalampos Chomenidis
 */
public abstract class BaseConsumer {

    protected final AsyncHttpClient httpClient;
    protected final ObjectMapper mapper;

    public BaseConsumer(AsyncHttpClient httpClient, ObjectMapper mapper) {
        this.httpClient = httpClient;
        this.mapper = mapper;
    }

    public <T> CompletableFuture<T> get(String path, Class<T> c) {
        return httpClient
                .prepareGet(path)
                .addHeader("Accept", "application/json")
                .execute(new AsyncHandler<T>() {

                    private PipedOutputStream pos;
                    private PipedInputStream pis;

                    io.netty.handler.codec.http.HttpHeaders headers;

                    @Override
                    public AsyncHandler.State onStatusReceived(HttpResponseStatus status) throws Exception {
                        int statusCode = status.getStatusCode();
                        if (statusCode >= 400) {
                            return AsyncHandler.State.ABORT;
                        }
                        pos = new PipedOutputStream();
                        pis = new PipedInputStream(pos);
                        return AsyncHandler.State.CONTINUE;
                    }

                    @Override
                    public AsyncHandler.State onHeadersReceived(HttpResponseHeaders h) throws Exception {
                        headers = h.getHeaders();
                        return AsyncHandler.State.CONTINUE;
                    }

                    @Override
                    public T onCompleted() throws Exception {
                        pos.flush();
                        return mapper.readValue(pis, c);
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                    }

                    @Override
                    public AsyncHandler.State onBodyPartReceived(HttpResponseBodyPart httpResponseBodyPart) throws Exception {
                        pos.write(httpResponseBodyPart.getBodyPartBytes());
                        return AsyncHandler.State.CONTINUE;
                    }
                })
                .toCompletableFuture();
    }

    private <T> CompletableFuture<T> post(BoundRequestBuilder builder, Class<T> c) {
        return builder.execute(new AsyncHandler<T>() {

            private PipedOutputStream pos;
            private PipedInputStream pis;
            io.netty.handler.codec.http.HttpHeaders headers;

            @Override
            public AsyncHandler.State onStatusReceived(HttpResponseStatus status) throws Exception {
                int statusCode = status.getStatusCode();
                if (statusCode >= 400) {
                    return AsyncHandler.State.ABORT;
                }
                pos = new PipedOutputStream();
                pis = new PipedInputStream(pos);
                return AsyncHandler.State.CONTINUE;
            }

            @Override
            public AsyncHandler.State onHeadersReceived(HttpResponseHeaders h) throws Exception {
                headers = h.getHeaders();
                return AsyncHandler.State.CONTINUE;
            }

            @Override
            public T onCompleted() throws Exception {
                pos.flush();
                return mapper.readValue(pis, c);
            }

            @Override
            public void onThrowable(Throwable t) {
            }

            @Override
            public AsyncHandler.State onBodyPartReceived(HttpResponseBodyPart httpResponseBodyPart) throws Exception {
                pos.write(httpResponseBodyPart.getBodyPartBytes());
                return AsyncHandler.State.CONTINUE;
            }
        }).toCompletableFuture();
    }

    public <T> CompletableFuture<T> postForm(String path, Map<String, List<String>> parameters, Class<T> c) {
        return post(httpClient
                .preparePost(path)
                .setFormParams(parameters)
                .addHeader("Accept", "application/json"),
                c
        );
    }

    public <T> CompletableFuture<T> postMultipart(String path, List<Part> bodyParts, Class<T> c) {
        return post(httpClient
                .preparePost(path)
                .setBodyParts(bodyParts)
                .addHeader("Accept", "application/json"),
                c
        );
    }

    protected final String createPath(String... paths) {
        StringJoiner joiner = new StringJoiner("/");
        for (String path : paths) {
            joiner.add(path.substring(0 + (path.startsWith("/") ? 1 : 0),
                    path.length() - (path.endsWith("/") ? 1 : 0))
            );
        }
        return joiner.toString();
    }
}
