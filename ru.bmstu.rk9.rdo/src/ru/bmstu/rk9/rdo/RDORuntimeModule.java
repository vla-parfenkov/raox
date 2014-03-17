/*
 * generated by Xtext
 */
package ru.bmstu.rk9.rdo;

import org.eclipse.xtext.linking.ILinkingDiagnosticMessageProvider;
import org.eclipse.xtext.naming.IQualifiedNameProvider;

import ru.bmstu.rk9.rdo.customizations.RDOLinkingDiagnosticMessageProvider;
import ru.bmstu.rk9.rdo.customizations.RDOQualifiedNameProvider;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */

public class RDORuntimeModule extends ru.bmstu.rk9.rdo.AbstractRDORuntimeModule {
	
	public Class<? extends ILinkingDiagnosticMessageProvider> bindILinkingDiagnosticMessageProvider() {
		return RDOLinkingDiagnosticMessageProvider.class;
	}
	
    @Override
    public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
        return RDOQualifiedNameProvider.class;
    }
}
