package com.seacroak.plushables.block;

import com.seacroak.plushables.block.tile.CluckyTileEntity;
import com.seacroak.plushables.block.tile.DragonTileEntity;
import com.seacroak.plushables.registry.SoundRegistry;
import com.seacroak.plushables.registry.TileRegistry;
import com.seacroak.plushables.util.networking.PlushablesNetworking;
import com.seacroak.plushables.util.networking.SoundPacketHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimationController;

import java.util.List;

public class DragonBlock extends AnimatronicPlushable {

  public DragonBlock() {
    super();
  }
  // Shift Right Click pickup code
  @Override
  public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
    super.onUse(state, world, pos, player, hand, hit);

    if (world instanceof ServerWorld serverWorld) {
      SoundPacketHandler.sendPacketToClients(serverWorld, new SoundPacketHandler.SoundPacket(player, pos, SoundRegistry.LIGHTFURY,1f));
      return ActionResult.SUCCESS;
    } else if (world.isClient) {
      BlockEntity blockEntity = world.getBlockEntity(pos);
      if (blockEntity instanceof DragonTileEntity) {
        DragonTileEntity dragonEntity = (DragonTileEntity) blockEntity;
        dragonEntity.shouldAnimate(true);
        if (dragonEntity.shouldAnimate()
            && dragonEntity.interactionController.getAnimationState() == AnimationController.State.STOPPED) {
          PlushablesNetworking.playSoundOnClient(SoundRegistry.LIGHTFURY, world, pos, 1f,1f);
        }
        return ActionResult.SUCCESS;
      }
    }
    return ActionResult.PASS;
  }

  public VoxelShape getShape() {
    VoxelShape shape = VoxelShapes.empty();
    shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3125, 0, 0.25, 0.6875, 0.5, 0.6875));

    return shape;
  }

  @Nullable
  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return TileRegistry.DRAGON_TILE.instantiate(pos, state);
  }
}