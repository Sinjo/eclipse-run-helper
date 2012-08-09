package uk.co.sinjakli.eclipserunhelper;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class RunHelperPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "uk.co.sinjakli.eclipserunhelper"; //$NON-NLS-1$

	// The shared instance
	private static RunHelperPlugin plugin;

	/**
	 * The constructor
	 */
	public RunHelperPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static RunHelperPlugin getDefault() {
		return plugin;
	}

	public static IStatus errorStatus(final String message, final Throwable t) {
		return new Status(IStatus.ERROR, PLUGIN_ID, message, t);
	}

}
