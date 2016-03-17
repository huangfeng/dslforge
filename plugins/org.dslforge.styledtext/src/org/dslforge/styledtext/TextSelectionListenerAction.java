/**
 * <copyright>
 *
 * Copyright (c) 2015 PlugBee. All rights reserved.
 * 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Amine Lajmi - Initial API and implementation
 *
 * </copyright>
 */
package org.dslforge.styledtext;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Event;

public class TextSelectionListenerAction extends Action implements ISelectionChangedListener {

	private static final long serialVersionUID = 1L;

	/**
	 * The current selection.
	 */
	private ITextSelection selection = new TextSelection(0,0);

	/**
	 * Running flag: <code>true</code> iff the action is running.
	 */
	private boolean running = false;

	/**
	 * The deferred selection. Any selection change that occurs while the action
	 * is running is held here until the run is complete.
	 */
	private ITextSelection deferredSelection = null;

	/**
	 * Creates a new action with the given text.
	 *
	 * @param text
	 *            the string used as the text for the action, or
	 *            <code>null</code> if there is no text
	 */
	protected TextSelectionListenerAction(String text) {
		super(text);
	}

	/**
	 * Clears any cached state associated with the selection. Called when the
	 * selection changes.
	 * <p>
	 * The <code>BaseSelectionListenerAction</code> implementation of this
	 * method does nothing. Subclasses may override.
	 * </p>
	 */
	protected void clearCache() {
		// do nothing
	}

	/**
	 * Returns the current structured selection in the workbench, or an empty
	 * selection if nothing is selected or if selection does not include objects
	 * (for example, raw text).
	 *
	 * @return the current structured selection in the workbench
	 */
	public ITextSelection getTextSlection() {
		return selection;
	}

	/**
	 * Notifies this action that the given structured selection has changed.
	 * <p>
	 * The <code>BaseSelectionListenerAction</code> implementation of this
	 * method records the given selection for future reference and calls
	 * <code>updateSelection</code>, updating the enable state of this action
	 * based on the outcome. Subclasses should override
	 * <code>updateSelection</code> to react to selection changes.
	 * </p>
	 *
	 * @param selection
	 *            the new selection
	 */
	public final void selectionChanged(ITextSelection selection) {
		// Ignore any incoming selection change while the action is running,
		// otherwise the action can have unpredictable results, including lost
		// data, if it operates on a different selection than what it initially
		// validated.
		// See Bug 60606 [Navigator] (data loss) Navigator deletes/moves the
		// wrong file
		if (running) {
			deferredSelection = selection;
			return;
		}
		this.selection = selection;
		clearCache();
		setEnabled(updateSelection(selection));
	}

	/**
	 * The <code>BaseSelectionListenerAction</code> implementation of this
	 * <code>ISelectionChangedListener</code> method calls
	 * <code>selectionChanged(IStructuredSelection)</code> assuming the
	 * selection is a structured one. Subclasses should override the
	 * <code>updateSelection</code> method to react to selection changes.
	 */
	public final void selectionChanged(SelectionChangedEvent event) {
		ISelection selection = event.getSelection();
		if (selection instanceof IStructuredSelection) {
			selectionChanged((ITextSelection) selection);
		} else {
			selectionChanged(TextSelection.emptySelection());
		}
	}

	/**
	 * Updates this action in response to the given selection.
	 * <p>
	 * The <code>BaseSelectionListenerAction</code> implementation of this
	 * method returns <code>true</code>. Subclasses may extend to react to
	 * selection changes; however, if the super method returns
	 * <code>false</code>, the overriding method must also return
	 * <code>false</code>.
	 * </p>
	 *
	 * @param selection
	 *            the new selection
	 * @return <code>true</code> if the action should be enabled for this
	 *         selection, and <code>false</code> otherwise
	 */
	protected boolean updateSelection(ITextSelection selection) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.action.Action#runWithEvent(org.eclipse.swt.widgets.
	 * Event)
	 */
	public void runWithEvent(Event event) {
		// Set the running flag during the run so that selection changes are
		// deferred.
		// See selectionChanged(IStructuredSelection) for more details.
		running = true;
		try {
			run();
		} finally {
			running = false;
			ITextSelection s = deferredSelection;
			deferredSelection = null;
			if (s != null) {
				selectionChanged(s);
			}
		}
	}
}
