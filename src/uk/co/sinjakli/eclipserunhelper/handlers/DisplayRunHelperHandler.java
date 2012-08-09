package uk.co.sinjakli.eclipserunhelper.handlers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import uk.co.sinjakli.eclipserunhelper.ui.RunHelperDialog;

@SuppressWarnings("restriction")
public class DisplayRunHelperHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ILaunchConfiguration[] launchHistory = DebugUIPlugin.getDefault()
				.getLaunchConfigurationManager()
				.getLaunchHistory(IDebugUIConstants.ID_RUN_LAUNCH_GROUP)
				.getHistory();

		final Map<ILaunchConfiguration, String> availableLaunches = new LinkedHashMap<ILaunchConfiguration, String>();
		int launchIndex = 1;
		for (final ILaunchConfiguration launchConfiguration : launchHistory) {
			availableLaunches.put(launchConfiguration, String.valueOf(launchIndex));
			launchIndex++;
		}

		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				final Shell activeShell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();

				final RunHelperDialog dialog = new RunHelperDialog(activeShell,
						availableLaunches);

				dialog.open();
			}
		});

		return null;
	}
}
