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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jaqpot.ambitclient.model.BundleData;
import org.jaqpot.ambitclient.model.dto.bundle.BundleProperties;
import org.jaqpot.ambitclient.model.dto.bundle.BundleSubstances;
import org.asynchttpclient.*;

import java.util.concurrent.CompletableFuture;

import org.jaqpot.ambitclient.model.dto.ambit.AmbitTask;
import org.jaqpot.ambitclient.model.dto.ambit.AmbitTaskArray;
import org.jaqpot.ambitclient.serialize.Serializer;
import org.jaqpot.ambitclient.util.MultiValuedHashMap;
import org.jaqpot.ambitclient.util.MultiValuedMap;

/**
 * @author Angelos Valsamis
 * @author Charalampos Chomenidis
 */
public class BundleResourceConsumer extends BaseConsumer {

    private static final String BUNDLE = "bundle";
    private static final String BUNDLE_BY_ID = "bundle/%s";

    private static final String BUNDLE_SUBSTANCES_BY_ID = "bundle/%s/substance";
    private static final String BUNDLE_PROPERTIES_BY_ID = "bundle/%s/property";

    private final String basePath;
    private final String bundlePath;
    private final String bundleByIdPath;
    private final String bundleSubstancesByIdPath;
    private final String bundlePropertiesByIdPath;

    public BundleResourceConsumer(Serializer serializer, AsyncHttpClient httpClient, String basePath) {
        super(httpClient, serializer);
        this.basePath = basePath;
        this.bundlePath = createPath(this.basePath, BUNDLE);
        this.bundleByIdPath = createPath(this.basePath, BUNDLE_BY_ID);
        this.bundleSubstancesByIdPath = createPath(this.basePath, BUNDLE_SUBSTANCES_BY_ID);
        this.bundlePropertiesByIdPath = createPath(this.basePath, BUNDLE_PROPERTIES_BY_ID);
    }

    public CompletableFuture<AmbitTask> createBundle(String description, String userName, String substanceOwner) {
        String path = bundlePath;
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("title", Arrays.asList("owner-bundle"));
        parameters.put("description", Arrays.asList(description));
        parameters.put("source", Arrays.asList(userName));
        parameters.put("seeAlso", Arrays.asList(substanceOwner));
        parameters.put("license", Arrays.asList("Copyright of " + userName));
        parameters.put("rightsHolder", Arrays.asList(userName));
        parameters.put("maintainer", Arrays.asList(userName));
        parameters.put("stars", Arrays.asList("1"));
        return postForm(path, parameters, AmbitTaskArray.class)
                .thenApply((ta) -> ta.getTask().get(0));
    }

    public CompletableFuture<BundleSubstances> getSubstancesByBundleId(String bundleId) {
        String path = String.format(bundleSubstancesByIdPath, bundleId);
        return get(path, BundleSubstances.class);
    }

    public CompletableFuture<BundleProperties> getPropertiesByBundleId(String bundleId) {
        String path = String.format(bundlePropertiesByIdPath, bundleId);
        return get(path, BundleProperties.class);
    }

    public CompletableFuture<BundleData> getBundleById(String bundleId) {
        String path = String.format(bundleByIdPath, bundleId);
        return get(path, BundleData.class);
    }

    public CompletableFuture<AmbitTask> putSubstanceByBundleId(String bundleId, String substanceURI) {
        String path = String.format(bundleSubstancesByIdPath, bundleId);
        MultiValuedMap<String, String> formParameters = new MultiValuedHashMap<>();
        formParameters.putSingle("substance_uri", substanceURI);
        formParameters.putSingle("command", "add");
        return put(path, formParameters, AmbitTaskArray.class)
                .thenApply((ta) -> ta.getTask().get(0));
    }

    public CompletableFuture<AmbitTask> putPropertyByBundleId(String bundleId, String topCategory, String subCategory) {
        String path = String.format(bundlePropertiesByIdPath, bundleId);
        MultiValuedMap<String, String> formParameters = new MultiValuedHashMap<>();
        formParameters.putSingle("topcategory", topCategory);
        formParameters.putSingle("endpointcategory", subCategory);
        formParameters.putSingle("command", "add");
        return put(path, formParameters, AmbitTaskArray.class)
                .thenApply((ta) -> ta.getTask().get(0));
    }

//    public Object getBundleByJsonLD(String bundleId) {
//
//        BundleSubstances result = null;
//
//        Future<Object> f = httpClient
//                .prepareGet(PATH + "/" + bundleId + "/substance")
//                .addHeader("Accept", "application/ld+json")
//                .execute(new AsyncHandler<Object>() {
//
//                    private ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                    io.netty.handler.codec.http.HttpHeaders headers;
//
//                    @Override
//                    public AsyncHandler.State onStatusReceived(HttpResponseStatus status) throws Exception {
//                        int statusCode = status.getStatusCode();
//                        // The Status have been read
//                        // If you don't want to read the headers,body or stop processing the response
//                        if (statusCode >= 500) {
//                            return AsyncHandler.State.ABORT;
//                        }
//                        return AsyncHandler.State.CONTINUE;
//                    }
//
//                    @Override
//                    public AsyncHandler.State onHeadersReceived(HttpResponseHeaders h) throws Exception {
//                        headers = h.getHeaders();
//                        // The headers have been read
//                        // If you don't want to read the body, or stop processing the response
//                        return AsyncHandler.State.CONTINUE;
//                    }
//
//                    @Override
//                    public Object onCompleted() throws Exception {
//                        // Will be invoked once the response has been fully read or a ResponseComplete exception
//                        // has been thrown.
//                        // NOTE: should probably use Content-Encoding from headers
//                        bytes.flush();
//                        //System.out.println(bytes.toString());
//                        InputStream inputStream = new ByteArrayInputStream(bytes.toByteArray());
//
//                        Object jsonObject = JsonUtils.fromInputStream(inputStream);
//                        Map<String, String> context = new HashMap<String, String>();
//                        context.put("sso", "http://semanticscience.org/resource_consumers/");
//                        context.put("otee", "http://www.opentox.org/echaEndpoints.owl#");
//                        context.put("bx", "http://purl.org/net/nknouf/ns/bibtex#");
//                        context.put("npo", "http://purl.bioontology.org/ontology/npo#");
//                        context.put("dcterms", "http://purl.org/dc/terms/");
//                        context.put("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
//                        context.put("substance", "https://apps.ideaconsult.net/data/substance/");
//                        context.put("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
//                        context.put("ot", "http://www.opentox.org/api/1.1#");
//                        context.put("dc", "http://purl.org/dc/elements/1.1/");
//                        context.put("enm", "http://purl.enanomapper.org/onto/");
//                        context.put("foaf", "http://xmlns.com/foaf/0.1/");
//                        context.put("ota", "http://www.opentox.org/algorithmTypes.owl#");
//                        context.put("as", "https://apps.ideaconsult.net/data/assay/");
//                        context.put("void", "http://rdfs.org/ns/void#");
//                        context.put("mgroup", "https://apps.ideaconsult.net/data/measuregroup/");
//                        context.put("obo", "http://purl.obolibrary.org/obo/");
//                        context.put("ap", "https://apps.ideaconsult.net/data/protocol/");
//                        context.put("am", "https://apps.ideaconsult.net/data/model/");
//                        context.put("sio", "http://semanticscience.org/resource_consumers/");
//                        context.put("ac", "https://apps.ideaconsult.net/data/compound/");
//                        context.put("owl", "http://www.w3.org/2002/07/owl#");
//                        context.put("ep", "https://apps.ideaconsult.net/data/endpoint/");
//                        context.put("owner", "https://apps.ideaconsult.net/data/owner/");
//                        context.put("xsd", "http://www.w3.org/2001/XMLSchema#");
//                        context.put("ad", "https://apps.ideaconsult.net/data/dataset/");
//                        context.put("ag", "https://apps.ideaconsult.net/data/algorithm/");
//                        context.put("af", "https://apps.ideaconsult.net/data/feature/");
//                        context.put("bao", "http://www.bioassayontology.org/bao#");
//                        JsonLdOptions options = new JsonLdOptions();
//                        options.useNamespaces = false;
//                        options.setExplicit(false);
//                        options.setCompactArrays(false);
//                        // Customise options...
//                        // Call whichever JSONLD function you want! (e.g. compact)
//                        Object compact = JsonLdProcessor.compact(jsonObject, context, options);
//                        return compact;
//                    }
//
//                    @Override
//                    public void onThrowable(Throwable t) {
//                    }
//
//                    @Override
//                    public AsyncHandler.State onBodyPartReceived(HttpResponseBodyPart httpResponseBodyPart) throws Exception {
//                        bytes.write(httpResponseBodyPart.getBodyPartBytes());
//                        bytes.flush();
//                        return AsyncHandler.State.CONTINUE;
//                    }
//                });
//
//        try {
//            return f.get();
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
}
