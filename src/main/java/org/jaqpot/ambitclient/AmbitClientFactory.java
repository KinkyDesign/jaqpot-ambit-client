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

/**
 * Created by Angelos Valsamis on 11/10/2016.
 */

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.AsyncHttpClientConfig;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AmbitClientFactory {

    private static final Logger LOG = Logger.getLogger(AmbitClientFactory.class.getName());

    public AmbitClientFactory(){}

    private enum ClientFactory {

        INSTANCE;

        private DefaultAsyncHttpClient s;

        ClientFactory() {
            AsyncHttpClientConfig config = new DefaultAsyncHttpClientConfig.Builder()
                    .setPooledConnectionIdleTimeout(500)
                    .setMaxConnections(20000)
                    .setAcceptAnyCertificate(true)
                    .setMaxConnectionsPerHost(5000).build();

            s = new DefaultAsyncHttpClient(config);

        }
        public AsyncHttpClient getClient() {
            return s;
        }
    }

    @PreDestroy
    public void destroy() {
        try {
            getClient().close();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Could not successfully close AmbitClient", ex);
        }
    }

    public AsyncHttpClient getClient() {
        return ClientFactory.INSTANCE.getClient();
    }
}
