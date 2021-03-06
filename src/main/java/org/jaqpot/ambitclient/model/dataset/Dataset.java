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
package org.jaqpot.ambitclient.model.dataset;

import org.jaqpot.ambitclient.model.JaqpotEntity;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Pantelis Sopasakis
 * @author Charalampos Chomenidis
 *
 */
@XmlRootElement
public class Dataset extends JaqpotEntity {

    public enum DescriptorCategory {

        EXPERIMENTAL("Experimental data", "Nanomaterial properties derived from experiments"),
        IMAGE("ImageAnalysis descriptors", "Descriptors derived from analyzing substance images by the ImageAnalysis software."),
        GO("GO descriptors", "Descriptors derived by proteomics data."),
        MOPAC("MopacResource descriptors", "Descriptors derived by crystallographic data."),
        CDK("CDK descriptors", "Descriptors derived from cdk software."),
        PREDICTED("Predicted descriptors", "Descriptors derived from algorithm predictions.");

        private final String name;
        private final String description;

        private DescriptorCategory(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return this.name;
        }

        public String getDescription() {
            return this.description;
        }

    }

    private String datasetURI;

    private String byModel;

    private List<DataEntry> dataEntry;

    private Set<FeatureInfo> features;

    private Integer totalRows;
    private Integer totalColumns;

    private Set<DescriptorCategory> descriptors;

    public String getDatasetURI() {
        return datasetURI;
    }

    public void setDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
    }

    public String getByModel() {
        return byModel;
    }

    public void setByModel(String byModel) {
        this.byModel = byModel;
    }

    public List<DataEntry> getDataEntry() {
        return dataEntry;
    }

    public void setDataEntry(List<DataEntry> dataEntry) {
        this.dataEntry = dataEntry;
    }

    public Set<FeatureInfo> getFeatures() {
        return features;
    }

    public void setFeatures(Set<FeatureInfo> features) {
        this.features = features;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public Integer getTotalColumns() {
        return totalColumns;
    }

    public void setTotalColumns(Integer totalColumns) {
        this.totalColumns = totalColumns;
    }

    public Set<DescriptorCategory> getDescriptors() {
        return descriptors;
    }

    public void setDescriptors(Set<DescriptorCategory> descriptors) {
        this.descriptors = descriptors;
    }

    @Override
    public String toString() {
        return "Dataset{" + "datasetURI=" + datasetURI + ", dataEntry=" + dataEntry + '}';
    }

}
