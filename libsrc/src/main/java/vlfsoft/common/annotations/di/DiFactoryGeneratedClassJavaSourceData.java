package vlfsoft.common.annotations.di;

import java.security.InvalidParameterException;

import javax.lang.model.element.Element;

import vlfsoft.common.annotations.processor.ProcessorException;
import vlfsoft.common.annotations.processor.ProcessorUtil;

class DiFactoryGeneratedClassJavaSourceData {

    String factoryPackageName;

    String factoryClassName;
    String factoryClassNameA;

    String factoryClassifiedNameA;
    String factoryClassifiedName;

    boolean generateDefaultFactoryImplementationModule;

    private int mHashCode;

    DiFactoryGeneratedClassJavaSourceData(Element aAnnotatedElement) throws ProcessorException {

        factoryPackageName = ProcessorUtil.getPackageElement(aAnnotatedElement).toString();

        Element interfaceContainerElement = aAnnotatedElement.getEnclosingElement();

        // Build factoryClassName based on Interface-container name to which the method relate to.
        factoryClassName = interfaceContainerElement.getSimpleName().toString() + "Factory";

        DiFactory diFactoryAnnotation = interfaceContainerElement.getAnnotation(DiFactory.class);
        generateDefaultFactoryImplementationModule = diFactoryAnnotation == null || diFactoryAnnotation.value();

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
