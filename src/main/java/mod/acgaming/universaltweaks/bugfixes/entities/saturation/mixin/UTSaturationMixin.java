package mod.acgaming.universaltweaks.bugfixes.entities.saturation.mixin;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// MC-31819
// https://bugs.mojang.com/browse/MC-31819
// Courtesy of isXander
@Mixin(EntityPlayer.class)
public abstract class UTSaturationMixin extends EntityLivingBase
{
    public UTSaturationMixin(World worldIn)
    {
        super(worldIn);
    }

    @ModifyExpressionValue(method = "addExhaustion", at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;isRemote:Z"))
    public boolean utShouldNotAddExhaustion(boolean isRemote)
    {
        if (!UTConfigBugfixes.ENTITIES.utExhaustionToggle) return isRemote;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTSaturation ::: Exhaustion check");
        return isRemote || this.world.getDifficulty() == EnumDifficulty.PEACEFUL;
    }
}