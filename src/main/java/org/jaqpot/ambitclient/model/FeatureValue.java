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
package org.jaqpot.ambitclient.model;

//import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pantelis Sopasakis
 * @author Charalampos Chomenidis
 *
 */
//@XmlRootElement
public class FeatureValue extends JaqpotEntity {

    public FeatureValue() {
    }

    public FeatureValue(FeatureValue other) {
        super(other);
        this.bibtex = other.bibtex;
        this.createdBy = other.createdBy;
        this.feature = other.feature;
        this.highValue = other.highValue;
        this.lowValue = other.lowValue;
        this.stdError = other.stdError;
        this.stringValue = other.stringValue;
        this.value = other.value;
    }

    public FeatureValue(String id) {
        super(id);
    }

    /**
     * The corresponding feature.
     */
    private String feature;

    /**
     * stores numeric lower value value
     */
    private Double lowValue;

    /**
     * Stores numeric upper value.
     */
    private Double highValue;

    /**
     * Stores the standard error of the numeric value.
     */
    private Double stdError;

    /**
     * Stores string value.
     */
    private String stringValue;

    /**
     * Single value to be used for training.
     */
    private Object value;
    /**
     * Where in the literature the value was found.
     */
    private String bibtex;

    /**
     * ID of the user who created the feature value.
     */
    private String createdBy;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Double getLowValue() {
        return lowValue;
    }

    public void setLowValue(Double lowValue) {
        this.lowValue = lowValue;
    }

    public Double getHighValue() {
        return highValue;
    }

    public void setHighValue(Double highValue) {
        this.highValue = highValue;
    }

    public Double getStdError() {
        return stdError;
    }

    public void setStdError(Double stdError) {
        this.stdError = stdError;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getBibtex() {
        return bibtex;
    }

    public void setBibtex(String bibtex) {
        this.bibtex = bibtex;
    }

}
