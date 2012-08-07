package uk.co.sinjakli.eclipserunhelper;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class DisplayRunHelperHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				final Shell activeShell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();

				final PopupDialog dialog = new PopupDialog(activeShell,
						PopupDialog.INFOPOPUPRESIZE_SHELLSTYLE, true, false,
						false, false, false, "Title", "Info");

				dialog.open();
			}
		});

		return null;
	}
}
