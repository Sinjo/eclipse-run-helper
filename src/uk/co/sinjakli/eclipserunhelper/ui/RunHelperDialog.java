package uk.co.sinjakli.eclipserunhelper.ui;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import uk.co.sinjakli.eclipserunhelper.RunHelperPlugin;

public class RunHelperDialog extends PopupDialog {

	private final Map<ILaunchConfiguration, String> availableLaunches;
	private final ILog logger;
	private final Set<Image> disposableImages;

	public RunHelperDialog(final Shell parent, final Map<ILaunchConfiguration, String> availableLaunches) {

		super(parent, PopupDialog.INFOPOPUP_SHELLSTYLE, true, false,
				false, false, false, null, null);

		this.availableLaunches = availableLaunches;
		logger = RunHelperPlugin.getDefault().getLog();
		disposableImages = new HashSet<Image>();
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

			@Override
			public Image getImage(final Object element) {
				final ILaunchConfiguration launchConfiguration = (ILaunchConfiguration) element;
				try {
					final ILaunchConfigurationType launchConfigurationType = launchConfiguration.getType();
					final Image launchImage = DebugUITools.getDefaultImageDescriptor(launchConfigurationType).createImage(true);
					disposableImages.add(launchImage);
					return launchImage;
				} catch (final CoreException e) {
					final IStatus errorStatus = RunHelperPlugin.errorStatus("Error getting launch configuration type.", e);
					logger.log(errorStatus);
				}
				return super.getImage(element);
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
				launchAndCloseDialog(launchConfiguration);
			}
		});

		table.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(final KeyEvent e) {
				if (isEnterKey(e.keyCode)) {
					final TableItem tableSelection = table.getSelection()[0];
					final ILaunchConfiguration launchConfiguration = (ILaunchConfiguration) tableSelection.getData();
					launchAndCloseDialog(launchConfiguration);
				} else {
					launchByCharacter(e.character);
				}
			}

			private boolean isEnterKey(final int keyCode) {
				return keyCode == SWT.CR || keyCode == SWT.LF;
			}
		});

		// Needed so that eclipse updates table based on our label providers
		tableViewer.refresh();

		table.getColumn(0).pack();
		table.getColumn(1).pack();

		composite.pack();

		table.setSelection(0);

		return composite;
	}

	private void launchAndCloseDialog(final ILaunchConfiguration launchConfiguration) {
		try {
			launchConfiguration.launch(ILaunchManager.RUN_MODE, null);
		} catch (final CoreException ex) {
			final IStatus errorStatus = RunHelperPlugin.errorStatus("Error launching selected configuration.", ex);
			logger.log(errorStatus);
		}

		RunHelperDialog.this.close();
	}

	private void launchByCharacter(final char keyCharacter) {
		final String keyString = String.valueOf(keyCharacter);
		for (final Entry<ILaunchConfiguration, String> launchBinding : availableLaunches.entrySet()) {
			if (launchBinding.getValue().equals(keyString)) {
				launchAndCloseDialog(launchBinding.getKey());
			}
		}

	}

	@Override
	public boolean close() {
		for (final Image image : disposableImages) {
			image.dispose();
		}
		return super.close();
	}

}
