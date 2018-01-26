package com.daishumovie.base.dto;

import java.io.Serializable;
import java.util.Map;

public class AttributeEntry<K, V> implements Map.Entry<K, V>, Serializable {

	private static final long serialVersionUID = 4425717673867374767L;
	private K key;
	private V value;

	public AttributeEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}

	@SuppressWarnings("unchecked")
	public boolean equals(Object o) {
		if (!(o instanceof AttributeEntry)) {
			return false;
		}
		AttributeEntry<K, V> other = (AttributeEntry<K, V>) o;
		return other.key.equals(key) && (value == null ? other.value == null : other.value.equals(value));
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	public V setValue(Object newValue) {
		throw new UnsupportedOperationException();
	}

	public int hashCode() {
		return key.hashCode() ^ (value == null ? 0 : value.hashCode());
	}

	public String toString() {
		return key.toString() + "=" + value.toString();
	}
}
