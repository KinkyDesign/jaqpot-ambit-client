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

import org.jaqpot.ambitclient.model.dataset.Dataset;
import org.jaqpot.ambitclient.model.dto.ambit.AmbitTask;
import org.asynchttpclient.*;
import org.asynchttpclient.request.body.multipart.ByteArrayPart;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.asynchttpclient.request.body.multipart.Part;
import org.jaqpot.ambitclient.model.dto.ambit.AmbitTaskArray;
import org.jaqpot.ambitclient.serialize.Serializer;

/**
 * @author Angelos Valsamis
 * @author Charalampos Chomenidis
 */
public class DatasetResourceConsumer extends BaseConsumer {

    private final static String DATASET = "dataset";
    private final static String DATASET_BY_ID = "dataset/%s";
    private final static String STRUCTURES_BY_ID = "dataset/%s/structures";

    private final String basePath;
    private final String datasetPath;
    private final String datasetByIdPath;
    private final String structuresByIdPath;

    public DatasetResourceConsumer(Serializer serializer, AsyncHttpClient httpClient, String basePath) {
        super(httpClient, serializer);
        this.basePath = basePath;
        this.datasetPath = createPath(this.basePath, DATASET);
        this.datasetByIdPath = createPath(this.basePath, DATASET_BY_ID);
        this.structuresByIdPath = createPath(this.basePath, STRUCTURES_BY_ID);
    }

    public CompletableFuture<Dataset> getDatasetById(String datasetId) {
        String path = String.format(datasetByIdPath, datasetId);
        return get(path, Dataset.class);
    }

    public CompletableFuture<AmbitTask> createDatasetByPDB(byte[] file) {
        String fileName = UUID.randomUUID().toString() + ".pdb";
        ByteArrayPart part = new ByteArrayPart("file", file, "octet-stream", Charset.defaultCharset(), fileName);
        List<Part> bodyParts = new ArrayList<>();
        bodyParts.add(part);
        return postMultipart(datasetPath, bodyParts, AmbitTaskArray.class)
                .thenApply((ta) -> ta.getTask().get(0));
    }

    public CompletableFuture<Dataset> getStructuresByDatasetId(String datasetId) {
        String path = String.format(structuresByIdPath, datasetId);
        return get(path, Dataset.class);
    }

}
