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

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.jaqpot.ambitclient.model.BundleData;
import org.jaqpot.ambitclient.model.dataset.Dataset;
import org.jaqpot.ambitclient.model.dto.ambit.AmbitTask;
import org.jaqpot.ambitclient.model.dto.bundle.BundleProperties;
import org.jaqpot.ambitclient.model.dto.bundle.BundleSubstances;
import org.jaqpot.ambitclient.model.dto.study.Studies;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

/**
 * @author Angelos Valsamis
 * @author Charalampos Chomenidis
 */
public class AmbitClientImplIT {

    private static AmbitClient client;

    public AmbitClientImplIT() {
    }

    @BeforeClass
    public static void setUpClass() {
        client = AmbitClientFactory.createNewClient("https://apps.ideaconsult.net/enmtest");
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        client.close();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @org.junit.Test
    public void testGenerateMopacDescriptors() throws InterruptedException, ExecutionException {
        System.out.println("generateMopacDescriptors");
        String pdbFile = "http://enanomapper.ntua.gr/pdbRepo/17002-ICSDNiO.pdb";

        CompletableFuture<Dataset> result = client.generateMopacDescriptors(pdbFile);
        Dataset dataset = result.get();
        assertNotNull(dataset);
    }

    @org.junit.Test
    public void testGetDatasetById() throws InterruptedException, ExecutionException {
        System.out.println("getDatasetById");
        String datasetId = "1";
        CompletableFuture<Dataset> result = client.getDataset(datasetId);
        Dataset dataset = result.get();
        assertNotNull(dataset);
    }

    @org.junit.Test
    public void testGetStructuresByDatasetId() throws InterruptedException, ExecutionException {
        System.out.println("getDatasetStructuresById");
        String datasetId = "1";
        CompletableFuture<Dataset> result = client.getDatasetStructures(datasetId);
        Dataset dataset = result.get();
        assertNotNull(dataset);
    }

    @org.junit.Test
    public void testGetSubstances() throws InterruptedException, ExecutionException {
        System.out.println("getSubstances");
        String bundleId = "15";
        CompletableFuture<BundleSubstances> result = client.getBundleSubstances(bundleId);
        BundleSubstances subs = result.get();
        assertNotNull(subs);
    }

    @org.junit.Test
    public void testGetStudiesBySubstanceId() throws InterruptedException, ExecutionException {
        System.out.println("getStudiesBySubstanceId");
        String substanceId = "CNLB-e9b74719-ce6b-80c5-3371-48d12725db03";
        CompletableFuture<Studies> result = client.getSubstanceStudies(substanceId);
        Studies studies = result.get();
        assertNotNull(studies);
    }

    @org.junit.Test
    public void testGetPropertiesByBundleId() throws InterruptedException, ExecutionException {
        System.out.println("getBundleProperties");
        String bundleId = "15";
        CompletableFuture<BundleProperties> result = client.getBundleProperties(bundleId);
        BundleProperties props = result.get();
        assertNotNull(props);
    }


    @org.junit.Test
    public void testCreateBundle() throws InterruptedException, ExecutionException {
        System.out.println("createBundle");
        BundleData bundleData= new BundleData();
        String username ="guest";
        bundleData.setDescription("a bundle with protein corona data");
        bundleData.setSubstanceOwner("CNLB-00B0A42C-3392-81F3-89F7-1F097956F48A");
        HashMap<String,List<String>> props = new HashMap<>();
        props.put("P-CHEM", Arrays.asList("PC_GRANULOMETRY_SECTION"));
        bundleData.setProperties(props);
        bundleData.setSubstances(null);
       // bundleData.setSubstances(Arrays.asList("https://apps.ideaconsult.net/enmtest/substance/IUC4-efdb21bb-e79f-3286-a988-b6f6944d3734",
       //         "https://apps.ideaconsult.net/enmtest/substance/FCSV-0e1a05ec-6045-3419-89e5-6e48e1c62e3c"));

        CompletableFuture<String> result = client.createBundle(bundleData, username);
        String bundleData1 = result.get();

        System.out.println(bundleData1);
        assertNotNull(result);
    }

}
