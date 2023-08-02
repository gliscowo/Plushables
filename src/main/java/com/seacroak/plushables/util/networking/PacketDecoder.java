package com.seacroak.plushables.util.networking;

import com.seacroak.plushables.registry.SoundRegistry;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class PacketDecoder {
  public static SoundEvent decodeSoundEvent(String stringIdentifier) {
    SoundEvent returnValue;
    switch(stringIdentifier) {
      /* Custom Sounds */
      case "plushables:builder_ding": returnValue = SoundRegistry.BUILDER_DING; break;
      case "plushables:clucky_cluck": returnValue = SoundRegistry.CLUCKY_CLUCK; break;
      case "plushables:rupert_purr": returnValue = SoundRegistry.RUPERT_PURR; break;
      case "plushables:swmg": returnValue = SoundRegistry.SWMG; break;
      case "plushables:lightfury": returnValue = SoundRegistry.LIGHTFURY; break;

      /* MC Sounds */
      case "minecraft:block.moss.place": returnValue = SoundEvents.BLOCK_MOSS_PLACE; break;
      case "minecraft:block.wool.hit": returnValue = SoundEvents.BLOCK_WOOL_HIT; break;

      /* Default */
      case "plushables:plushable_pop":
      default: returnValue = SoundRegistry.PLUSHABLE_POP;
    }
    return returnValue;
  }

  public static ParticleEffect decodeParticle(String stringIdentifier) {
    ParticleEffect returnValue;
    switch(stringIdentifier) {
      case "minecraft:glow": returnValue = ParticleTypes.GLOW; break;
      case "minecraft:note": returnValue = ParticleTypes.NOTE; break;
      case "minecraft:poof": returnValue = ParticleTypes.POOF; break;

      default: returnValue = ParticleTypes.SCULK_SOUL;
    }

    return returnValue;
  }
}