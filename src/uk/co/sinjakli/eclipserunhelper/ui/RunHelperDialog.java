package uk.co.sinjakli.eclipserunhelper.ui;

import java.util.List;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class RunHelperDialog extends PopupDialog {
	
	private final List<ILaunchConfiguration> availableLaunches;

	public RunHelperDialog(final Shell parent, final List<ILaunchConfiguration> availableLaunches) {

		super(parent, PopupDialog.INFOPOPUP_SHELLSTYLE, true, false,
				false, false, false, null, null);
		
		this.availableLaunches = availableLaunches;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		Table table = new Table(composite, SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER);
		table.setLinesVisible(true);
		table.setHeaderVisible(false);

		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		table.setLayoutData(data);

		TableColumn launchNameColumn = new TableColumn(table, SWT.NONE);
		TableColumn keyBindingColumn = new TableColumn(table, SWT.NONE);
		
		TableViewer viewer = new TableViewer(table);
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.setInput(availableLaunches);
		
		TableViewerColumn launchNameColumnViewer = new TableViewerColumn(viewer, launchNameColumn);
		launchNameColumnViewer.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				ILaunchConfiguration launchConfiguration = (ILaunchConfiguration) element;
				return launchConfiguration.getName();
			}
		});
		
		TableViewerColumn keyBindingColumnViewer = new TableViewerColumn(viewer, keyBindingColumn);
		keyBindingColumnViewer.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return "1";
			}
		});

		// Needed so that eclipse updates table based on our label providers
		viewer.refresh();
		
		table.getColumn(0).pack();
		table.getColumn(1).pack();

		composite.pack();

		return composite;
	}

}
