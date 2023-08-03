package com.seacroak.plushables.util.networking;

import com.seacroak.plushables.block.tile.AnimatronicBlockEntity;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public class PlushablesNetworking {

  /* Call this method after sending packet when wanting to play sound */
  public static void playSoundOnClient(SoundEvent sound, World world, BlockPos pos, float volume, float pitch) {
    Vec3d vec = pos.toCenterPos();
    world.playSoundAtBlockCenter(BlockPos.ofFloored(vec), sound, SoundCategory.BLOCKS, volume, pitch, true);
  }

  /* Call this method after sending packet when wanting to spawn particles */
  public static void spawnParticlesOnClient(ParticleEffect particleType, World world, BlockPos pos, int particleCount, Vec3d offset, float spread) {
    Vec3d vec = pos.toCenterPos();
    Random rand = new Random();

    for (int i = 0; i < particleCount; i++) {
      if(spread == 0) world.addParticle(particleType, vec.x + offset.x, vec.y +offset.y, vec.z +offset.x,0,0,0);
      if(spread != 0) world.addParticle(particleType, vec.x + offset.x, vec.y +offset.y, vec.z +offset.x,
          rand.nextFloat(-spread, spread), rand.nextFloat(-spread, spread), rand.nextFloat(-spread, spread));
    }
  }

  public static void playAnimationOnClient(boolean shouldAnimate, World world, BlockPos pos, String animationName) {
    Vec3d vec = pos.toCenterPos();
    BlockEntity blockEntity = world.getBlockEntity(pos);
    if(blockEntity instanceof AnimatronicBlockEntity) {
      ((AnimatronicBlockEntity)blockEntity).animationName = animationName;
      ((AnimatronicBlockEntity)blockEntity).shouldAnimate = shouldAnimate;
    }
  }

  /* Receiver WITH Player Data*/
  public static void registerGlobalSoundPacketReceiverWithPlayer() {
    /* Registers global packet receiver in MainRegistry.class */
    ServerPlayNetworking.registerGlobalReceiver(SoundPacketHandler.PACKET_ID_PLAYER, ((server, player, handler, buf, responseSender) -> {
      var packet = SoundPacketHandler.PlayerSoundPacket.read(buf);
      if (packet.player == player.getUuid())
        return;
      server.execute(() -> {
        SoundPacketHandler.sendPlayerPacketToClients(player.getServerWorld(), packet);
      });
    }));
  }

  /* Receiver WITHOUT Player Data*/
  public static void registerGlobalSoundPacketReceiverWithoutPlayer() {
    /* Registers global packet receiver in MainRegistry.class */
    ServerPlayNetworking.registerGlobalReceiver(SoundPacketHandler.PACKET_ID_NO_PLAYER, ((server, player, handler, buf, responseSender) -> {
      var packet = SoundPacketHandler.NoPlayerSoundPacket.read(buf);
      server.execute(() -> {
        SoundPacketHandler.sendNoPlayerPacketToClients(player.getServerWorld(), packet);
      });
    }));
  }

  public static void registerGlobalParticlePacketReceiver() {
    /* Registers global packet receiver in MainRegistry.class */
    ServerPlayNetworking.registerGlobalReceiver(ParticlePacketHandler.PACKET_ID, ((server, player, handler, buf, responseSender) -> {
      var packet = ParticlePacketHandler.ParticlePacket.read(buf);
      if (packet.player == player.getUuid())
        return;
      server.execute(() -> {
        ParticlePacketHandler.sendPacketToClients(player.getServerWorld(), packet);
      });
    }));
  }

  public static void registerGlobalAnimationPacketReceiver() {
    /* Registers global packet receiver in MainRegistry.class */
    ServerPlayNetworking.registerGlobalReceiver(AnimationPacketHandler.PACKET_ID, ((server, player, handler, buf, responseSender) -> {
      var packet = AnimationPacketHandler.AnimationPacket.read(buf);
      if (packet.player == player.getUuid())
        return;
      server.execute(() -> {
        AnimationPacketHandler.sendPacketToClients(player.getServerWorld(), packet);
      });
    }));
  }
}