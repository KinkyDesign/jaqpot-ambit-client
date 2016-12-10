/*
 *
 *   Ambit Client
 *
 *   Ambit Client is licensed by GPL v3 as specified hereafter. Additional components may ship
 *   with some other licence as will be specified therein.
 *
 *   Copyright (C) 2016 KinkyDesign
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *   Source code:
 *   The source code of Ambit Client is available on github at:
 *   https://github.com/KinkyDesign/AmbitClient
 *   All source files of Ambit Client that are stored on github are licensed
 *   with the aforementioned licence.
 *
 */
package org.jaqpot.ambitclient.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;
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
import org.jaqpot.ambitclient.exception.AmbitClientException;

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

        CompletableFuture<T> future = httpClient
                .prepareGet(path)
                .addHeader("Accept", "application/json")
                .execute(new AsyncHandler<T>() {

                    private InputStream sis;
                    io.netty.handler.codec.http.HttpHeaders headers;

                    @Override
                    public AsyncHandler.State onStatusReceived(HttpResponseStatus status) throws Exception {
                        int statusCode = status.getStatusCode();
                        if (statusCode >= 400) {
                            return AsyncHandler.State.ABORT;
                        }
                        return AsyncHandler.State.CONTINUE;
                    }

                    @Override
                    public AsyncHandler.State onHeadersReceived(HttpResponseHeaders h) throws Exception {
                        headers = h.getHeaders();
                        return AsyncHandler.State.CONTINUE;
                    }

                    @Override
                    public T onCompleted() throws Exception {
                        return mapper.readValue(sis, c);
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                        throw new AmbitClientException(t);
                    }

                    @Override
                    public AsyncHandler.State onBodyPartReceived(HttpResponseBodyPart httpResponseBodyPart) throws Exception {
                        if (sis == null) {
                            sis = new ByteInputStream(httpResponseBodyPart.getBodyPartBytes(), httpResponseBodyPart.length());
                        } else {
                            sis = new SequenceInputStream(sis, new ByteInputStream(httpResponseBodyPart.getBodyPartBytes(), httpResponseBodyPart.length()));
                        }
                        return AsyncHandler.State.CONTINUE;
                    }
                }).toCompletableFuture();
        return future;
    }

    private <T> CompletableFuture<T> post(BoundRequestBuilder builder, Class<T> c) {
        return builder.execute(new AsyncHandler<T>() {

            private InputStream sis;
            io.netty.handler.codec.http.HttpHeaders headers;

            @Override
            public AsyncHandler.State onStatusReceived(HttpResponseStatus status) throws Exception {
                int statusCode = status.getStatusCode();
                if (statusCode >= 400) {
                    return AsyncHandler.State.ABORT;
                }
                return AsyncHandler.State.CONTINUE;
            }

            @Override
            public AsyncHandler.State onHeadersReceived(HttpResponseHeaders h) throws Exception {
                headers = h.getHeaders();
                return AsyncHandler.State.CONTINUE;
            }

            @Override
            public T onCompleted() throws Exception {
                return mapper.readValue(sis, c);
            }

            @Override
            public void onThrowable(Throwable t) {
                throw new AmbitClientException(t);
            }

            @Override
            public AsyncHandler.State onBodyPartReceived(HttpResponseBodyPart httpResponseBodyPart) throws Exception {
                if (sis == null) {
                    sis = new ByteInputStream(httpResponseBodyPart.getBodyPartBytes(), httpResponseBodyPart.length());
                } else {
                    sis = new SequenceInputStream(sis, new ByteInputStream(httpResponseBodyPart.getBodyPartBytes(), httpResponseBodyPart.length()));
                }
                return AsyncHandler.State.CONTINUE;
            }
        }).toCompletableFuture();
    }


    public <T> CompletableFuture<T> put(String path, Map<String, List<String>> parameters, Class<T> c) {

        CompletableFuture<T> future = httpClient
                .preparePut(path)
                .setFormParams(parameters)
                .addHeader("Accept", "application/json")
                .execute(new AsyncHandler<T>() {

                    private InputStream sis;
                    io.netty.handler.codec.http.HttpHeaders headers;

                    @Override
                    public AsyncHandler.State onStatusReceived(HttpResponseStatus status) throws Exception {
                        int statusCode = status.getStatusCode();
                        if (statusCode >= 400) {
                            return AsyncHandler.State.ABORT;
                        }
                        return AsyncHandler.State.CONTINUE;
                    }

                    @Override
                    public AsyncHandler.State onHeadersReceived(HttpResponseHeaders h) throws Exception {
                        headers = h.getHeaders();
                        return AsyncHandler.State.CONTINUE;
                    }

                    @Override
                    public T onCompleted() throws Exception {
                        return mapper.readValue(sis, c);
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                        throw new AmbitClientException(t);
                    }

                    @Override
                    public AsyncHandler.State onBodyPartReceived(HttpResponseBodyPart httpResponseBodyPart) throws Exception {
                        if (sis == null) {
                            sis = new ByteInputStream(httpResponseBodyPart.getBodyPartBytes(), httpResponseBodyPart.length());
                        } else {
                            sis = new SequenceInputStream(sis, new ByteInputStream(httpResponseBodyPart.getBodyPartBytes(), httpResponseBodyPart.length()));
                        }
                        return AsyncHandler.State.CONTINUE;
                    }
                }).toCompletableFuture();
        return future;
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
