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
import java.util.Objects;
import java.util.Set;
//import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author Pantelis Sopasakis
 * @author Charalampos Chomenidis
 *
 */
//@XmlRootElement
public abstract class JaqpotEntity {

    /**
     * Identifier of the entity.
     */
    private String id;
    /**
     * Meta data of the entity.
     */
    private MetaInfo meta;
    /**
     * Set of ontological characterizations.
     */
    private Set<String> ontologicalClasses;

    private Boolean visible;

    private Boolean temporary;

    private Boolean featured;

    public JaqpotEntity() {
    }

    public JaqpotEntity(String id) {
        this.id = id;
    }

    public JaqpotEntity(JaqpotEntity other) {
        if (other == null) {
            throw new NullPointerException("Cannot copy null object");
        }
        this.id = other.id;
        this.meta = other.meta != null ? new MetaInfo(other.meta) : null;
        this.ontologicalClasses = other.ontologicalClasses != null ? new HashSet<>(other.ontologicalClasses) : null;
    }

//    @XmlAttribute(name = "_id")
    public String getId() {
        return id;
    }

//    @XmlAttribute(name = "_id")
    public void setId(String id) {
        this.id = id;
    }

    public MetaInfo getMeta() {
        return meta;
    }

    public void setMeta(MetaInfo meta) {
        this.meta = meta;
    }

    public Set<String> getOntologicalClasses() {
        return ontologicalClasses;
    }

    public void setOntologicalClasses(Set<String> ontologicalClasses) {
        this.ontologicalClasses = ontologicalClasses;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getTemporary() {
        return temporary;
    }

    public void setTemporary(Boolean temporary) {
        this.temporary = temporary;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JaqpotEntity other = (JaqpotEntity) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
