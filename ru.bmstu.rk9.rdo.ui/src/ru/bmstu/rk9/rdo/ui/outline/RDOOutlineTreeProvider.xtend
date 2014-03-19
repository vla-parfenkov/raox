package ru.bmstu.rk9.rdo.ui.outline

import org.eclipse.xtext.ui.editor.outline.impl.AbstractOutlineNode
import org.eclipse.xtext.ui.editor.outline.IOutlineNode

import org.eclipse.swt.graphics.Image

import ru.bmstu.rk9.rdo.rdo.ResourceTypeParameter

import ru.bmstu.rk9.rdo.rdo.ResourceTrace
import ru.bmstu.rk9.rdo.rdo.ResourceDeclaration

import ru.bmstu.rk9.rdo.rdo.Sequence

import ru.bmstu.rk9.rdo.rdo.Function
import ru.bmstu.rk9.rdo.rdo.FunctionParameter

import ru.bmstu.rk9.rdo.rdo.ConstantDeclaration

import ru.bmstu.rk9.rdo.rdo.Pattern
import ru.bmstu.rk9.rdo.rdo.PatternParameter
import ru.bmstu.rk9.rdo.rdo.OperationRelevantResource
import ru.bmstu.rk9.rdo.rdo.Rule
import ru.bmstu.rk9.rdo.rdo.Operation
import ru.bmstu.rk9.rdo.rdo.Event
import ru.bmstu.rk9.rdo.rdo.RuleConvert
import ru.bmstu.rk9.rdo.rdo.EventConvert
import ru.bmstu.rk9.rdo.rdo.OperationConvert
import ru.bmstu.rk9.rdo.rdo.RuleRelevantResource
import ru.bmstu.rk9.rdo.rdo.EventRelevantResource

import ru.bmstu.rk9.rdo.rdo.DecisionPointSearchActivities
import ru.bmstu.rk9.rdo.rdo.DecisionPointActivities

import ru.bmstu.rk9.rdo.rdo.ProcessGenerate
import ru.bmstu.rk9.rdo.rdo.ProcessAdvance
import ru.bmstu.rk9.rdo.rdo.ProcessAssign
import ru.bmstu.rk9.rdo.rdo.ProcessTerminate
import ru.bmstu.rk9.rdo.rdo.ProcessQueue

import ru.bmstu.rk9.rdo.rdo.ResultDeclaration


public class VirtualOutlineNode extends AbstractOutlineNode
{
	protected new(IOutlineNode parent, Image image, Object text, boolean isLeaf)
	{
		super(parent, image, text, isLeaf)
	}
}

class RDOOutlineTreeProvider extends org.eclipse.xtext.ui.editor.outline.impl.DefaultOutlineTreeProvider
{
	// Resource Types
	def _isLeaf(ResourceTypeParameter rtp) { true }

	// Resources
	def _isLeaf(ResourceDeclaration rss) { true }
	def _isLeaf(ResourceTrace rsstrc)    { true }

	// Sequence
	def _isLeaf(Sequence seq) { true }

	// Functions
	def _createChildren(IOutlineNode parentNode, Function fun)
	{
		for (e : fun.eAllContents.toIterable.filter(typeof(FunctionParameter)))
		{
			createNode(parentNode, e)
		}
	}
	def _isLeaf(FunctionParameter p) { true }
	
	// Constants
	def _isLeaf (ConstantDeclaration c) { true }
	
	// Pattern
	def _createChildren(IOutlineNode parentNode, Pattern pat)
	{
		if (pat.eAllContents.toList.filter(typeof(PatternParameter)).size > 0)
		{
			val groupParameters = new VirtualOutlineNode(parentNode, parentNode.image, "Parameters", false)
			for (p : pat.eAllContents.toIterable.filter(typeof(PatternParameter)))
			{
				createEObjectNode(groupParameters, p)
			}
		}
		
		switch pat
		{
			Rule:
			{
				val groupRelRes = new VirtualOutlineNode(parentNode, parentNode.image, "Relevant resources", false)
				for (r : pat.eAllContents.toIterable.filter(typeof(RuleRelevantResource)))
				{
					createEObjectNode(groupRelRes, r)
				}
				
				val groupRelBody = new VirtualOutlineNode(parentNode, parentNode.image, "Body", false)
				for (b : pat.eAllContents.toIterable.filter(typeof(RuleConvert)))
				{
					createEObjectNode(groupRelBody, b)
				}
			}
			Operation:
			{
				val groupRelRes = new VirtualOutlineNode(parentNode, parentNode.image, "Relevant resources", false)
				for (r : pat.eAllContents.toIterable.filter(typeof(OperationRelevantResource)))
				{
					createEObjectNode(groupRelRes, r)
				}
				
				val groupRelBody = new VirtualOutlineNode(parentNode, parentNode.image, "Body", false)
				for (b : pat.eAllContents.toIterable.filter(typeof(OperationConvert)))
				{
					createEObjectNode(groupRelBody, b)
				}
			}
			Event:
			{				
				val groupRelRes = new VirtualOutlineNode(parentNode, parentNode.image, "Relevant resources", false)
				for (r : pat.eAllContents.toIterable.filter(typeof(EventRelevantResource)))
				{
					createEObjectNode(groupRelRes, r)
				}
				
				val groupRelBody = new VirtualOutlineNode(parentNode, parentNode.image, "Body", false)
				for (b : pat.eAllContents.toIterable.filter(typeof(EventConvert)))
				{
					createEObjectNode(groupRelBody, b)
				}
			}
		}
	}
	
	def _isLeaf(RuleConvert c)      { true }
	def _isLeaf(OperationConvert c) { true }
	def _isLeaf(EventConvert c)     { true }
	
	// Decision points
	def _isLeaf(DecisionPointSearchActivities d) { true }
	def _isLeaf(DecisionPointActivities d)       { true }
	
	// Process
	def _isLeaf(ProcessGenerate pg)  { true }
	def _isLeaf(ProcessAdvance pa)   { true }
	def _isLeaf(ProcessAssign pa)    { true }
	def _isLeaf(ProcessTerminate pt) { true }
	def _isLeaf(ProcessQueue pq)     { true }
	
	// Results
	def _isLeaf(ResultDeclaration r) { true }

}