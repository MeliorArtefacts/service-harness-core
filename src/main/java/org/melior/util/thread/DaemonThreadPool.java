/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.util.thread;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import org.melior.context.transaction.TransactionContext;

/**
 * Implements a pool of daemon threads that don't hold up the JVM when
 * it terminates.  A task may be executed by a child thread that
 * inherits some parameters from the callers transaction context.
 * @author Melior
 * @since 2.2
 */
public class DaemonThreadPool
{
		private static ExecutorService executor = Executors.newCachedThreadPool(new ThreadFactory()
	{

		public Thread newThread(
			final Runnable runnable)
		{
						Thread thread;

						thread = Executors.defaultThreadFactory().newThread(runnable);
			thread.setDaemon(true);

			return thread;
        }

	});

	/**
	 * Constructor.
	 */
	private DaemonThreadPool()
	{
				super();
	}

	/**
	 * Use thread to execute given callable.
	 * @param callable The callable to execute
	 */
	public <T> Future<T> execute(
		final Callable<T> callable)
	{
				return executor.submit(callable);
	}

	/**
	 * Use child thread to execute given callable.
	 * The child inherits the following parameters from the
	 * transaction context of the caller.
	 * <p><ul>
	 * <li>The origin identifier
	 * <li>The transaction identifier
	 * </ul><p>
	 * @param callable The callable to execute
	 */
	public <T> Future<T> executeAsChild(
		final Callable<T> callable)
	{
				TransactionContext callerContext;
		String originId;
		String transactionId;

				callerContext = TransactionContext.get();
		originId = callerContext.getOriginId();
		transactionId = callerContext.getTransactionId();

				return executor.submit(new Callable<T>()
		{

			public T call() throws Exception
			{
								TransactionContext childContext;

								childContext = TransactionContext.get();
				childContext.setOriginId(originId);
				childContext.setTransactionId(transactionId);

								return callable.call();
			}

		});
	}

	/**
	 * Execute given callable.
	 * @param callable The callable to execute
	 * @param timeout The time to wait in milliseconds
	 */
	public <T> Future<T> execute(
		final Callable<T> callable,
		final long timeout)
	{
				return null;
	}

	/**
	 * Use thread to execute given runnable.
	 * @param runnable The runnable to execute
	 */
	public void execute(
		final Runnable runnable)
	{
				executor.submit(runnable);
	}

	/**
	 * Use child thread to execute given runnable.
	 * The child inherits the following parameters from the
	 * transaction context of the caller.
	 * <p><ul>
	 * <li>The origin identifier
	 * <li>The transaction identifier
	 * </ul><p>
	 * @param runnable The runnable to execute
	 */
	public void executeAsChild(
		final Runnable runnable)
	{
				TransactionContext callerContext;
		String originId;
		String transactionId;

				callerContext = TransactionContext.get();
		originId = callerContext.getOriginId();
		transactionId = callerContext.getTransactionId();

				executor.submit(new Runnable()
		{

			public void run()
			{
								TransactionContext childContext;

								childContext = TransactionContext.get();
				childContext.setOriginId(originId);
				childContext.setTransactionId(transactionId);

								runnable.run();
			}

		});
	}

	/**
	 * Execute given runnable.
	 * @param runnable The runnable to execute
	 * @param timeout The time to wait in milliseconds
	 */
	public void execute(
		final Runnable runnable,
		final long timeout)
	{
			}

}
