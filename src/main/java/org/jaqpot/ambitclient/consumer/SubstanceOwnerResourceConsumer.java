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

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.asynchttpclient.AsyncHttpClient;
import org.jaqpot.ambitclient.model.dataset.Substance;
import org.jaqpot.ambitclient.model.dto.bundle.BundleSubstances;
import org.jaqpot.ambitclient.serialize.Serializer;

/**
 * @author Angelos Valsamis
 * @author Charalampos Chomenidis
 */
public class SubstanceOwnerResourceConsumer extends BaseConsumer {

    private final static String SUBSTANCEOWNER = "substanceowner";
    private final static String SUBSTANCEOWNER_BY_ID = "substanceowner/%s";
    private final static String SUBSTANCEOWNER_DATASET_BY_ID = "substanceowner/%s/dataset";
    private final static String SUBSTANCEOWNER_SUBSTANCE_BY_ID = "substanceowner/%s/substance";
    private final static String SUBSTANCEOWNER_STRUCTURE_BY_ID = "substanceowner/%s/structure";

    private final String basePath;
    private final String ownerPath;
    private final String ownerByIdPath;
    private final String ownerDatasetByIdPath;
    private final String ownerSubstancesByIdPath;
    private final String ownerStructuresByIdPath;

    public SubstanceOwnerResourceConsumer(Serializer serializer, AsyncHttpClient httpClient, String basePath) {
        super(httpClient, serializer);
        this.basePath = basePath;
        this.ownerPath = createPath(this.basePath, SUBSTANCEOWNER);
        this.ownerByIdPath = createPath(this.basePath, SUBSTANCEOWNER_BY_ID);
        this.ownerDatasetByIdPath = createPath(this.basePath, SUBSTANCEOWNER_DATASET_BY_ID);
        this.ownerSubstancesByIdPath = createPath(this.basePath, SUBSTANCEOWNER_SUBSTANCE_BY_ID);
        this.ownerStructuresByIdPath = createPath(this.basePath, SUBSTANCEOWNER_STRUCTURE_BY_ID);
    }

    public CompletableFuture<List<String>> getOwnerSubstances(String ownerId) {
        String path = String.format(ownerSubstancesByIdPath, ownerId);
        return get(path, BundleSubstances.class)
                .thenApply((ta) -> {
                    if (ta.getSubstance() != null && !ta.getSubstance().isEmpty()) {
                        return ta.getSubstance()
                                .stream()
                                .map(Substance::getURI)
                                .collect(Collectors.toList());
                    }
                    return null;
                });
    }

}
