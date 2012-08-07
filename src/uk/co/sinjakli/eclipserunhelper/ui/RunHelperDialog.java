package uk.co.sinjakli.eclipserunhelper.ui;

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

	public RunHelperDialog(Shell parent, int shellStyle,
			boolean takeFocusOnOpen, boolean persistSize,
			boolean persistLocation, boolean showDialogMenu,
			boolean showPersistActions, String titleText, String infoText) {
		
		super(parent, shellStyle, takeFocusOnOpen, persistSize, persistLocation,
				showDialogMenu, showPersistActions, titleText, infoText);
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
		
		TableItem line1 = new TableItem(table, SWT.NONE);
		line1.setText(0, "Run previous Launch");
		line1.setText(1, "1");
		
		TableItem line2 = new TableItem(table, SWT.NONE);
		line2.setText(0, "Run 2nd previous Launch");
		line2.setText(1, "2");
		
		table.getColumn(0).pack();
		table.getColumn(1).pack();
		
		composite.pack();
		
		return composite;
	}

}
