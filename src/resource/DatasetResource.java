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

package resource;

import client.AmbitClientFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.dataset.Dataset;
import model.dto.ambit.AmbitTask;
import model.dto.ambit.AmbitTaskArray;
import org.asynchttpclient.*;
import org.asynchttpclient.request.body.multipart.ByteArrayPart;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class DatasetResource {

    private final String PATH = "https://apps.ideaconsult.net/enmtest/dataset";

    public DatasetResource(){
        mapper = new ObjectMapper();
        ambitClientFactory = new AmbitClientFactory();
    }

    private ObjectMapper mapper;


    AmbitClientFactory ambitClientFactory;

    public Dataset getDatasetById(String datasetId) {
        Dataset result=null;
        AsyncHttpClient c = ambitClientFactory.getClient();

        Future<Dataset> f = c
                .prepareGet(PATH+"/"+datasetId)
                .addHeader("Accept","application/json")
                .execute(new AsyncHandler<Dataset>() {

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
                        headers =  h.getHeaders();
                        // The headers have been read
                        // If you don't want to read the body, or stop processing the response
                        return State.CONTINUE;
                    }

                    @Override
                    public Dataset onCompleted() throws Exception {
                        // Will be invoked once the response has been fully read or a ResponseComplete exception
                        // has been thrown.
                        // NOTE: should probably use Content-Encoding from headers
                        bytes.flush();
                        return mapper.readValue(bytes.toByteArray(), Dataset.class);
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
            result=f.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }


    public AmbitTask createDatasetByPDB(byte[] file) {

        AmbitTask bodyResponse=null;
        AsyncHttpClient c = ambitClientFactory.getClient();

        String fileName = UUID.randomUUID().toString() + ".pdb";

        Future<AmbitTaskArray> f = c
                .preparePost(PATH)
                .addBodyPart(new ByteArrayPart("file",file,"octet-stream",Charset.defaultCharset(),fileName))
                .addHeader("Accept","application/json")
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
                        headers =  h.getHeaders();
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
            bodyResponse=f.get().getTask().get(0);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return bodyResponse;
    }
}
