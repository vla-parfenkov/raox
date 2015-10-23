package ru.bmstu.rk9.rao.ui.process.generate;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import ru.bmstu.rk9.rao.ui.process.Node;
import ru.bmstu.rk9.rao.ui.process.ProcessEditPart;

public class GeneratePart extends ProcessEditPart {

	@Override
	protected IFigure createFigure() {
		IFigure figure = new GenerateFigure();
		return figure;
	}

	@Override
	public List<Node> getModelChildren() {
		return ((Generate) getModel()).getChildren();
	}
}
