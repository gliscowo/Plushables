package com.seacroak.plushables.block;

import com.seacroak.plushables.registry.SoundRegistry;
import com.seacroak.plushables.util.networking.ParticlePacketHandler;
import com.seacroak.plushables.util.networking.PlushablesNetworking;
import com.seacroak.plushables.util.networking.SoundPacketHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;

public class WizardBlock extends SimplePlushable {

  public WizardBlock() {
    super();
  }

  @Override
  public VoxelShape getShape() {
    VoxelShape shape = VoxelShapes.empty();
    shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0, 0.25, 0.75, 0.640625, 0.625));
    shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0.9375, 0.15625, 0.625, 1, 0.5));
    shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3125, 0.625, 0.15625, 0.6875, 0.9375, 0.5625));
    shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.421875, 0.296875, 0.21875, 0.578125, 0.4375, 0.25));

    return shape;
  }

  //   Shift Right Click pickup code
  @Override
  public ActionResult onUse(BlockState state, World world, BlockPos pos,
                            PlayerEntity player, Hand hand, BlockHitResult hit) {
    // Injects superclass method
    super.onUse(state, world, pos, player, hand, hit);
    if (player.shouldCancelInteraction()) return ActionResult.PASS;
    if (!player.isSneaking()) {
      if (world instanceof ServerWorld serverWorld) {
        SoundPacketHandler.sendPlayerPacketToClients(serverWorld, new SoundPacketHandler.PlayerSoundPacket(player, pos, SoundRegistry.SWMG, 1f));
        ParticlePacketHandler.sendPacketToClients(serverWorld, new ParticlePacketHandler.ParticlePacket
            (player, pos, "minecraft:note", 1, new Vec3d(0, 0.5, 0), 0f));
        ParticlePacketHandler.sendPacketToClients(serverWorld, new ParticlePacketHandler.ParticlePacket
            (player, pos, "minecraft:glow", 5, new Vec3d(0, 0, 0), 0.05f));

        return ActionResult.CONSUME;

      } else {
        PlushablesNetworking.playSoundOnClient(SoundRegistry.SWMG, world, pos, 1f, 1f);
        PlushablesNetworking.spawnParticlesOnClient(ParticleTypes.NOTE, world, pos, 1, new Vec3d(0, 0.5, 0), 0);
        PlushablesNetworking.spawnParticlesOnClient(ParticleTypes.GLOW, world, pos, 5, new Vec3d(0, 0, 0), 0.05f);
        return ActionResult.SUCCESS;
      }
    }
    return ActionResult.PASS;
  }
}