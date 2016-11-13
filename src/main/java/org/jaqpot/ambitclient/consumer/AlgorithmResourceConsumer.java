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

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author Angelos Valsamis
 * @author Charalampos Chomenidis
 */
public class AlgorithmResourceConsumer extends BaseConsumer {

    private final String PATH = "https://apps.ideaconsult.net/enmtest/algorithm";

    private static final String ALGORITHM_BY_ID = "algorithm/%s";

    private final String basePath;
    private final String algorithmPath;

    public AlgorithmResourceConsumer(ObjectMapper mapper, AsyncHttpClient httpClient, String basePath) {
        super(httpClient, mapper);
        this.basePath = basePath;
        this.algorithmPath = createPath(this.basePath, ALGORITHM_BY_ID);
    }

    public CompletableFuture<AmbitTask> train(String algorithmId, Map<String, List<String>> parameters) {
        String path = String.format(algorithmPath, algorithmId);
        CompletableFuture<AmbitTaskArray> f = postForm(path, parameters, AmbitTaskArray.class);
        return f.thenApply((ta) -> ta.getTask().get(0));
    }
}
