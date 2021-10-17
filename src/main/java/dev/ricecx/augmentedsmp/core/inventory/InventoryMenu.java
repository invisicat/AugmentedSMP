package dev.ricecx.augmentedsmp.core.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Consumer;

public class InventoryMenu implements InventoryHolder {


    /* Metadata */
    private final String title;
    private final int rows;
    private final String tag;

    /* Inventory Slot */
    private final Map<Integer, InventoryButton> items;

    /* Consumers */
    private Consumer<InventoryMenu> onClose;
    private Consumer<InventoryMenu> onPageChange;

    /* State */
    private int currentPage;

    public InventoryMenu(String title, int rowsPerPage, String tag) {
        this.title = title;
        this.rows = rowsPerPage;
        this.tag = tag;

        this.items = new HashMap<>();
    }

    public void addButton(InventoryButton button) {
        // If slot 0 is empty but it's the 'highest filled slot', then set slot 0 to contain button.
        // (This is an edge case for when the whole inventory is empty).
        if (getHighestFilledSlot() == 0 && getButton(0) == null) {
            setButton(0, button);
            return;
        }

        // Otherwise, add one to the highest filled slot, then use that slot for the new button.
        setButton(getHighestFilledSlot() + 1, button);
    }


    public void addButtonsToNextAvailable(InventoryButton button, int[] slotsToFill) {
        for (int i : slotsToFill) {
            if(getButton(i) == null) {
                setButton(i, button);
                break;
            }
        }
    }
    public void addButtons(InventoryButton... buttons) {
        for (InventoryButton button : buttons) addButton(button);
    }

    public void setButton(int slot, InventoryButton button) {
        items.put(slot, button);
    }

    public void setButton(int page, int slot, InventoryButton button) {
        if (slot < 0 || slot > getPageSize())
            return;

        setButton((page * getPageSize()) + slot, button);
    }

    public void removeButton(int slot) {
        items.remove(slot);
    }

    public InventoryButton getButton(int slot) {
        if (slot < 0 || slot > getHighestFilledSlot())
            return null;

        return items.get(slot);
    }

    public InventoryButton getButton(int page, int slot) {
        if (slot < 0 || slot > getPageSize())
            return null;

        return getButton((page * getPageSize()) + slot);
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, getPageSize(), title.replace("{currentPage}", String.valueOf(currentPage + 1))
                .replace("{maxPage}", String.valueOf(getMaxPage())));

        for (int key = currentPage * getPageSize(); key < (currentPage + 1) * getPageSize(); key++) {
            // If we've already reached the maximum assigned slot, stop assigning slots.
            if (key > getHighestFilledSlot()) break;

            if (items.containsKey(key)) {
                inventory.setItem(key - (currentPage * getPageSize()), items.get(key).getIcon());
            }
        }

        return inventory;
    }

    public void refreshInventory(HumanEntity viewer) {
        if (!(viewer.getOpenInventory().getTopInventory().getHolder() instanceof InventoryMenu) || viewer.getOpenInventory().getTopInventory().getHolder() != this) return;
        // If the new size is different, we'll need to open a new inventory.
        if (viewer.getOpenInventory().getTopInventory().getSize() != getPageSize() + (getMaxPage() > 0 ? 9 : 0)) {
            viewer.openInventory(getInventory());
            return;
        }

        // If the name has changed, we'll need to open a new inventory.
        String newName = title.replace("{currentPage}", String.valueOf(currentPage + 1))
                .replace("{maxPage}", String.valueOf(getMaxPage()));
        if (!viewer.getOpenInventory().getTitle().equals(newName)) {
            viewer.openInventory(getInventory());
            return;
        }

        // Otherwise, we can refresh the contents without re-opening the inventory.
        viewer.getOpenInventory().getTopInventory().setContents(getInventory().getContents());
    }

    public int getPageSize() {
        return rows * 9;
    }

    public String getTitle() {
        return title;
    }

    public int getRows() {
        return rows;
    }

    public String getTag() {
        return tag;
    }

    /**
     * Gets the page number of the final page of the GUI.
     *
     * @return The highest page number that can be viewed.
     */
    public int getMaxPage() {
        return (int) Math.ceil(((double) getHighestFilledSlot() + 1) / ((double) getPageSize()));
    }

    /**
     * Returns the slot number of the highest filled slot.
     * This is mainly used to calculate the number of pages there needs to be to
     * display the GUI's contents in the rendered inventory.
     *
     * @return The highest filled slot's number.
     */
    public int getHighestFilledSlot() {
        int slot = 0;

        for (int nextSlot : items.keySet()) {
            if (items.get(nextSlot) != null && nextSlot > slot)
                slot = nextSlot;
        }

        return slot;
    }


    public Map<Integer, InventoryButton> getItems() {
        return items;
    }

    public Consumer<InventoryMenu> getOnClose() {
        return onClose;
    }

    public Consumer<InventoryMenu> getOnPageChange() {
        return onPageChange;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}
