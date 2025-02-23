package org.dementhium.model;



/**
 * Container class.
 *
 * @author Graham
 * @param <T>
 */
public class Container {

	private Item[] data;
	private boolean alwaysStackable = false;

	public Container(int size, boolean alwaysStackable) {
		data = new Item[size];
		this.alwaysStackable = alwaysStackable;
	}

	public int size() {
		int size = 0;
		for (int i = 0; i < data.length; i++) {
			if (data[i] != null) {
				size++;
			}
		}
		return size;
	}

	public void shift() {
		Item[] oldData = data;
		data = new Item[oldData.length];
		int ptr = 0;
		for (int i = 0; i < data.length; i++) {
			if (oldData[i] != null) {
				data[ptr++] = oldData[i];
			}
		}
	}

	private Item checkNull(Item item) {
		if (item == null) return null;
		if (item.getAmount() < 1) return null;
		return item.getId() < 0 ? null : item;
	}

	public Item get(int slot) {
		if (slot < 0 || slot >= data.length) {
			return null;
		}
		return data[slot];
	}

	public void set(int slot, Item item) {
		if (slot < 0 || slot >= data.length) {
			return;
		}
		data[slot] = checkNull(item);
	}

	public boolean forceAdd(Item item) {
		for (int i = 0; i < data.length; i++) {
			if (data[i] == null) {
				data[i] = item;
				return true;
			}
		}
		return false;
	}

	public boolean add(Item itemToAdd) {
		final Item item = checkNull(itemToAdd);
		if (item == null) return false;
		if (alwaysStackable || item.getDefinition().isStackable() || item.getDefinition().isNoted()) {
			for (int i = 0; i < data.length; i++) {
				if (data[i] != null) {
					if (data[i].getId() == item.getId()) {
						data[i] = new Item(data[i].getDefinition().getId(), data[i].getAmount() + item.getAmount());
						return true;
					}
				}
			}
		} else {
			if (item.getAmount() > 1) {
				if (freeSlots() >= item.getAmount()) {
					for (int i = 0; i < item.getAmount(); i++) {
						int index = freeSlot();
						data[index] = new Item(item.getId(), 1);
					}
					return true;
				} else {
					return false;
				}
			}
		}
		int index = freeSlot();
		if (index == -1) {
			return false;
		}
		data[index] = item;
		return true;
	}
	
	public void insert(Item item, int slot) {
		remove(item);
		if (item.getAmount() > 0) {
			for (int i = slot + 1; i < getTakenSlots(); i++) { //This is wrong.
				data[i] = data[i - 1];
			}
			data[slot] = item;
		}
	}

	public int freeSlots() {
		int j = 0;
		for (Item aData : data) {
			if (aData == null) {
				j++;
			}
		}
		return j;
	}

	public void remove(Item item) {
		int removed = 0, toRemove = item.getAmount();
		for (int i = 0; i < data.length; i++) {
			if (data[i] != null) {
				if (data[i].getId() == item.getId()) {
					int amt = data[i].getAmount();
					if (amt > toRemove) {
						removed += toRemove;
						amt -= toRemove;
						toRemove = 0;
						data[i] = new Item(data[i].getDefinition().getId(), amt);
						return;
					} else {
						removed += amt;
						toRemove -= amt;
						data[i] = null;
					}
				}
			}
		}
	}

	public void removeAll(Item item) {
		for (int i = 0; i < data.length; i++) {
			if (data[i] != null) {
				if (data[i].getId() == item.getId()) {
					data[i] = null;
				}
			}
		}
	}

	public void remove(Item item, int amount) {
		for (int i = 0; i < amount; i++) {
			if (data[i] != null) {
				if (data[i].getId() == item.getId()) {
					data[i] = null;
				}
			}
		}
	}
	
	public boolean containsOne(Item item) {
		for (Item aData : data) {
			if (aData != null) {
				if (aData.getId() == item.getId()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public int indexOf(Item item) {
		int i = 0;
		for (Item aData : data) {
			if (aData != null) {
				if (aData.getId() == item.getId()) {
					return i;
				}
			}
			i++;
		}
		return -1;
	}

	public boolean contains(Item item) {
		int amtOf = 0;
		for (Item aData : data) {
			if (aData != null) {
				if (aData.getId() == item.getId()) {
					amtOf += aData.getAmount();
				}
			}
		}
		return amtOf >= item.getAmount();
	}

	public int freeSlot() {
		for (int i = 0; i < data.length; i++) {
			if (data[i] == null) {
				return i;
			}
		}
		return -1;
	}

	public void clear() {
		for (int i = 0; i < data.length; i++) {
			data[i] = null;
		}
	}

	public int getSize() {
		return data.length;
	}

	public int getFreeSlots() {
		int s = 0;
		for (Item aData : data) {
			if (aData == null) {
				s++;
			}
		}
		return s;
	}
	
	public int getTakenSlots() {
		int s = 0;
		for (Item aData : data) {
			if (aData != null) {
				s++;
			}
		}
		return s;
	}

	public int getNumberOf(Item item) {
		int count = 0;
		for (Item aData : data) {
			if (aData != null) {
				if (aData.getId() == item.getId()) {
					count += aData.getAmount();
				}
			}
		}
		return count;
	}

	public int getNumberOff(int item) {
		int count = 0;
		for (Item aData : data) {
			if (aData != null) {
				if (aData.getId() == item) {
					count += aData.getAmount();
				}
			}
		}
		return count;
	}

	public Item[] getItems() {
		return data;
	}

	public int getFreeSlot() {
		for (int i = 0; i < data.length; i++) {
			if (data[i] == null) {
				return i;
			}
		}
		return 0;
	}

	public int getThisItemSlot(Item item) {
		for (int i = 0; i < data.length; i++) {
			if (data[i] != null) {
				if (data[i].getId() == item.getId()) {
					return i;
				}
			}
		}
		return getFreeSlot();
	}

	public Item lookup(int id) {
		for (Item aData : data) {
			if (aData == null) {
				continue;
			}
			if (aData.getId() == id) {
				return aData;
			}
		}
		return null;
	}

	public int lookupSlot(int id) {
		for (int i = 0; i < data.length; i++) {
			if (data[i] == null) {
				continue;
			}
			if (data[i].getId() == id) {
				return i;
			}
		}
		return -1;
	}

	public void reset() {
		data = new Item[data.length];
	}

	public void remove(int preferredSlot, Item item) {
		int removed = 0, toRemove = item.getAmount();
		if(data[preferredSlot] != null) {
			if(data[preferredSlot].getId() == item.getId()) {
				int amt = data[preferredSlot].getAmount();
				if(amt > toRemove) {
					removed += toRemove;
					amt -= toRemove;
					toRemove = 0;
					data[preferredSlot] = new Item(data[preferredSlot].getDefinition().getId(), amt);
					return;
				} else {
					removed += amt;
					toRemove -= amt;
					data[preferredSlot] = null;
				}
			}
		}
		for(int i = 0; i < data.length; i++) {
			if(data[i] != null) {
				if(data[i].getId() == item.getId()) {
					int amt = data[i].getAmount();
					if(amt > toRemove) {
						removed += toRemove;
						amt -= toRemove;
						toRemove = 0;
						data[i] = new Item(data[i].getDefinition().getId(), amt);
						return;
					} else {
						removed += amt;
						toRemove -= amt;
						data[i] = null;
					}
				}
			}
		}
	}

	public void addAll(Container container) {
		for (int i = 0; i < container.getSize(); i++) {
			Item item = container.get(i);
			if (item != null) {
				this.add(item);
			}
		}
	}

	public boolean hasSpaceFor(Container container) {
		for (int i = 0; i < container.getSize(); i++) {
			Item item = container.get(i);
			if (item != null) {
				if (!this.hasSpaceForItem(item)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean hasSpaceForItem(Item item) {
		if (alwaysStackable || item.getDefinition().isStackable() || item.getDefinition().isNoted()) {
			for (Item aData : data) {
				if (aData != null) {
					if (aData.getId() == item.getId()) {
						return true;
					}
				}
			}
		} else {
			if (item.getAmount() > 1) {
				return freeSlots() >= item.getAmount();
			}
		}
		int index = freeSlot();
		return index != -1;
	}

}
