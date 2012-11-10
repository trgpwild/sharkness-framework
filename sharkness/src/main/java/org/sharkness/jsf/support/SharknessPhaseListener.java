package org.sharkness.jsf.support;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.log4j.Logger;
import org.sharkness.logging.support.LoggerFactory;

@SuppressWarnings("serial")
public class SharknessPhaseListener implements PhaseListener {

	protected Logger getLogger() {
		return LoggerFactory.getLogger();
	}
	
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

	public void beforePhase(PhaseEvent event) {
		
		getLogger().info(
			new StringBuilder("SharknessPhaseListener.beforePhase(Start): ")
				.append(event.getPhaseId()).toString());

		FacesContext context;

		if (event.getPhaseId() == PhaseId.RENDER_RESPONSE) {
			context = event.getFacesContext();
			try {

				getLogger().info("SharknessPhaseListener.beforePhase: Adjusting Locale.");
				context.getViewRoot().setLocale(
					FacesContext.getCurrentInstance().getExternalContext().getRequestLocale()
				);

			} catch (Exception e) {
				getLogger().error("SharknessPhaseListener.beforePhase: Error in Getting Locale.", e);
			}
		}

	}

	public void afterPhase(PhaseEvent event) {
		getLogger().info(
			new StringBuilder("SharknessPhaseListener.afterPhase(Finish): ")
				.append(event.getPhaseId()).toString());
	}
	
}