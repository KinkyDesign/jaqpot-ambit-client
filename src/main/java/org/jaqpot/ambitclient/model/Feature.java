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
import java.util.HashSet;
import java.util.Set;

/**
 * Feature: The definition of a property, either measured, predicted or computed
 * for a substance.
 *
 * @author Pantelis Sopasakis
 * @author Charalampos Chomenidis
 *
 */
//@XmlRootElement
public class Feature extends JaqpotEntity {

    /**
     * Units of measurement.
     */
    private String units;
    /**
     * In case the feature is a prediction feature, this field is used to refer
     * to the original feature that is predicted. This field will point to a
     * URI.
     */
    private String predictorFor;

    /**
     * In case the feature is nominal, this field stores it admissible values.
     * Whether the field is Nominal or Numeric or String is determined by its
     * ontological classes which can be retrieved from its superclass,
     * {@link JaqpotEntity}.
     */
    private Set<String> admissibleValues;

    public Feature() {
    }

    public Feature(String id) {
        super(id);
    }

    public Feature(Feature other) {
        super(other);
        this.admissibleValues = other.admissibleValues != null ? new HashSet<>(other.admissibleValues) : null;
        this.units = other.units;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public void setAdmissibleValues(Set<String> admissibleValues) {
        this.admissibleValues = admissibleValues;
    }

    public Set<String> getAdmissibleValues() {
        return admissibleValues;
    }

    public String getPredictorFor() {
        return predictorFor;
    }

    public void setPredictorFor(String predictorFor) {
        this.predictorFor = predictorFor;
    }

}
