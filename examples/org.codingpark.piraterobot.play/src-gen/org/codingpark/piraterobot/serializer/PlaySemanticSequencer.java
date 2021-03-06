/*
 * generated by Xtext
 */
package org.codingpark.piraterobot.serializer;

import com.google.inject.Inject;
import java.util.Set;
import org.codingpark.piraterobot.play.ParameterValue;
import org.codingpark.piraterobot.play.PlayModel;
import org.codingpark.piraterobot.play.PlayPackage;
import org.codingpark.piraterobot.play.Procedure;
import org.codingpark.piraterobot.services.PlayGrammarAccess;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class PlaySemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private PlayGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == PlayPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case PlayPackage.ACTION:
				sequence_Action(context, (org.codingpark.piraterobot.play.Action) semanticObject); 
				return; 
			case PlayPackage.PARAMETER_VALUE:
				sequence_ParameterValue(context, (ParameterValue) semanticObject); 
				return; 
			case PlayPackage.PLAY_MODEL:
				sequence_PlayModel(context, (PlayModel) semanticObject); 
				return; 
			case PlayPackage.PROCEDURE:
				sequence_Procedure(context, (Procedure) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Contexts:
	 *     Action returns Action
	 *
	 * Constraint:
	 *     (
	 *         (isUp?='up' parameterValue=ParameterValue?) | 
	 *         (isRight?='right' parameterValue=ParameterValue?) | 
	 *         (isDown?='down' parameterValue=ParameterValue?) | 
	 *         (isLeft?='left' parameterValue=ParameterValue?) | 
	 *         (isDig?='dig' parameterValue=ParameterValue?) | 
	 *         (isJump?='jump' parameterValue=ParameterValue?) | 
	 *         (isFight?='fight' parameterValue=ParameterValue?) | 
	 *         (isRepeat?='repeat' parameterValue=ParameterValue? actions+=Action*)
	 *     )
	 */
	protected void sequence_Action(ISerializationContext context, org.codingpark.piraterobot.play.Action semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     ParameterValue returns ParameterValue
	 *
	 * Constraint:
	 *     num=INT
	 */
	protected void sequence_ParameterValue(ISerializationContext context, ParameterValue semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, PlayPackage.Literals.PARAMETER_VALUE__NUM) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PlayPackage.Literals.PARAMETER_VALUE__NUM));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getParameterValueAccess().getNumINTTerminalRuleCall_0(), semanticObject.getNum());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     PlayModel returns PlayModel
	 *
	 * Constraint:
	 *     procedures+=Procedure
	 */
	protected void sequence_PlayModel(ISerializationContext context, PlayModel semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Procedure returns Procedure
	 *
	 * Constraint:
	 *     (name=ID actions+=Action*)
	 */
	protected void sequence_Procedure(ISerializationContext context, Procedure semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
}
