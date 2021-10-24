package dev.ricecx.augmentedsmp.modules.supplydrops;

import dev.ricecx.augmentedsmp.modules.supplydrops.drops.MonsterSupplyDrop;
import dev.ricecx.augmentedsmp.modules.supplydrops.drops.RegularSupplyDrop;

public enum SupplyDrops {
    MONSTER(new MonsterSupplyDrop()),
    REGULAR(new RegularSupplyDrop());


    private SupplyDrop drop;
    SupplyDrops(SupplyDrop supplyDropClass) {
        this.drop = supplyDropClass;
    }

    public SupplyDrop getDrop() {
        return drop;
    }
}
