package us.coastalhacking.corvus.annotations.ui.e3;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;

import us.coastalhacking.corvus.annotations.ui.common.MarkerAdapter;
import us.coastalhacking.corvus.annotations.ui.common.MarkerDTO;

public abstract class AbstractAnnotationHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// This method always returns null
		final Object result = null;

		final ISelection selection = HandlerUtil.getCurrentSelection(event);

		if (selection != null && !selection.isEmpty() && selection instanceof ITextSelection) {

			MarkerDTO dto = adapt((ITextSelection)selection, event);

			if (dto != null && dto.resource != null) {
				doExecute(dto);
			}
		}
		return result;
	}

	protected abstract void doExecute(MarkerDTO dto);
	
	private MarkerDTO adapt(ITextSelection textSelection, ExecutionEvent event) {
		MarkerDTO dto = createMarkerDTO(textSelection, event);

		// TODO: test
		// TODO: abstract out as OSGi DS
		try {
			IJavaElement javaElement = getJavaElement(textSelection, event);
			if (javaElement != null) {
				MarkerAdapter.adaptJavaElement(javaElement, dto);
			}
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dto;
	}
	
	// http://stackoverflow.com/a/13243290
	private IJavaElement getJavaElement(ITextSelection textSelection, ExecutionEvent event) throws JavaModelException {

		final IEditorPart activeEditor = HandlerUtil.getActiveEditor(event);
		if (activeEditor instanceof ITextEditor) {
			final ITextEditor editor = (ITextEditor) activeEditor;
			IJavaElement javaElement = JavaUI.getEditorInputJavaElement(editor.getEditorInput());
			if (javaElement == null) {
				return null;
			}

			switch (javaElement.getElementType()) {
			case (IJavaElement.COMPILATION_UNIT):
			    return ((ICompilationUnit)javaElement).getElementAt(textSelection.getOffset());

			default:
				break;
			}
		}
		return null;

	}

	private MarkerDTO createMarkerDTO(ITextSelection textSelection, ExecutionEvent event) {
		MarkerDTO results = new MarkerDTO();
		results.text = textSelection.getText();

		final IEditorPart activeEditor = HandlerUtil.getActiveEditor(event);
		if (activeEditor instanceof ITextEditor) {
			final ITextEditor editor = (ITextEditor) activeEditor;
			results.resource = editor.getAdapter(IResource.class);
		}

		MarkerAdapter.adaptTextSelection(textSelection, results);

		return results;
	}
}
