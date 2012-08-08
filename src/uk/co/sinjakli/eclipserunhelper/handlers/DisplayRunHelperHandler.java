package uk.co.sinjakli.eclipserunhelper.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import uk.co.sinjakli.eclipserunhelper.ui.RunHelperDialog;

public class DisplayRunHelperHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
		final ILaunch[] launchHistory = launchManager.getLaunches();
		
		final List<ILaunch> availableLaunches = new ArrayList<ILaunch>();
		availableLaunches.addAll(Arrays.asList(launchHistory));
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				final Shell activeShell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();

				final RunHelperDialog dialog = new RunHelperDialog(activeShell, availableLaunches);

				dialog.open();
			}
		});

		return null;
	}
}
