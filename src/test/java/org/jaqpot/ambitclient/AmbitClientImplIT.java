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

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jaqpot.ambitclient.model.BundleData;
import org.jaqpot.ambitclient.model.dataset.Dataset;
import org.jaqpot.ambitclient.model.dto.bundle.BundleProperties;
import org.jaqpot.ambitclient.model.dto.bundle.BundleSubstances;
import org.jaqpot.ambitclient.model.dto.study.Studies;
import org.jaqpot.ambitclient.model.dto.study.Substance;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertNotNull;

/**
 * @author Angelos Valsamis
 * @author Charalampos Chomenidis
 */
public class AmbitClientImplIT {

    private static AmbitClient client;

    private final String subjectId = "";

    public AmbitClientImplIT() {
    }

    @BeforeClass
    public static void setUpClass() {
        client = AmbitClientFactory.createNewClient("https://apps.ideaconsult.net/enmtest", new JacksonSerializer(new ObjectMapper()));

        //client = AmbitClientFactory.createNewClient("https://data.enanomapper.net", new JacksonSerializer(new ObjectMapper()));
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

        CompletableFuture<Dataset> result = client.generateMopacDescriptors(pdbFile, subjectId);
        Dataset dataset = result.get();
        assertNotNull(dataset);
    }

    @org.junit.Test
    public void testGetDatasetById() throws InterruptedException, ExecutionException {
        System.out.println("getDatasetById");
        String datasetId = "1";
        CompletableFuture<Dataset> result = client.getDataset(datasetId, subjectId);
        Dataset dataset = result.get();
        assertNotNull(dataset);
    }

    @org.junit.Test
    public void testGetStructuresByDatasetId() throws InterruptedException, ExecutionException {
        System.out.println("getDatasetStructuresById");
        String datasetId = "1";
        CompletableFuture<Dataset> result = client.getDatasetStructures(datasetId, subjectId);
        Dataset dataset = result.get();
        assertNotNull(dataset);
    }

    @org.junit.Test
    public void testGetSubstances() throws InterruptedException, ExecutionException {
        System.out.println("getSubstances");
        String bundleId = "1";
        CompletableFuture<BundleSubstances> result = client.getBundleSubstances(bundleId, subjectId);
        BundleSubstances subs = result.get();
        assertNotNull(subs);
    }

    @org.junit.Test
    public void testGetStudiesBySubstanceId() throws InterruptedException, ExecutionException, JsonMappingException {
        System.out.println("getStudiesBySubstanceId");
        String substanceId = "XLSX-7011cea0-1011-3f8b-9e8a-b3289fed836a";
        CompletableFuture<Studies> result = client.getSubstanceStudies(substanceId, subjectId);
        Studies studies = result.get();
        assertNotNull(studies);
    }

    @org.junit.Test
    public void testGetPropertiesByBundleId() throws InterruptedException, ExecutionException {
        System.out.println("getBundleProperties");
        String bundleId = "15";
        CompletableFuture<BundleProperties> result = client.getBundleProperties(bundleId, subjectId);
        BundleProperties props = result.get();
        assertNotNull(props);
    }

    @org.junit.Test
    public void testCreateBundle() throws InterruptedException, ExecutionException {
        System.out.println("createBundle");
        BundleData bundleData = new BundleData();
        String username = "guest";
        bundleData.setDescription("a bundle with protein corona data");
            bundleData.setSubstanceOwner("NWKI-9F4E86D0-C85D-3E83-8249-A856659087DA");
        HashMap<String, List<String>> props = new HashMap<>();
        props.put("P-CHEM", Arrays.asList("PC_GRANULOMETRY_SECTION"));
        bundleData.setProperties(props);
        bundleData.setSubstances(null);
        CompletableFuture<List<org.jaqpot.ambitclient.model.dataset.Substance>> result = client.getSubstancesBySubstanceOwner("NWKI-9F4E86D0-C85D-3E83-8249-A856659087DA", subjectId);
        List<org.jaqpot.ambitclient.model.dataset.Substance> resultS = result.get();
        System.out.println(resultS);
        assertNotNull(resultS);
    }
}
