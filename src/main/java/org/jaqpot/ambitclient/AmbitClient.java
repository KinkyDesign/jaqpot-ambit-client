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

import org.jaqpot.ambitclient.consumer.DatasetResourceConsumer;
import org.jaqpot.ambitclient.consumer.SubstanceResourceConsumer;
import org.jaqpot.ambitclient.consumer.BundleResourceConsumer;
import org.jaqpot.ambitclient.consumer.AlgorithmResourceConsumer;
import org.jaqpot.ambitclient.consumer.TaskResourceConsumer;
import org.jaqpot.ambitclient.model.dataset.Dataset;
import org.jaqpot.ambitclient.model.dto.bundle.BundleProperties;
import org.jaqpot.ambitclient.model.dto.bundle.BundleSubstances;
import org.jaqpot.ambitclient.model.dto.study.Studies;

/**
 * Created by Angelos Valsamis on 20/10/2016.
 */
public interface AmbitClient {

    Dataset createMopacDataset(DatasetResourceConsumer datasetResourceConsumer, TaskResourceConsumer taskResourceConsumer, AlgorithmResourceConsumer algorithmResourceConsumer, String pdbFile, String options);

    Dataset getStructuresByDatasetId(DatasetResourceConsumer datasetResourceConsumer, String datasetId);

    Dataset createDatasetByPDB(DatasetResourceConsumer datasetResourceConsumer, TaskResourceConsumer taskResourceConsumer, byte[] file);

    BundleSubstances getSubstances(BundleResourceConsumer bundleResourceConsumer , String bundleId);

    BundleProperties getPropertiesByBundleId (BundleResourceConsumer bundleResourceConsumer, String bundleId);

    Studies getStudiesBySubstanceId(SubstanceResourceConsumer substanceResourceConsumer, String substanceId);



}
