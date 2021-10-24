package dev.ricecx.augmentedsmp.monsters;

import dev.ricecx.augmentedsmp.core.entities.EntityAttributable;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeMapBase;
import net.minecraft.world.entity.ai.attributes.AttributeModifiable;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.EntityChicken;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;


@SuppressWarnings("all")
public class AngryChicken extends EntityChicken implements EntityAttributable {

    private static Field attributeField;

    static {
        try {
            attributeField = AttributeMapBase.class.getDeclaredField("b");
            attributeField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void registerGenericAttribute(org.bukkit.entity.Entity entity, Attribute attribute) throws IllegalAccessException {
        AttributeMapBase attributeMapBase = ((org.bukkit.craftbukkit.v1_17_R1.entity.CraftLivingEntity)entity).getHandle().getAttributeMap();
        Map<AttributeBase, AttributeModifiable> map = (Map<AttributeBase, AttributeModifiable>) attributeField.get(attributeMapBase);
        AttributeBase attributeBase = org.bukkit.craftbukkit.v1_17_R1.attribute.CraftAttributeMap.toMinecraft(attribute);
        AttributeModifiable attributeModifiable = new AttributeModifiable(attributeBase, AttributeModifiable::getAttribute);
        map.put(attributeBase, attributeModifiable);
    }

    @Override
    public void initPathfinder() { // This method will apply some custom pathfinders to our pig

        // These are the two attributes we need. We can actually add any attribute we want like this.
        //  current attributes            add an attribute       the attribute to add            |lambda|        attribute value(acts very weird)
        this.getAttributeMap().b().add(new AttributeModifiable(GenericAttributes.f, (a) -> a.setValue(1.0)));
        this.getAttributeMap().b().add(new AttributeModifiable(GenericAttributes.b, (a) -> a.setValue(1.0)));
        // Adds attack goal to pig
        this.bP.a(0, new PathfinderGoalMeleeAttack(this, 1.0D, false));

        this.bP.a(0, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
        this.bP.a(0, new PathfinderGoalFloat(this));
        this.bP.a(2, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));

    }


    public AngryChicken(@NotNull Location loc) {
        super(EntityTypes.l, ((CraftWorld) Objects.requireNonNull(loc.getWorld())).getHandle()); // Super the EntityPig Class

        try {
            registerGenericAttribute(this.getBukkitEntity(), Attribute.GENERIC_ATTACK_DAMAGE);
            registerGenericAttribute(this.getBukkitEntity(), Attribute.GENERIC_FOLLOW_RANGE);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.setPosition(loc.getX(), loc.getY(), loc.getZ()); // Sets the location of the CustomPig when we spawn it
    }

    public AngryChicken(EntityTypes<? extends EntityChicken> type, World world) {
        super(type, world);

        try {
            registerGenericAttribute(this.getBukkitEntity(), Attribute.GENERIC_ATTACK_DAMAGE);
            registerGenericAttribute(this.getBukkitEntity(), Attribute.GENERIC_FOLLOW_RANGE);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
