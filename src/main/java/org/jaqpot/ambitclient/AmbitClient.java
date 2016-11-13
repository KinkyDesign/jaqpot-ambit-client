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
package org.jaqpot.ambitclient;

import java.io.Closeable;
import java.util.concurrent.CompletableFuture;
import org.jaqpot.ambitclient.model.dataset.Dataset;
import org.jaqpot.ambitclient.model.dto.bundle.BundleProperties;
import org.jaqpot.ambitclient.model.dto.bundle.BundleSubstances;
import org.jaqpot.ambitclient.model.dto.study.Studies;

/**
 * @author Angelos Valsamis
 */
public interface AmbitClient extends Closeable {

    CompletableFuture<Dataset> generateMopacDescriptors(String pdbFile);

    CompletableFuture<Dataset> getDataset(String datasetId);

    CompletableFuture<Dataset> getDatasetStructures(String datasetId);

    CompletableFuture<BundleSubstances> getBundleSubstances(String bundleId);

    CompletableFuture<BundleProperties> getBundleProperties(String bundleId);

    CompletableFuture<Studies> getSubstanceStudies(String substanceId);

}
