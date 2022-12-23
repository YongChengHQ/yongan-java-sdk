/*
 * Copyright 2014-2020  [fisco-dev]
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 */
package tech.yongcheng.yongan.sdk.v3.transaction.pusher;

import java.util.concurrent.CompletableFuture;
import tech.yongcheng.yongan.sdk.v3.client.Client;
import tech.yongcheng.yongan.sdk.v3.client.protocol.request.Transaction;
import tech.yongcheng.yongan.sdk.v3.client.protocol.response.Call;
import tech.yongcheng.yongan.sdk.v3.model.TransactionReceipt;
import tech.yongcheng.yongan.sdk.v3.model.callback.TransactionCallback;

public class TransactionPusherService implements TransactionPusherInterface {

    private Client client;

    /**
     * create the TransactionPusherService
     *
     * @param client the client object responsible for send transaction
     */
    public TransactionPusherService(Client client) {
        super();
        this.client = client;
    }

    @Override
    public void pushOnly(String signedTransaction) {
        this.client.sendTransactionAsync(signedTransaction, false, null);
    }

    @Override
    public Call push(String from, String to, byte[] encodedFunction) {
        Transaction transaction = new Transaction(from, to, encodedFunction);
        return this.client.call(transaction);
    }

    @Override
    public TransactionReceipt push(String signedTransaction) {
        return this.client.sendTransaction(signedTransaction, false).getTransactionReceipt();
    }

    @Override
    public void pushAsync(String signedTransactionData, TransactionCallback callback) {
        this.client.sendTransactionAsync(signedTransactionData, false, callback);
    }

    @Override
    public CompletableFuture<TransactionReceipt> pushAsync(String signedTransaction) {
        return CompletableFuture.supplyAsync(() -> this.push(signedTransaction));
    }

    /** @return the client */
    public Client getClient() {
        return this.client;
    }

    /** @param client the client to set */
    public void setClient(Client client) {
        this.client = client;
    }
}
