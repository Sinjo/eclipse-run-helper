package uk.co.sinjakli.eclipserunhelper.ui;

import java.util.List;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class RunHelperDialog extends PopupDialog {
	
	private final List<ILaunch> availableLaunches;

	public RunHelperDialog(final Shell parent, final List<ILaunch> availableLaunches) {

		super(parent, PopupDialog.INFOPOPUP_SHELLSTYLE, true, false,
				false, false, false, null, null);
		
		this.availableLaunches = availableLaunches;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		Table table = new Table(composite, SWT.SINGLE | SWT.BORDER);
		table.setLinesVisible(true);
		table.setHeaderVisible(false);

		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		table.setLayoutData(data);

		new TableColumn(table, SWT.NONE).setText("Col1");
		new TableColumn(table, SWT.NONE).setText("Col2");
		
		int launchCounter = 1;
		for (ILaunch launch : availableLaunches) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, launch.getLaunchConfiguration().getName());
			item.setText(1, String.valueOf(launchCounter));
			launchCounter++;
		}

		table.getColumn(0).pack();
		table.getColumn(1).pack();

		composite.pack();

		return composite;
	}

}
