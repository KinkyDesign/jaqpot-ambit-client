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

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import org.jaqpot.ambitclient.consumer.DatasetResourceConsumer;
import org.jaqpot.ambitclient.consumer.SubstanceResourceConsumer;
import org.jaqpot.ambitclient.consumer.BundleResourceConsumer;
import org.jaqpot.ambitclient.consumer.AlgorithmResourceConsumer;
import org.jaqpot.ambitclient.consumer.TaskResourceConsumer;
import org.jaqpot.ambitclient.model.dataset.Dataset;
import org.jaqpot.ambitclient.model.dto.ambit.AmbitTask;
import org.jaqpot.ambitclient.model.dto.bundle.BundleProperties;
import org.jaqpot.ambitclient.model.dto.bundle.BundleSubstances;
import org.jaqpot.ambitclient.model.dto.study.Studies;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Created by Angelos Valsamis on 20/10/2016.
 */
public class AmbitClientImpl implements AmbitClient {

    private static final Logger LOG = Logger.getLogger(AmbitClientImpl.class.getName());

    private final DatasetResourceConsumer datasetConsumer;
    private final TaskResourceConsumer taskConsumer;
    private final AlgorithmResourceConsumer algorithmConsumer;
    private final BundleResourceConsumer bundleConsumer;
    private final SubstanceResourceConsumer substanceConsumer;

    public AmbitClientImpl(DatasetResourceConsumer datasetConsumer, TaskResourceConsumer taskConsumer, AlgorithmResourceConsumer algorithmConsumer, BundleResourceConsumer bundleConsumer, SubstanceResourceConsumer substanceConsumer) {
        this.datasetConsumer = datasetConsumer;
        this.taskConsumer = taskConsumer;
        this.algorithmConsumer = algorithmConsumer;
        this.bundleConsumer = bundleConsumer;
        this.substanceConsumer = substanceConsumer;
    }

    @Override
    public Dataset createMopacDataset(String pdbFile, String options) {
        URL pdbURL = null;
        byte[] file = new byte[0];

        try {
            pdbURL = new URL(pdbFile);//"http://enanomapper.ntua.gr/pdbRepo/17002-ICSDNiO.pdb"
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            file = InputStreamToByteArray(pdbURL.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        AmbitTask result = datasetConsumer.createDatasetByPDB(file);

        while (result.getStatus().equals("Running") || result.getStatus().equals("Queued")) {
            result = taskConsumer.getTask(result.getId());
        }

        result = algorithmConsumer.mopacOriginalStructure(result.getResult(), options); //"PM3 NOINTER MMOK BONDS MULLIK GNORM=1.0 T=30.00M"

        while (result.getStatus().equals("Running") || result.getStatus().equals("Queued")) {
            result = taskConsumer.getTask(result.getId());
        }

        return datasetConsumer.getDatasetById(result.getResult().split("dataset/")[1]);

    }

    @Override
    public Dataset getStructuresByDatasetId(String datasetId) {
        return datasetConsumer.getStructuresByDatasetId(datasetId);
    }

    @Override
    public Dataset createDatasetByPDB(byte[] file) {
        AmbitTask result = datasetConsumer.createDatasetByPDB(file);

        while (result.getStatus().equals("Running") || result.getStatus().equals("Queued")) {
            result = taskConsumer.getTask(result.getId());
        }
        return datasetConsumer.getDatasetById(result.getResult().split("dataset/")[1]);
    }

    @Override
    public BundleSubstances getSubstances(String bundleId) {
        return bundleConsumer.getSubstancesByBundleId(bundleId);

    }

    @Override
    public Studies getStudiesBySubstanceId(String substanceId) {
        return substanceConsumer.getStudiesBySubstanceId(substanceId);
    }

    @Override
    public BundleProperties getPropertiesByBundleId(String bundleId) {
        return bundleConsumer.getPropertiesByBundleId(bundleId);
    }

    private byte[] InputStreamToByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[8192];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();

        return buffer.toByteArray();
    }
}
