package uk.co.sinjakli.eclipserunhelper;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchManager;

public class RunLastLaunchHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
		final ILaunch[] launches = launchManager.getLaunches();
		
		try {
			launches[0].getLaunchConfiguration().launch(ILaunchManager.RUN_MODE, null);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
