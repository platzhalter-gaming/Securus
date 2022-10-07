// Copyright (c) 2021, Tau <nullvoxel@gmail.com>
// All rights reserved.
// 
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
// 
// 1. Redistributions of source code must retain the above copyright notice, this
//    list of conditions and the following disclaimer.
// 
// 2. Redistributions in binary form must reproduce the above copyright notice,
//    this list of conditions and the following disclaimer in the documentation
//    and/or other materials provided with the distribution.
// 
// 3. Redistributions in source and/or binary forms must be free of all charges
//    and fees to the recipient of the redistribution unless It is clearly 
//    disclosed to the recipient of the redistribution prior to payment that it
//    includes this software or portions of it and by providing a means to 
//    obtain this software free of charge.
// 
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
// FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
// DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
// SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
// CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
// OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

package net.platzhaltergaming.securus.bungee.commandblocker.packets;

import java.util.Iterator;

import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.RootCommandNode;

import dev.simplix.protocolize.api.Direction;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.listener.AbstractPacketListener;
import dev.simplix.protocolize.api.listener.PacketReceiveEvent;
import dev.simplix.protocolize.api.listener.PacketSendEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.protocol.packet.Commands;
import net.platzhaltergaming.securus.bungee.commandblocker.PlayerWrapper;
import net.platzhaltergaming.securus.common.commandblocker.CommandChecker;

public class DeclareCommandsListener extends AbstractPacketListener<Commands> {

    private final CommandChecker commandChecker;

    public DeclareCommandsListener(CommandChecker commandChecker) {
        super(Commands.class, Direction.UPSTREAM, 0);
        this.commandChecker = commandChecker;
    }

    @Override
    public void packetSend(PacketSendEvent<Commands> e) {
        RootCommandNode<?> root = e.packet().getRoot();

        PlayerWrapper sender = new PlayerWrapper((ProxiedPlayer) e.player().handle());

        if (root != null) {
            if (!isBase(root) && filterNode(sender, root)) {
                e.cancelled(true);
            } else {
                filterNodes(sender, root);
            }
        }
    }

    @Override
    public void packetReceive(PacketReceiveEvent<Commands> paramPacketReceiveEvent) {
    }

    private void filterNodes(PlayerWrapper sender, CommandNode<?> node) {
        Iterator<? extends CommandNode<?>> it = node.getChildren().iterator();
        while (it.hasNext()) {
            if (filterNode(sender, it.next())) {
                it.remove();
            }
        }
    }

    private boolean filterNode(PlayerWrapper sender, CommandNode<?> node) {
        return commandChecker.checkCommand(sender, node.getName(), true);
    }

    private boolean isBase(CommandNode<?> node) {
        return node.getName().isEmpty();
    }

    public void register() {
        Protocolize.listenerProvider().registerListener(this);
    }

    public void unregister() {
        try { // for some fucking reason protocolize throws an exception if the listener isn't
              // registered... while providing no method to check if the listener is
              // registered.
            Protocolize.listenerProvider().unregisterListener(this);
        } catch (IllegalArgumentException e) {
        }
    }

}
