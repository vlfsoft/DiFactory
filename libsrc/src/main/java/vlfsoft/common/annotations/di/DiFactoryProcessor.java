package vlfsoft.common.annotations.di;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.processing.FilerException;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.tools.Diagnostic;

import vlfsoft.common.annotations.processor.BaseProcessor;
import vlfsoft.common.annotations.processor.ProcessorException;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({
        "vlfsoft.common.annotations.di.DiFactory.Component"
})

public class DiFactoryProcessor extends BaseProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        super.process(annotations, roundEnvironment);

        try {
            getClassesAnnotatedWith()
                    .getAnnotatedClassesGroupedByFactory()
                    .processAnnotatedClassesGrouppedByFactory();
        } catch (FilerException e) {
            // Ignore exceptions like:
            // Error:javax.annotation.processing.FilerException: Attempt to recreate a file for type vlfsoft.difactorytest.factory4.di.App4FactoryA
        } catch (Exception e) {
            /* TODO: Get stacktrace to String */
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.toString());
/*
        } catch (ProcessorException e) {
            // stop processing;
        } catch (IOException e) {
            // Note: calling e.printStackTrace() will print IO errors
            // that occur from the file already existing after its first run, this is normal
*/
        }

        return true;
    }

    final static String INDENT = "    ";
    private Set<? extends javax.lang.model.element.Element> mAnnotatedElements = null;
    private Map<DiFactoryGeneratedClassJavaSourceData, List<DiFactoryAnnotatedClassJavaSourceData>> mAnnotatedClassesGroupedByFactory = new HashMap<>();

    private DiFactoryProcessor getClassesAnnotatedWith() {
        mAnnotatedElements = roundEnvironment.getElementsAnnotatedWith(DiFactory.Component.class);
        // printDiagnosticMessage("mAnnotatedElements.size() = %d ", mAnnotatedElements.size());
        return this;
    }

    private DiFactoryProcessor getAnnotatedClassesGroupedByFactory() throws IOException, ProcessorException {

        Map<String, DiFactoryAnnotatedClassJavaSourceData> allAnnotatedClasses = new HashMap<>();

        // for each Class or method in the special interface-container annotated with @DiFactory
        for (Element annotatedMethodElement : mAnnotatedElements) {

            DiFactoryGeneratedClassJavaSourceData diFactoryGeneratedClassJavaSourceData =
                    new DiFactoryGeneratedClassJavaSourceData(annotatedMethodElement);

            List<DiFactoryAnnotatedClassJavaSourceData> annotatedClassesInFactory =
                    mAnnotatedClassesGroupedByFactory.get(diFactoryGeneratedClassJavaSourceData);

            // First occurence of new Factory
            if (annotatedClassesInFactory == null) {
                // First occurence of diFactoryComponentAnnotation for the factory with factoryPackageName.factoryClassName
                annotatedClassesInFactory = new ArrayList<>();
                mAnnotatedClassesGroupedByFactory.put(diFactoryGeneratedClassJavaSourceData, annotatedClassesInFactory);
            }

            DiFactoryAnnotatedClassJavaSourceData diFactoryAnnotatedClassJavaSourceData =
                    new DiFactoryAnnotatedClassJavaSourceData(this, annotatedMethodElement);

            annotatedClassesInFactory.add(diFactoryAnnotatedClassJavaSourceData);
            allAnnotatedClasses.put(diFactoryAnnotatedClassJavaSourceData.classifiedClassNameA, diFactoryAnnotatedClassJavaSourceData);

        }

        fillWithDataOfConstructorsAnnotatedWithInject(allAnnotatedClasses);
        return this;

    }

    /**
     * Fill elements in the aAllAnnotatedClasses with data about parameters of constructor (constructorData.parameters),
     * if the constructor of the AnnotatedClass is annotated with DiFactory.ConstructorInject
     *
     * @param aAllAnnotatedClasses - the list with all classes annotated with @DiFactory
     * @throws ProcessorException
     */
    private void fillWithDataOfConstructorsAnnotatedWithInject(Map<String, DiFactoryAnnotatedClassJavaSourceData> aAllAnnotatedClasses) throws ProcessorException {
        /**
         *  the list with all constructors annotated with @DiFactory
         */
        Set<? extends Element> allAnnotatedConstructors = roundEnvironment.getElementsAnnotatedWith(DiFactory.ConstructorInject.class);

        /**
         * for each constructor annotated with @DiFactory.ConstructorInject
         * try to find element (diFactoryAnnotatedClassJavaSourceData) in the list with all classes annotated with @DiFactory
         * if don't find, raise printDiagnosticError and stop processing
         * if find, fill diFactoryAnnotatedClassJavaSourceData.constructorData.parameters with data about parameters of constructor (constructorData.parameters).
         */
        for (Element annotatedConstructor : allAnnotatedConstructors) {

            // Try to find implementation class (without abstractionClassSuffix)
            String classifiedClassNameEnclosingConstructor = annotatedConstructor.getEnclosingElement().toString();
            DiFactoryAnnotatedClassJavaSourceData diFactoryAnnotatedClassJavaSourceData =
                    aAllAnnotatedClasses.get(classifiedClassNameEnclosingConstructor);

            // if don't find, implementation class (without abstractionClassSuffix)
            if (diFactoryAnnotatedClassJavaSourceData == null) {
                DiFactory.AbstractionTypeSuffix diFactoryAbstractionTypeSuffixAnnotation = annotatedConstructor.getAnnotation(DiFactory.AbstractionTypeSuffix.class);
                classifiedClassNameEnclosingConstructor += diFactoryAbstractionTypeSuffixAnnotation != null ? diFactoryAbstractionTypeSuffixAnnotation.value() : "";
                // Try to find abstraction type(interface/class) (with abstractionClassSuffix)
                diFactoryAnnotatedClassJavaSourceData =
                        aAllAnnotatedClasses.get(classifiedClassNameEnclosingConstructor);
            }

            // if don't find, raise printDiagnosticError and stop processing
            if (diFactoryAnnotatedClassJavaSourceData == null) {
                printDiagnosticError(true, "Constructor of the class %s is annotated with @DiFactory.ConstructorInject." +
                                " The class must be annotated with @DiFactory" +
                                " or a method with parameter of the class in the special interface-container must be annotated with @DiFactory",
                        classifiedClassNameEnclosingConstructor);
            } else {
                // if find
                List<? extends VariableElement> variableElements = ((ExecutableElement) annotatedConstructor).getParameters();

                for (VariableElement variableElement : variableElements) {
                    DeclaredType declaredType = (DeclaredType) variableElement.asType();

                    DiFactory.Singleton.NameSuffix diFactorySingletonNameSuffixAnnotation =
                            variableElement.getAnnotation(DiFactory.Singleton.NameSuffix.class);

                    // If constructor contains several instances of the same singlton,
                    // extract from diFactorySingletonAnnotation names of these instances and use them to inject parameters of constructor
                    String singletonInstanceNameSuffix =
                            diFactorySingletonNameSuffixAnnotation != null
                                    ? diFactorySingletonNameSuffixAnnotation.value()
                                    : "";

                    String packageName = declaredType.asElement().getEnclosingElement().toString();

                    // Try to find class annotated with @DiFactory.Component which name == Type of parameter of constructor annotated with @DiFactory.ConstructorInject
                    String classifiedClassNameOfConstructorParameter = packageName + "." + declaredType.asElement().getSimpleName().toString();
                    DiFactoryAnnotatedClassJavaSourceData diFactoryAnnotatedClassJavaSourceDataForParameter =
                            aAllAnnotatedClasses.get(classifiedClassNameOfConstructorParameter);

                    if (diFactoryAnnotatedClassJavaSourceDataForParameter == null) {
                        printDiagnosticError(true, "Constructor of the class %s is annotated with @DiFactory.ConstructorInject." +
                                        " The type %s of parameter must be annotated with @DiFactory",
                                classifiedClassNameEnclosingConstructor, classifiedClassNameOfConstructorParameter);
                        return;
                    }
                    diFactoryAnnotatedClassJavaSourceData.constructorData.parameters.add(
                            new DiFactoryAnnotatedClassJavaSourceData.ConstructorData.ParameterData(
                                    diFactoryAnnotatedClassJavaSourceDataForParameter.className,
                                    diFactoryAnnotatedClassJavaSourceDataForParameter.singleton,
                                    singletonInstanceNameSuffix));
                }
            }
        }
    }

    private
    @Nonnull
    DiFactoryProcessor processAnnotatedClassesGrouppedByFactory() throws IOException {
        for (Map.Entry<DiFactoryGeneratedClassJavaSourceData, List<DiFactoryAnnotatedClassJavaSourceData>> entry : mAnnotatedClassesGroupedByFactory.entrySet()) {
            // printDiagnosticMessage("Map.Entry<DiFactoryGeneratedClassJavaSourceData, List<DiFactoryAnnotatedClassJavaSourceData>> = \"%s\" \"%d\" ", entry.getKey().factoryClassifiedName, entry.getValue().size());
            processAnnotatedClassesInFactory(entry.getKey(), entry.getValue());
        }
        return this;
    }

    private void processAnnotatedClassesInFactory(DiFactoryGeneratedClassJavaSourceData aDiFactoryGeneratedClassJavaSourceData, List<DiFactoryAnnotatedClassJavaSourceData> aAnnotatedClassesInFactory) throws IOException {
        writeWithWriter(aDiFactoryGeneratedClassJavaSourceData.factoryClassifiedNameA, new DiFactoryJavaSourceGeneratorA(aDiFactoryGeneratedClassJavaSourceData, aAnnotatedClassesInFactory).generateSourceFile());
        if (aDiFactoryGeneratedClassJavaSourceData.generateDefaultFactoryImplementationModule)
            writeWithWriter(aDiFactoryGeneratedClassJavaSourceData.factoryClassifiedName, new DiFactoryJavaSourceGeneratorImpl(aDiFactoryGeneratedClassJavaSourceData, aAnnotatedClassesInFactory).generateSourceFile());
    }

    static String generateSourceFileImportLine(String aImportClassName) {
        return String.format("import %s;\n", aImportClassName);
    }

    private void writeWithWriter(String aJavaFileSourceFullName, String aJavaFileSource) throws IOException {
        try (Writer writer = processingEnv.getFiler().createSourceFile(aJavaFileSourceFullName).openWriter()) {
            writer.write(aJavaFileSource);
            writer.flush();
        }
    }

}
