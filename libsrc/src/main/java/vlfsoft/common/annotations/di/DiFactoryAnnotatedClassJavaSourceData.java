package vlfsoft.common.annotations.di;

import java.util.ArrayList;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import vlfsoft.common.annotations.processor.ProcessorException;
import vlfsoft.common.annotations.processor.ProcessorUtil;

class DiFactoryAnnotatedClassJavaSourceData {

    DiFactory diFactoryAnnotation;

    String abstractionClassSuffix;
    String implementationClassSuffix;

    String className;
    Boolean ofClassNameReturnNull;
    String classNameA;

    String classifiedClassName;
    String classifiedClassNameA;

    String[] singletonInstanceNames;

    DiFactoryAnnotatedClassJavaSourceData(DiFactoryProcessor aProcessor, Element aAnnotatedElement, DiFactory aDiFactoryAnnotation) throws ProcessorException {

        diFactoryAnnotation = aDiFactoryAnnotation;

        abstractionClassSuffix = diFactoryAnnotation.abstractionClassSuffix();
        implementationClassSuffix = diFactoryAnnotation.implementationClassSuffix();

        Element annotatedClassElement = aAnnotatedElement;
        if (aAnnotatedElement.getKind() == ElementKind.METHOD) {
            annotatedClassElement = ProcessorUtil.getParameterDeclaredType(aAnnotatedElement, 0).asElement();
        }

        classNameA = annotatedClassElement.getSimpleName().toString();

        if (!classNameA.endsWith(abstractionClassSuffix)) {
            aProcessor.printDiagnosticError(true, "factoryClassNameA = %s must end with \"%s\"", classNameA, abstractionClassSuffix);
        }

        ofClassNameReturnNull = implementationClassSuffix.equals("-");
        // cut off abstractionClassSuffix and append implementationClassSuffix
        className = classNameA.substring(0, classNameA.length() - abstractionClassSuffix.length());

        classifiedClassName = annotatedClassElement.getEnclosingElement() + "." + className;
        classifiedClassNameA = annotatedClassElement.getEnclosingElement() + "." + classNameA;

        singletonInstanceNames = diFactoryAnnotation.singletonInstanceNames();
        if (singletonInstanceNames.length == 0) singletonInstanceNames = new String[]{""};

    }

    public ConstructorData constructorData = new ConstructorData();

    public static class ConstructorData {
        public static class ParameterData {
            String type;
            String singletonInstanceName;

            public ParameterData(String aType, String aSingletonInstanceName) {
                this.type = aType;
                this.singletonInstanceName = aSingletonInstanceName;
            }
        }

        ArrayList<ParameterData> parameters = new ArrayList<>();

        public ConstructorData() {
        }

    }
}
