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
import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.utils.JsonUtils;
import model.dto.bundle.BundleProperties;
import model.dto.bundle.BundleSubstances;
import org.asynchttpclient.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by Angelos Valsamis on 13/10/2016.
 */
public class BundleResource {

    String PATH = "https://apps.ideaconsult.net/enmtest/bundle";

    private ObjectMapper mapper;

    public BundleResource(){
        mapper = new ObjectMapper();
        ambitClientFactory = new AmbitClientFactory();
    }

    private AmbitClientFactory ambitClientFactory;

    public BundleSubstances getSubstances (String bundleId) {


        BundleSubstances result = null;
        AsyncHttpClient c = ambitClientFactory.getClient();

        Future<BundleSubstances> f = c
                .prepareGet(PATH+"/"+bundleId+"/substance")
                .addHeader("Accept","application/json")
                .execute(new AsyncHandler<BundleSubstances>() {

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
                    public BundleSubstances onCompleted() throws Exception {
                        // Will be invoked once the response has been fully read or a ResponseComplete exception
                        // has been thrown.
                        // NOTE: should probably use Content-Encoding from headers
                        bytes.flush();
                        return mapper.readValue(bytes.toByteArray(), BundleSubstances.class);
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

    public Object getBundleByJsonLD (String bundleId) {


        BundleSubstances result = null;
        AsyncHttpClient c = ambitClientFactory.getClient();

        Future<Object> f = c
                .prepareGet(PATH+"/"+bundleId+"/substance")
                .addHeader("Accept","application/ld+json")
                .execute(new AsyncHandler<Object>() {

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
                    public Object onCompleted() throws Exception {
                        // Will be invoked once the response has been fully read or a ResponseComplete exception
                        // has been thrown.
                        // NOTE: should probably use Content-Encoding from headers
                        bytes.flush();
                        //System.out.println(bytes.toString());
                        InputStream inputStream = new ByteArrayInputStream(bytes.toByteArray());

                        Object jsonObject = JsonUtils.fromInputStream(inputStream);
                        Map<String, String> context = new HashMap<String, String>();
                        context.put("sso","http://semanticscience.org/resource/");
                        context.put("otee" , "http://www.opentox.org/echaEndpoints.owl#");
                        context.put("bx" , "http://purl.org/net/nknouf/ns/bibtex#");
                        context.put("npo" , "http://purl.bioontology.org/ontology/npo#");
                        context.put("dcterms" , "http://purl.org/dc/terms/");
                        context.put("rdfs" , "http://www.w3.org/2000/01/rdf-schema#");
                        context.put("substance" , "https://apps.ideaconsult.net/data/substance/");
                        context.put("rdf" , "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
                        context.put("ot" , "http://www.opentox.org/api/1.1#");
                        context.put("dc" , "http://purl.org/dc/elements/1.1/");
                        context.put("enm" , "http://purl.enanomapper.org/onto/");
                        context.put("foaf" , "http://xmlns.com/foaf/0.1/");
                        context.put("ota" , "http://www.opentox.org/algorithmTypes.owl#");
                        context.put("as" , "https://apps.ideaconsult.net/data/assay/");
                        context.put("void" , "http://rdfs.org/ns/void#");
                        context.put("mgroup" , "https://apps.ideaconsult.net/data/measuregroup/");
                        context.put("obo" , "http://purl.obolibrary.org/obo/");
                        context.put("ap" , "https://apps.ideaconsult.net/data/protocol/");
                        context.put("am" , "https://apps.ideaconsult.net/data/model/");
                        context.put("sio" , "http://semanticscience.org/resource/");
                        context.put("ac" , "https://apps.ideaconsult.net/data/compound/");
                        context.put("owl" , "http://www.w3.org/2002/07/owl#");
                        context.put("ep" , "https://apps.ideaconsult.net/data/endpoint/");
                        context.put("owner" , "https://apps.ideaconsult.net/data/owner/");
                        context.put("xsd" , "http://www.w3.org/2001/XMLSchema#");
                        context.put("ad" , "https://apps.ideaconsult.net/data/dataset/");
                        context.put("ag" , "https://apps.ideaconsult.net/data/algorithm/");
                        context.put("af" , "https://apps.ideaconsult.net/data/feature/");
                        context.put("bao" ,"http://www.bioassayontology.org/bao#");
                        JsonLdOptions options = new JsonLdOptions();
                        options.useNamespaces=false;
                        options.setExplicit(false);
                        options.setCompactArrays(false);
                        // Customise options...
                        // Call whichever JSONLD function you want! (e.g. compact)
                        Object compact = JsonLdProcessor.compact(jsonObject,context,options);
                        return compact;
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
            return f.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    public BundleProperties getProperties (String bundleId) {

        String PATH = "https://apps.ideaconsult.net/enmtest/bundle";
        ambitClientFactory = new AmbitClientFactory();

        AsyncHttpClient c = ambitClientFactory.getClient();
        BundleProperties result = null;
        Future<BundleProperties> f = c
                .prepareGet(PATH+"/"+bundleId+"/property")
                .addHeader("Accept","application/json")
                .execute(new AsyncHandler<BundleProperties>() {

                    private ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    io.netty.handler.codec.http.HttpHeaders headers;

                    @Override
                    public State onStatusReceived(HttpResponseStatus status) throws Exception {
                        int statusCode = status.getStatusCode();
                        // The Status have been read
                        // If you don't want to read the headers,body or stop processing the response
                        if (statusCode >= 500) {
                            return State.ABORT;
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
                    public BundleProperties onCompleted() throws Exception {
                        // Will be invoked once the response has been fully read or a ResponseComplete exception
                        // has been thrown.
                        // NOTE: should probably use Content-Encoding from headers
                        bytes.flush();
                        System.out.println(bytes.toString());
                        return mapper.readValue(bytes.toByteArray(), BundleProperties.class);
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

}
