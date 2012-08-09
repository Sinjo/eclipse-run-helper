package uk.co.sinjakli.eclipserunhelper.ui;

import java.util.Map;

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
	
	private final Map<ILaunchConfiguration, String> availableLaunches;

	public RunHelperDialog(final Shell parent, final Map<ILaunchConfiguration, String> availableLaunches) {

		super(parent, PopupDialog.INFOPOPUP_SHELLSTYLE, true, false,
				false, false, false, null, null);
		
		this.availableLaunches = availableLaunches;
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		final Composite composite = (Composite) super.createDialogArea(parent);

		final Table table = new Table(composite, SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER);
		table.setLinesVisible(true);
		table.setHeaderVisible(false);

		final GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		table.setLayoutData(data);

		final TableColumn launchNameColumn = new TableColumn(table, SWT.NONE);
		final TableColumn keyBindingColumn = new TableColumn(table, SWT.NONE);
		
		final TableViewer viewer = new TableViewer(table);
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.setInput(availableLaunches.keySet());
		
		final TableViewerColumn launchNameColumnViewer = new TableViewerColumn(viewer, launchNameColumn);
		launchNameColumnViewer.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				final ILaunchConfiguration launchConfiguration = (ILaunchConfiguration) element;
				return launchConfiguration.getName();
			}
		});
		
		final TableViewerColumn keyBindingColumnViewer = new TableViewerColumn(viewer, keyBindingColumn);
		keyBindingColumnViewer.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return availableLaunches.get(element);
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
