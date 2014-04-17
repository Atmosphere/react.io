/*
 * Copyright 2012-2014 Donghwan Kim
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.react.runtime;

import io.react.Action;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * {@code Socket} is a connectivity between the two react endpoints.
 * <p>
 * Do not hold a reference on {@code Socket} unless the reference shares the
 * same life cycle with it. It makes things complicated since it is stateful and
 * also may result in a problem in clustered environment. Always create a socket
 * action and pass it to {@link Server} to access {@code Socket}.
 * <p>
 * Sockets may be accessed by multiple threads.
 * 
 * @author Donghwan Kim
 */
public interface Socket extends AbstractSocket<Socket> {

    /**
     * A unique identifier in the form of UUID generated by client by default.
     */
    String id();

    /**
     * A URI used to connect. To work with URI parts, use {@link URI} or
     * something like that.
     */
    String uri();

    /**
     * A set of tag names. It's modifiable, deal with it as a plain set.
     */
    Set<String> tags();

    /**
     * Adds a given event handler for a given event.
     * <p>
     * The allowed types for {@code T} are Java types corresponding to JSON
     * types.
     * <table>
     * <thead>
     * <tr>
     * <th>JSON</th>
     * <th>Java</th>
     * </tr>
     * </thead> <tbody>
     * <tr>
     * <td>Number</td>
     * <td>{@link Integer} or {@link Double}</td>
     * </tr>
     * <tr>
     * <td>String</td>
     * <td>{@link String}</td>
     * </tr>
     * <tr>
     * <td>Boolean</td>
     * <td>{@link Boolean}</td>
     * </tr>
     * <tr>
     * <td>Array</td>
     * <td>{@link List}, {@code List<T>} in generic</td>
     * </tr>
     * <tr>
     * <td>Object</td>
     * <td>{@link Map}, {@code Map<String, T>} in generic</td>
     * </tr>
     * <tr>
     * <td>null</td>
     * <td>{@code null}, {@link Void} for convenience</td>
     * </tr>
     * </tbody>
     * </table>
     * 
     * If the counterpart sends an event with callback, {@code T} should be
     * {@link Reply}.
     */
    <T> Socket on(String event, Action<T> action);

    /**
     * Removes a given added event handler for a given event.
     */
    <T> Socket off(String event, Action<T> action);

    /**
     * Sends a given event with data attaching resolved callback.
     * <p>
     * For the allowed types for {@code T}, see
     * {@link Socket#on(String, Action)}.
     */
    <T> Socket send(String event, Object data, Action<T> resolved);

    /**
     * Sends a given event with data attaching resolved callback 
     * and rejected callback.
     * <p>
     * For the allowed types for {@code T}, see
     * {@link Socket#on(String, Action)}.
     */
    <T, U> Socket send(String event, Object data, Action<T> resolved, Action<U> rejected);

    /**
     * Interface to deal with reply.
     * <p>
     * For the allowed types for {@code T}, see {@link Socket#on(String, Action)}.
     * 
     * @author Donghwan Kim
     */
    interface Reply<T> {

        /**
         * The original data.
         */
        T data();

        /**
         * Resolves.
         */
        void resolve();

        /**
         * Resolves with the value.
         */
        void resolve(Object data);

        /**
         * Rejects.
         */
        void reject();

        /**
         * Rejects with the reason.
         */
        void reject(Object error);

    }

}
