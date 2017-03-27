package us.coastalhacking.corvus.annotations.ui.e4.handlers;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.adapter.Adapter;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.IStructuredSelection;

import us.coastalhacking.corvus.annotations.ui.common.E4ResourceAdapter;
import us.coastalhacking.corvus.annotations.ui.common.MarkerDTO;

@SuppressWarnings("restriction")
public abstract class AbstractHandler {

	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) IStructuredSelection selection, Adapter adapter) {

		if (selection == null || selection.isEmpty()) return;

		Object element = selection.getFirstElement();
		MarkerDTO dto = E4ResourceAdapter.adapt(element, adapter);

		if (dto == null || dto.resource == null) return;

		doExecute(dto);
	}

	public abstract void doExecute(MarkerDTO dto);

}