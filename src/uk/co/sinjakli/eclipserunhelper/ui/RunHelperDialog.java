package uk.co.sinjakli.eclipserunhelper.ui;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import uk.co.sinjakli.eclipserunhelper.RunHelperPlugin;

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

		final TableViewer tableViewer = new TableViewer(table);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.setInput(availableLaunches.keySet());

		final TableViewerColumn launchNameColumnViewer = new TableViewerColumn(tableViewer, launchNameColumn);
		launchNameColumnViewer.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				final ILaunchConfiguration launchConfiguration = (ILaunchConfiguration) element;
				return "Run " + launchConfiguration.getName();
			}
		});

		final TableViewerColumn keyBindingColumnViewer = new TableViewerColumn(tableViewer, keyBindingColumn);
		keyBindingColumnViewer.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return availableLaunches.get(element);
			}
		});

		tableViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(final DoubleClickEvent event) {
				final IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				final ILaunchConfiguration launchConfiguration = (ILaunchConfiguration) selection.getFirstElement();
				try {
					launchConfiguration.launch(ILaunchManager.RUN_MODE, null);
					RunHelperDialog.this.close();
				} catch (final CoreException e) {
					final ILog logger = RunHelperPlugin.getDefault().getLog();
					final IStatus errorStatus = RunHelperPlugin.errorStatus("Error launching selected configuration.", e);
					logger.log(errorStatus);
				}
			}
		});

		// Needed so that eclipse updates table based on our label providers
		tableViewer.refresh();

		table.getColumn(0).pack();
		table.getColumn(1).pack();

		composite.pack();

		return composite;
	}

}
