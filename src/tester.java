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

import model.dataset.Dataset;
import model.dataset.Substance;
import model.dto.ambit.AmbitTask;
import model.dto.bundle.BundleProperties;
import model.dto.bundle.BundleSubstances;
import model.dto.study.Studies;
import org.apache.commons.io.IOUtils;
import resource.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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

        DatasetResource datasetResource = new DatasetResource();
        TaskResource taskResource = new TaskResource();
        AlgorithmResource algorithmResource = new AlgorithmResource();
        BundleResource bundleResource = new BundleResource();
        SubstanceResource substanceResource = new SubstanceResource();

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

        AmbitTask result = datasetResource.createDatasetByPDB(file);

        while (result.getStatus().equals("Running") || result.getStatus().equals("Queued")) {
            result=taskResource.getTask(result.getId());
        }

        result = algorithmResource.mopacOriginalStructure(result.getResult(),"PM3 NOINTER MMOK BONDS MULLIK GNORM=1.0 T=30.00M");

        while (result.getStatus().equals("Running") || result.getStatus().equals("Queued")) {
            result=taskResource.getTask(result.getId());
        }

        Dataset dataset = datasetResource.getDatasetById(result.getResult().split("dataset/")[1]);

        BundleProperties  bundleProperties = bundleResource.getProperties("1");
        BundleSubstances bundleSubstances = bundleResource.getSubstances("1");

        for (Substance substance : bundleSubstances.getSubstance()) {
            Studies studies = substanceResource.getStudiesBySubstanceId(substance.getURI().split("substance/")[1]);
            System.out.println(studies.getStudy().toString());

        }
    }
}
