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
package org.jaqpot.ambitclient.model.dto.ambit;

/**
 *
 * @author Charalampos Chomenidis
 * @author Pantelis Sopasakis
 */
public class AmbitTask {

    private Long completed;
    private String errorCause;
    private String error;
    private String id;
    private String name;
    private String policyError;
    private String result;
    private Long started;
    private String status;
    private String uri;
    private String user;

    public Long getCompleted() {
        return completed;
    }

    public void setCompleted(Long completed) {
        this.completed = completed;
    }

    public Long getStarted() {
        return started;
    }

    public void setStarted(Long started) {
        this.started = started;
    }

    public String getErrorCause() {
        return errorCause;
    }

    public void setErrorCause(String errorCause) {
        this.errorCause = errorCause;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPolicyError() {
        return policyError;
    }

    public void setPolicyError(String policyError) {
        this.policyError = policyError;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AmbitTask{" +
                "completed=" + completed +
                ",\nerrorCause='" + errorCause + '\'' +
                ",\nerror='" + error + '\'' +
                ",\nid='" + id + '\'' +
                ",\nname='" + name + '\'' +
                ",\npolicyError='" + policyError + '\'' +
                ",\nresult='" + result + '\'' +
                ",\nstarted=" + started +
                ",\nstatus='" + status + '\'' +
                ",\nuri='" + uri + '\'' +
                ",\nuser='" + user + '\'' +
                '}';
    }
}
