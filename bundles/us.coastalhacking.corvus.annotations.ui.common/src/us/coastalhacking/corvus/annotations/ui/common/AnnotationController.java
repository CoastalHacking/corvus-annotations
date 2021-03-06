package us.coastalhacking.corvus.annotations.ui.common;

import org.eclipse.core.resources.IMarker;

import us.coastalhacking.corvus.annotations.Markers;

public class AnnotationController {

	/**
	 * Creates an entry point marker and associated tasks
	 * 
	 * @param dto  a marker dto containing the information needed to make a marker
	 */
	// TODO: test
	public static void addEntryPoint(MarkerDTO dto) {
		try {
			// Create entry point
			IMarker entryPointMarker = MarkerAdapter.adapt(dto, Markers.ENTRY_POINT);
			entryPointMarker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
			entryPointMarker.setAttribute(IMarker.USER_EDITABLE, true);
			entryPointMarker.setAttribute(Markers.EP_ATTR_METHOD, Markers.EMPTY_STRING);
			entryPointMarker.setAttribute(Markers.EP_ATTR_URL, Markers.EMPTY_STRING);
			entryPointMarker.setAttribute(Markers.EP_ATTR_AUTHN, Markers.EMPTY_STRING);
			entryPointMarker.setAttribute(Markers.EP_ATTR_AUTHZ, Markers.EMPTY_STRING);
			entryPointMarker.setAttribute(Markers.EP_ATTR_CSRF, Markers.EMPTY_STRING);
			entryPointMarker.setAttribute(IMarker.MESSAGE, dto.text);

			// Create tasks around entry point
			IMarker idempotentMarker = MarkerAdapter.adapt(dto, IMarker.TASK);
			idempotentMarker.setAttribute(IMarker.MESSAGE, String.format("Review CSRF susceptibility on entry point %s", dto.text));
			idempotentMarker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);

			IMarker authenticationMarker = MarkerAdapter.adapt(dto, IMarker.TASK);
			authenticationMarker.setAttribute(IMarker.MESSAGE, String.format("Review authentication on entry point %s", dto.text));
			authenticationMarker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);

			IMarker authorizationMarker = MarkerAdapter.adapt(dto, IMarker.TASK);
			authorizationMarker.setAttribute(IMarker.MESSAGE, String.format("Review authorization on entry point %s", dto.text));
			authorizationMarker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);

		} catch (Exception e) {
			// TODO : log
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Creates a sink marker and associated tasks
	 * 
	 * @param dto  a marker dto containing the information needed to make a marker
	 */
	// TODO: test
	public static void addSink(MarkerDTO dto) {
		try {
			// Create sink
			IMarker sinkMarker = MarkerAdapter.adapt(dto, Markers.SINK);
			sinkMarker.setAttribute(IMarker.USER_EDITABLE, true);
			sinkMarker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
			sinkMarker.setAttribute(IMarker.MESSAGE, dto.text);
			sinkMarker.setAttribute(Markers.SINK_ATTR_DETAILS, Markers.EMPTY_STRING);
			sinkMarker.setAttribute(Markers.SINK_ATTR_SINK_TYPE, Markers.EMPTY_STRING);
			sinkMarker.setAttribute(Markers.SINK_ATTR_CWE, Markers.EMPTY_STRING);

			// Create tasks around sink
			IMarker controllabilityMarker = MarkerAdapter.adapt(dto, IMarker.TASK);
			controllabilityMarker.setAttribute(IMarker.MESSAGE, String.format("Review user-controllability of data to sink %s", dto.text));
			controllabilityMarker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);

		} catch (Exception e) {
			// TODO : log
			e.printStackTrace();
		}
	}


	/**
	 * 
	 * Creates an issue and associated tasks
	 * 
	 * @param dto  a marker dto containing the information needed to make a marker
	 */
	// TODO: test
	public static void addIssue(MarkerDTO dto) {
		try {
			// Create issue
			IMarker issueMarker = MarkerAdapter.adapt(dto, Markers.ISSUE);
			issueMarker.setAttribute(IMarker.USER_EDITABLE, true);
			issueMarker.setAttribute(IMarker.MESSAGE, String.format("Security issue with %s", dto.text));
			issueMarker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
			issueMarker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);

			// Create tasks around issue
			IMarker documentMarker = MarkerAdapter.adapt(dto, IMarker.TASK);
			documentMarker.setAttribute(IMarker.MESSAGE, String.format("Document security issue with %s", dto.text));
			documentMarker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);

		} catch (Exception e) {
			// TODO : log
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Creates a taint marker
	 * 
	 * @param dto  a marker dto containing the information needed to make a marker
	 */
	// TODO: test
	public static void addTaint(MarkerDTO dto) {
		try {
			IMarker taintMarker = MarkerAdapter.adapt(dto, Markers.TAINT);
			taintMarker.setAttribute(IMarker.USER_EDITABLE, true);
			taintMarker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
			taintMarker.setAttribute(IMarker.MESSAGE, dto.text);

		} catch (Exception e) {
			// TODO : log
			e.printStackTrace();
		}
	}

	
	/**
	 * 
	 * Creates a control marker
	 * 
	 * @param dto  a marker dto containing the information needed to make a marker
	 */
	// TODO: test
	public static void addControl(MarkerDTO dto) {
		try {
			IMarker controlMarker = MarkerAdapter.adapt(dto, Markers.CONTROL);
			controlMarker.setAttribute(IMarker.USER_EDITABLE, true);
			controlMarker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
			controlMarker.setAttribute(IMarker.MESSAGE, dto.text);
			controlMarker.setAttribute(Markers.CONTROL_ATTR_DETAILS, Markers.EMPTY_STRING);

		} catch (Exception e) {
			// TODO : log
			e.printStackTrace();
		}
	}
}
