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
import org.jaqpot.ambitclient.model.dto.ambit.AmbitTask;
import org.jaqpot.ambitclient.model.dto.ambit.AmbitTaskArray;
import org.asynchttpclient.*;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AlgorithmResourceConsumer {

    private final String PATH = "https://apps.ideaconsult.net/enmtest/algorithm";

    private final ObjectMapper mapper;
    private final AsyncHttpClient httpClient;

    public AlgorithmResourceConsumer(ObjectMapper mapper, AsyncHttpClient httpClient) {
        this.mapper = mapper;
        this.httpClient = httpClient;
    }

    public AmbitTask mopacOriginalStructure(String datasetURI, String options) {

        String algorithmName = "ambit2.mopac.MopacOriginalStructure";
        AmbitTask bodyResponse = null;

        Future<AmbitTaskArray> f = httpClient
                .preparePost(PATH + "/" + algorithmName)
                .addFormParam("dataset_uri", datasetURI)
                .addFormParam("mopac_commands", options)
                .addHeader("Accept", "application/json")
                .execute(new AsyncHandler<AmbitTaskArray>() {

                    private ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    io.netty.handler.codec.http.HttpHeaders headers;

                    @Override
                    public State onStatusReceived(HttpResponseStatus status) throws Exception {
                        int statusCode = status.getStatusCode();
                        // The Status have been read
                        // If you don't want to read the headers,body or stop processing the response
                        if (statusCode >= 500) {
                            return AsyncHandler.State.ABORT;
                        }
                        return State.CONTINUE;
                    }

                    @Override
                    public State onHeadersReceived(HttpResponseHeaders h) throws Exception {
                        headers = h.getHeaders();
                        // The headers have been read
                        // If you don't want to read the body, or stop processing the response
                        return State.CONTINUE;
                    }

                    @Override
                    public AmbitTaskArray onCompleted() throws Exception {
                        // Will be invoked once the response has been fully read or a ResponseComplete exception
                        // has been thrown.
                        // NOTE: should probably use Content-Encoding from headers
                        bytes.flush();
                        return mapper.readValue(bytes.toByteArray(), AmbitTaskArray.class);
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                    }

                    @Override
                    public State onBodyPartReceived(HttpResponseBodyPart httpResponseBodyPart) throws Exception {
                        bytes.write(httpResponseBodyPart.getBodyPartBytes());
                        bytes.flush();
                        return State.CONTINUE;
                    }
                });
        try {
            bodyResponse = f.get().getTask().get(0);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return bodyResponse;
    }
}
