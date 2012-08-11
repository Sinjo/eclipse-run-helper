package uk.co.sinjakli.eclipserunhelper.handlers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.internal.ui.launchConfigurations.LaunchConfigurationManager;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jdt.internal.junit.launcher.JUnitLaunchConfigurationConstants;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import uk.co.sinjakli.eclipserunhelper.RunHelperPlugin;
import uk.co.sinjakli.eclipserunhelper.ui.RunHelperDialog;

@SuppressWarnings("restriction")
public class DisplayRunHelperHandler extends AbstractHandler {

	private static final int MAX_RECENT_LAUNCHES = 5;
	private ILog logger;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		logger = RunHelperPlugin.getDefault().getLog();

		final String launchTypeParemeter = event.getParameter("uk.co.sinjakli.eclipserunhelper.launchType");
		final String launchType;
		if (launchTypeParemeter.equals("RUN")) {
			launchType = ILaunchManager.RUN_MODE;
		} else {
			launchType = ILaunchManager.DEBUG_MODE;
		}

		final ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
		final LaunchConfigurationManager launchConfigurationManager = DebugUIPlugin.getDefault().getLaunchConfigurationManager();

		final ILaunchConfiguration[] launchHistory = launchConfigurationManager
				.getLaunchHistory(IDebugUIConstants.ID_RUN_LAUNCH_GROUP)
				.getHistory();

		final Map<String, ILaunchConfiguration> availableLaunches = new LinkedHashMap<String, ILaunchConfiguration>();
		int launchIndex = 1;
		for (final ILaunchConfiguration launchConfiguration : launchHistory) {
			availableLaunches.put(String.valueOf(launchIndex), launchConfiguration);
			launchIndex++;

			if (launchIndex > MAX_RECENT_LAUNCHES) {
				break;
			}
		}

		final ILaunchConfiguration lastJUnitLaunch = getLastJunitLaunch(launchManager, launchHistory);
		if (lastJUnitLaunch != null) {
			availableLaunches.put("t", lastJUnitLaunch);
		}

		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				final Shell activeShell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();

				final RunHelperDialog dialog = new RunHelperDialog(activeShell,
						availableLaunches, launchType);

				dialog.open();
			}
		});

		return null;
	}

	private ILaunchConfiguration getLastJunitLaunch(final ILaunchManager launchManager, final ILaunchConfiguration[] launchHistory) {
		final ILaunchConfigurationType jUnitLaunchType = launchManager.getLaunchConfigurationType(JUnitLaunchConfigurationConstants.ID_JUNIT_APPLICATION);

		for (final ILaunchConfiguration launchConfiguration : launchHistory) {
			try {
				if (launchConfiguration.getType().equals(jUnitLaunchType)) {
					return launchConfiguration;
				}
			} catch (final CoreException e) {
				final IStatus errorStatus = RunHelperPlugin.errorStatus("Error finding JUnit launch configuration.", e);
				logger.log(errorStatus);
			}
		}

		return null;
	}
}
