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
package org.jaqpot.ambitclient.consumer;

import org.jaqpot.ambitclient.model.dto.ambit.AmbitTask;
import org.jaqpot.ambitclient.model.dto.ambit.AmbitTaskArray;
import org.asynchttpclient.*;

import java.util.concurrent.CompletableFuture;
import org.jaqpot.ambitclient.exception.AmbitClientException;
import org.jaqpot.ambitclient.serialize.Serializer;

/**
 * @author Angelos Valsamis
 * @author Charalampos Chomenidis
 */
public class TaskResourceConsumer extends BaseConsumer {

    private static final long POLLING_INTERVAL_MILLIS = 500;

    private static final String TASK_BY_ID = "task/%s";

    private final String basePath;
    private final String taskByIdPath;

    public TaskResourceConsumer(Serializer serializer, AsyncHttpClient httpClient, String basePath) {
        super(httpClient, serializer);
        this.basePath = basePath;
        this.taskByIdPath = createPath(this.basePath, TASK_BY_ID);
    }

    public CompletableFuture<AmbitTask> getTask(String taskId, String subjectId) {
        String path = String.format(taskByIdPath, taskId);
        return get(path, subjectId, AmbitTaskArray.class)
                .thenApply((ta) -> ta.getTask().get(0));
    }

    public CompletableFuture<AmbitTask> waitTask(String taskId, long timeoutMillis, String subjectId) {
        CompletableFuture<AmbitTask> tf = getTask(taskId, subjectId);
        int iterations = (int) (timeoutMillis / POLLING_INTERVAL_MILLIS);
        for (int i = 0; i < iterations; i++) {
            tf = tf.thenCompose(task -> {
                try {
                    if (task.getStatus().equals("Running") || task.getStatus().equals("Queued")) {
                        Thread.sleep(POLLING_INTERVAL_MILLIS);
                        return getTask(taskId, subjectId);
                    }
                } catch (InterruptedException ex) {
                    return CompletableFuture.supplyAsync(() -> task);
                }
                return CompletableFuture.supplyAsync(() -> task);
            });
        }
        return tf.thenApply(task -> {
            if (task.getStatus().equals("Running") || task.getStatus().equals("Queued")) {
                throw new AmbitClientException("Timeout waiting for Ambit task:" + task.getId());
            }
            return task;
        });
    }
}
