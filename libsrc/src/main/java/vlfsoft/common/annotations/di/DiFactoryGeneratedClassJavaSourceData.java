package vlfsoft.common.annotations.di;

import java.security.InvalidParameterException;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import vlfsoft.common.annotations.processor.ProcessorException;
import vlfsoft.common.annotations.processor.ProcessorUtil;

class DiFactoryGeneratedClassJavaSourceData {
    String factoryPackageName;

    String factoryClassName;
    String factoryClassNameA;

    String factoryClassifiedNameA;
    String factoryClassifiedName;

    boolean generateDefaultFactoryImplementationModule = true;

    private int mHashCode;

    DiFactoryGeneratedClassJavaSourceData(DiFactoryProcessor aProcessor, Element aAnnotatedElement, DiFactory aDiFactoryAnnotation) throws ProcessorException {

        String defaultPackageName = ProcessorUtil.getPackageElement(aAnnotatedElement).toString();

        factoryPackageName = aDiFactoryAnnotation.factoryPackageName().isEmpty() ? defaultPackageName : aDiFactoryAnnotation.factoryPackageName();

        factoryClassName = aDiFactoryAnnotation.factoryClassName();

        if (factoryClassName.isEmpty()) {
            if (aAnnotatedElement.getKind() == ElementKind.METHOD) {
                /* Build factoryClassName based on Interface name to which the method relate to. */
                factoryClassName = aAnnotatedElement.getEnclosingElement().getSimpleName().toString() + "Factory";
            }else if (aAnnotatedElement.getKind() == ElementKind.CLASS || aAnnotatedElement.getKind() == ElementKind.INTERFACE) {
                aProcessor.printDiagnosticError(true, "factoryClassName for annotated class %s can't be empty", aAnnotatedElement.toString());
            }
        }

        factoryClassifiedName = factoryPackageName + "." + factoryClassName;
        mHashCode = factoryClassifiedName.hashCode();

        factoryClassNameA = factoryClassName + "A";
        factoryClassifiedNameA = factoryClassifiedName + "A";

    }

    @Override
    public int hashCode() {
        return mHashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DiFactoryGeneratedClassJavaSourceData) {
            return factoryClassifiedName.equals(((DiFactoryGeneratedClassJavaSourceData) o).factoryClassifiedName);
        } else {
            throw new InvalidParameterException("o is not instanceOf " + this.getClass().getName());
        }
    }
}
