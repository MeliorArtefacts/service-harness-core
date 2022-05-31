package org.melior.util.cache;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Implements a timed cache with bounded capacity.  When the cache reaches capacity
 * then the eldest entry is evicted to make room for new entries.  When an entry
 * has expired then it is evicted from the cache.
 */
public class TimedCache<K, V>
{

	/**
	 * Timed value.
	 */
	public class TimedValue<T extends V>
	{
				private long createTime;

				private T value;

		/**
		 * Constructor.
		 * @param createTime The create time
		 * @param value The value
		 */
		public TimedValue(
			final long createTime,
			final T value)
		{
						super();

						this.createTime = createTime;

						this.value = value;
		}

		/**
		 * Get create time.
		 * @return The create time
		 */
		public long getCreateTime()
		{
			return createTime;
		}

		/**
		 * Get value.
		 * @return The value
		 */
		public T getValue()
		{
			return value;
		}

	}

		private int capacity;

		private long lifetime;

		protected Map<K, TimedValue<V>> cacheMap;

	/**
	 * Constructor.
	 * @param capacity The initial capacity of the cache
	 * @param lifetime The lifetime of the cache entries
	 * @param timeUnit The time unit of the lifetime
	 */
	public TimedCache(
		final int capacity,
		final long lifetime,
		final TimeUnit timeUnit)
	{
				super();

				this.capacity = capacity;

				this.lifetime = timeUnit.toMillis(lifetime);

				cacheMap = new LinkedHashMap<K, TimedValue<V>>(capacity, 0.75f, false);
	}

	/**
	 * Get capacity of cache.
	 * @return The capacity
	 */
	public int getCapacity()
	{
		return capacity;
	}

	/**
	 * Set capacity of cache.
	 * @param capacity The capacity
	 */
	public void setCapacity(
		final int capacity)
	{
		this.capacity = capacity;
	}

	/**
	 * Get entry lifetime.
	 * @return The entry lifetime
	 */
	public long getLifetime(){
		return lifetime;
	}

	/**
	 * Set entry lifetime.
	 * @param lifetime The entry lifetime
	 */
	public void setLifetime(
		final int lifetime)
	{
				this.lifetime = lifetime;
	}

	/**
	 * Add entry to cache.
	 * @param key The key of the entry
	 * @param value The value of the entry
	 * @return true if an entry was added to the cache, false otherwise
	 */
	public final boolean add(
		final K key,
		final V value)
	{

				if (get(key) == null)
		{

						if (atCapacity() == true)
			{
								evict();
			}

						cacheMap.put(key, new TimedValue<V>(System.currentTimeMillis(), value));

			return true;
		}

		return false;
	}

	/**
	 * Get value from cache.
	 * @param key The key to use to find the value
	 * @return The value, or null if the key is not found in the cache
	 */
	public V get(
		final K key)
	{
				TimedValue<V> value;

				evict(System.currentTimeMillis());

				value = cacheMap.get(key);
		return (value == null) ? null : value.getValue();
	}

	/**
	 * Evict expired entries from cache.
	 * @param currentTime The current time
	 */
	protected void evict(
		final long currentTime)
	{
				List<K> keyList;
		TimedValue<V> timedValue;

				keyList = new LinkedList<K>();

				for (Map.Entry<K, TimedValue<V>> mapEntry : cacheMap.entrySet())
		{
			timedValue = mapEntry.getValue();

						if ((currentTime - timedValue.getCreateTime()) < lifetime)
			{
				break;
			}

						keyList.add(mapEntry.getKey());
		}

				keyList.forEach(key -> evict(key));
	}

	/**
	 * Evict eldest entry from cache.
	 */
	protected void evict()
	{
				evict(cacheMap.keySet().iterator().next());
	}

	/**
	 * Evict entry from cache.
	 * @param key The key to use to find the entry
	 */
	public void evict(
		final K key)
	{
				destroyValue(cacheMap.remove(key).getValue());
	}

	/**
	 * Destroy value.
	 * @param value The value to destroy
	 */
	protected void destroyValue(
		final V value)
	{

				if (value == null)
		{
			return;
		}

				try {value.getClass().getMethod("destroy", (Class[]) null).invoke(value, (Object[]) null);} catch (Exception exception) {}
	}

	/**
	 * Clear cache.  Evict all entries from cache.
	 */
	public void clear()
	{
				List<K> keyList;

				keyList = new LinkedList<K>(cacheMap.keySet());

				keyList.forEach(key -> evict(key));
	}

	/**
	 * Check whether cache is at capacity.
	 * @return true if the cache is at capacity, false otherwise
	 */
	public boolean atCapacity()
	{
		return ((capacity > 0) && (cacheMap.size() >= capacity));
	}

}
