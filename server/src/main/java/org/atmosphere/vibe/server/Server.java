/*
 * Copyright 2014 The Vibe Project
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
package org.atmosphere.vibe.server;

import org.atmosphere.vibe.platform.Action;
import org.atmosphere.vibe.platform.server.ServerHttpExchange;
import org.atmosphere.vibe.platform.server.ServerWebSocket;

/**
 * Interface used to interact with sockets.
 * <p>
 * {@code Server} consumes {@link ServerHttpExchange} and
 * {@link ServerWebSocket}, produces {@link ServerSocket} following the Vibe
 * protocol and manages their life cycles. {@code Server} API is used to receive
 * {@link ServerHttpExchange} and {@link ServerWebSocket} from the platform and
 * accept, find and handle {@link ServerSocket}.
 * <p>
 * Server may be accessed by multiple threads.
 * 
 * @author Donghwan Kim
 */
public interface Server {

    /**
     * Returns a sentence that every socket in this server have to follow.
     */
    Sentence all();

    /**
     * Executes the given action retrieving every socket in this server.
     */
    Server all(Action<ServerSocket> action);

    /**
     * Returns a sentence that the socket of the given id in this server have to
     * follow.
     */
    Sentence byId(String id);

    /**
     * Executes the given action retrieving the socket of the given id in this
     * server. The given action will be executed only once if socket is found
     * and won't be executed if not found.
     */
    Server byId(String id, Action<ServerSocket> action);

    /**
     * Returns a sentence that the socket tagged with the given tags in this
     * server have to follow.
     */
    Sentence byTag(String... names);

    /**
     * Executes the given action retrieving the socket tagged with the given tag
     * in this server. The given action will be executed multiple times per
     * socket if sockets are found and won't be executed if not found.
     */
    Server byTag(String name, Action<ServerSocket> action);

    /**
     * Executes the given action retrieving the socket tagged with the given
     * tags in this server. The given action will be executed multiple times per
     * socket if sockets are found and won't be executed if not found.
     */
    Server byTag(String[] names, Action<ServerSocket> action);

    /**
     * Registers an action to be called when the socket has been opened in this
     * server. It's allowed to add several actions at any time, so you don't
     * need to centralize all your code to one class.
     */
    Server socketAction(Action<ServerSocket> action);

    /**
     * An action to consume {@link ServerHttpExchange}. 
     */
    Action<ServerHttpExchange> httpAction();

    /**
     * An action to consume {@link ServerWebSocket}. 
     */
    Action<ServerWebSocket> wsAction();

}