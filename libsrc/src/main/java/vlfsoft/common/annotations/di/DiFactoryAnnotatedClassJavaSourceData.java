package vlfsoft.common.annotations.di;

import java.util.ArrayList;

import javax.lang.model.element.Element;

import vlfsoft.common.annotations.design.patterns.etc.EtcShorthandPattern;
import vlfsoft.common.annotations.processor.ProcessorException;
import vlfsoft.common.annotations.processor.ProcessorUtil;

class DiFactoryAnnotatedClassJavaSourceData {

    String className;
    String implementationClassName;
    Boolean ofClassNameReturnNull;
    String classNameA;

    String classifiedClassName;
    String classifiedImplementationClassName;
    String classifiedClassNameA;

    boolean singleton;
    String[] singletonInstanceNameSuffixes;

    @EtcShorthandPattern
    String newClassNameMethod(String aConstructorParametersInjectionBlock) {
        return String.format("new %s(%s)", implementationClassName, aConstructorParametersInjectionBlock);
    }

    @EtcShorthandPattern
    private static String ofClassNameMethod(String aClassName) {
        return String.format("of%s()", aClassName);
    }

    @EtcShorthandPattern
    String ofClassNameMethod() {
        return ofClassNameMethod(className);
    }

    @EtcShorthandPattern
    private static String ofClassNameSuffixMethod(String aClassName, String aSingletonInstanceNameSuffix) {
        return String.format("of%s%s()", aClassName, aSingletonInstanceNameSuffix);
    }

    @EtcShorthandPattern
    String ofClassNameSuffixMethod(String aSingletonInstanceNameSuffix) {
        return ofClassNameSuffixMethod(className, aSingletonInstanceNameSuffix);
    }

    @EtcShorthandPattern
    String singltonClassNameInstanceField(String aSingletonInstanceNameSuffix) {
        return String.format("singlton%sInstance%s", className, aSingletonInstanceNameSuffix);
    }

    @EtcShorthandPattern
    static String injectSingltonClassNameInstanceMethod(String aClassName, String aSingletonInstanceNameSuffix) {
        return String.format("injectSinglton%sInstance%s()", aClassName, aSingletonInstanceNameSuffix);
    }

    @EtcShorthandPattern
    String injectSingltonClassNameInstanceMethod(String aSingletonInstanceNameSuffix) {
        return injectSingltonClassNameInstanceMethod(className, aSingletonInstanceNameSuffix);
    }

    DiFactoryAnnotatedClassJavaSourceData(DiFactoryProcessor aProcessor, Element aAnnotatedMethod) throws ProcessorException {

        DiFactory.AbstractionTypeSuffix diFactoryAbstractionTypeSuffixAnnotation = aAnnotatedMethod.getAnnotation(DiFactory.AbstractionTypeSuffix.class);
        String abstractionClassSuffix = diFactoryAbstractionTypeSuffixAnnotation != null ? diFactoryAbstractionTypeSuffixAnnotation.value() : "";

        DiFactory.ImplementationType diFactoryImplementationTypeAnnotation = aAnnotatedMethod.getAnnotation(DiFactory.ImplementationType.class);
        String implementationClassSuffix = diFactoryImplementationTypeAnnotation != null ? diFactoryImplementationTypeAnnotation.value() : "";

        Element annotatedClassElement = ProcessorUtil.getParameterDeclaredType(aAnnotatedMethod, 0).asElement();

/*-
         Replace abstractionClassSuffix and implementationClassSuffix with implementationClassName.
         if implementationClassName == "" (default value of implementationClassName), method ofClassName return null instead of return new implementationClassName()
         if implementationClassName != "", method ofClassName return new implementationClassName()
         Conclusion: Using implementationClassName instead of abstractionClassSuffix+implementationClassSuffix will do code
         less generic, because @DiFactory will contain specific names of implementation classes instead of more generic suffixes
         like "A", "Impl" and etc.
*/

        classNameA = annotatedClassElement.getSimpleName().toString();

        // if abstractionClassSuffix == "A" (default value of abstractionClassSuffix) and classNameA ends with "A" ->
        // !classNameA.endsWith(abstractionClassSuffix) == false -> no exception
        //
        // if abstractionClassSuffix == "" (f.e diFactoryAbstractionTypeSuffixAnnotation == null) (classNameA can end with any!)  ->
        // !classNameA.endsWith(abstractionClassSuffix) == false () -> no exception
        //
        // if abstractionClassSuffix == "A" (default value of abstractionClassSuffix) and classNameA doesn't end with "A" ->
        // !classNameA.endsWith(abstractionClassSuffix) == true -> exception
        if (!classNameA.endsWith(abstractionClassSuffix)) {
            aProcessor.printDiagnosticError(true, "classNameA = %s must end with \"%s\"", classNameA, abstractionClassSuffix);
        }

        // cut off abstractionClassSuffix
        className = classNameA.substring(0, classNameA.length() - abstractionClassSuffix.length());
//        className = implementationClassName != null
//                ? implementationClassName
//                : classNameA.substring(0, classNameA.length() - abstractionClassSuffix.length());

        ofClassNameReturnNull = diFactoryImplementationTypeAnnotation != null && diFactoryImplementationTypeAnnotation.value().isEmpty();

        implementationClassName =
                diFactoryImplementationTypeAnnotation != null && !diFactoryImplementationTypeAnnotation.value().isEmpty()
                        ? diFactoryImplementationTypeAnnotation.value() : className;

        classifiedClassName = annotatedClassElement.getEnclosingElement() + "." + className;
        classifiedImplementationClassName = annotatedClassElement.getEnclosingElement() + "." + implementationClassName;
        classifiedClassNameA = annotatedClassElement.getEnclosingElement() + "." + classNameA;

        DiFactory.Singleton diFactorySingletonAnnotation = aAnnotatedMethod.getAnnotation(DiFactory.Singleton.class);
        if (diFactorySingletonAnnotation != null) {
            singleton = true;
            singletonInstanceNameSuffixes = diFactorySingletonAnnotation.value();
        }
        if (diFactorySingletonAnnotation == null || singletonInstanceNameSuffixes.length == 0)
            singletonInstanceNameSuffixes = new String[]{""};

    }

    ConstructorData constructorData = new ConstructorData();

    static class ConstructorData {
        static class ParameterData {
            String className;
            boolean singlon = false;
            String singletonInstanceNameSuffix;

            @EtcShorthandPattern
            String injectParameter() {
                return
                        singlon
                                ? DiFactoryAnnotatedClassJavaSourceData.injectSingltonClassNameInstanceMethod(className, singletonInstanceNameSuffix) // there is a singlton
                                : DiFactoryAnnotatedClassJavaSourceData.ofClassNameMethod(className); // no singlton
            }

            ParameterData(String aClassName, boolean aSinglon, String aSingletonInstanceNameSuffix) {
                this.className = aClassName;
                this.singlon = aSinglon;
                this.singletonInstanceNameSuffix = aSingletonInstanceNameSuffix;
            }
        }

        ArrayList<ParameterData> parameters = new ArrayList<>();

        ConstructorData() {
        }

    }
}
