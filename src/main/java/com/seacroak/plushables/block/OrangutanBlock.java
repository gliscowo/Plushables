package com.seacroak.plushables.block;

import com.seacroak.plushables.block.tile.OrangutanTileEntity;
import com.seacroak.plushables.networking.AnimationPacketHandler;
import com.seacroak.plushables.networking.PlushablesNetworking;
import com.seacroak.plushables.networking.SoundPacketHandler;
import com.seacroak.plushables.registry.assets.SoundRegistry;
import com.seacroak.plushables.registry.uncommon.TileRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimationController;

public class OrangutanBlock extends BasePoweredPlushable {

  public OrangutanBlock() {
    super();
  }

  // Shift Right Click pickup code
  @Override
  public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
    if (!player.isSneaking()) {
      float randomPitch = 0.7f + randPitch.nextFloat() / 2;
      BlockEntity blockEntity = world.getBlockEntity(pos);

      if (world instanceof ServerWorld serverWorld) {
        if (!(blockEntity instanceof OrangutanTileEntity)) return ActionResult.CONSUME;
        /* Server: Send sound & animation packets to clients*/
        SoundPacketHandler.sendPlayerPacketToClients(serverWorld, new SoundPacketHandler.PlayerSoundPacket(player, pos, SoundRegistry.ORANGUTAN, randomPitch));
        AnimationPacketHandler.sendPacketToClients(serverWorld, new AnimationPacketHandler.AnimationPacket(player, pos, true, "interaction"));
        return ActionResult.CONSUME;

      } else if (world.isClient) {
        if (!(blockEntity instanceof OrangutanTileEntity)) return ActionResult.CONSUME;
        OrangutanTileEntity orangutanEntity = (OrangutanTileEntity) blockEntity;
        /* Client: Play animation */
        PlushablesNetworking.playAnimationOnClient(true, world, pos, "interaction");
        if (orangutanEntity.shouldAnimate()
            && orangutanEntity.interactionController.getAnimationState() == AnimationController.State.STOPPED) {
          PlushablesNetworking.playSoundOnClient(SoundRegistry.ORANGUTAN, world, pos, 1f, randomPitch);
        }
        return ActionResult.SUCCESS;
      }
    }
    return super.onUse(state, world, pos, player, hand, hit);
  }

  public VoxelShape getShape() {
    VoxelShape shape = VoxelShapes.empty();
    shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0, 0.3125, 0.3125, 0.375, 0.5));
    shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.6875, 0, 0.3125, 0.875, 0.375, 0.5));
    shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0, 0.75, 0.3125, 0.375, 0.9375));
    shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.6875, 0, 0.75, 0.875, 0.375, 0.9375));
    shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0.203125, 0.3125, 0.875, 0.4375, 0.5));
    shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0.203125, 0.5, 0.875, 0.375, 0.9375));

    return shape;
  }

  @Nullable
  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return TileRegistry.ORANGUTAN_TILE.instantiate(pos, state);
  }
}