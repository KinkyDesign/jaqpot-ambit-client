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

import com.github.jsonldjava.utils.JsonUtils;
import resource_consumers.*;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Angelos Valsamis on 6/10/2016.
 */
class tester {
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException {
        System.out.println("Hello word");
        byte[] file = new byte[0];

        org.apache.log4j.BasicConfigurator.configure();

        DatasetResourceConsumer datasetResourceConsumer = new DatasetResourceConsumer();
        TaskResourceConsumer taskResourceConsumer = new TaskResourceConsumer();
        AlgorithmResourceConsumer algorithmResourceConsumer = new AlgorithmResourceConsumer();
        BundleResourceConsumer bundleResourceConsumer = new BundleResourceConsumer();
        SubstanceResourceConsumer substanceResourceConsumer = new SubstanceResourceConsumer();
/*
        URL pdbURL = null;
        try {
            pdbURL = new URL("http://enanomapper.ntua.gr/pdbRepo/17002-ICSDNiO.pdb");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            file = IOUtils.toByteArray(pdbURL.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        AmbitTask result = datasetResourceConsumer.createDatasetByPDB(file);

        while (result.getStatus().equals("Running") || result.getStatus().equals("Queued")) {
            result = taskResourceConsumer.getTask(result.getId());
        }

        result = algorithmResourceConsumer.mopacOriginalStructure(result.getResult(), "PM3 NOINTER MMOK BONDS MULLIK GNORM=1.0 T=30.00M");

        while (result.getStatus().equals("Running") || result.getStatus().equals("Queued")) {
            result = taskResourceConsumer.getTask(result.getId());
        }

        Dataset dataset = datasetResourceConsumer.getDatasetById(result.getResult().split("dataset/")[1]);

        BundleProperties bundleProperties = bundleResourceConsumer.getProperties("1");
        BundleSubstances bundleSubstances = bundleResourceConsumer.getSubstances("1");

        for (Substance substance : bundleSubstances.getSubstance()) {
            Studies studies = substanceResourceConsumer.getStudiesBySubstanceId(substance.getURI().split("substance/")[1]);
            System.out.println(studies.getStudy().toString());
        }

        Dataset datset = datasetResourceConsumer.getStructuresByDatasetId("1");
        System.out.println(datset.toString());

        //   BundleSubstances bundleSubstances = bundleResourceConsumer.getSubstances("1");
*/
        Object jsonLd = bundleResourceConsumer.getBundleByJsonLD("1");
        try {
            System.out.println(JsonUtils.toPrettyString(jsonLd));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

