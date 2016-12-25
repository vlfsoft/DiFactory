package vlfsoft.common.annotations.di;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import vlfsoft.common.annotations.processor.BaseProcessor;
import vlfsoft.common.annotations.processor.ProcessorException;
import vlfsoft.common.annotations.processor.ProcessorUtil;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({
        "vlfsoft.common.annotations.di.DiFactory"
})

public class DiFactoryProcessor extends BaseProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        super.process(annotations, roundEnvironment);

        try {
            getClassesAnnotatedWith()
//                    .printDiagnosticMessage("mAnnotatedElements.size() = %d ", mAnnotatedElements.size())
                    .getAnnotatedElementsGrouppedByFactory()
//                    .printDiagnosticMessage("mAnnotatedClassesGroupedByFactory.size() = %d ", mAnnotatedClassesGroupedByFactory.size())
                    .processAnnotatedClassesGrouppedByFactory();

        } catch (ProcessorException e) {
            // stop processing;
        } catch (IOException e) {
            // Note: calling e.printStackTrace() will print IO errors
            // that occur from the file already existing after its first run, this is normal
        }

        return true;
    }

    final static String INDENT = "    ";
    private Class<DiFactory> mAnnotationClass = DiFactory.class;
    private Set<? extends javax.lang.model.element.Element> mAnnotatedElements = null;
    private Map<DiFactoryGeneratedClassJavaSourceData, List<DiFactoryAnnotatedClassJavaSourceData>> mAnnotatedClassesGroupedByFactory = new HashMap<>();

    private DiFactoryProcessor getClassesAnnotatedWith() {
        mAnnotatedElements = roundEnvironment.getElementsAnnotatedWith(mAnnotationClass);
        return this;
    }

    private DiFactoryProcessor getAnnotatedElementsGrouppedByFactory() throws IOException, ProcessorException {

        Map<DiFactoryGeneratedClassJavaSourceData, Boolean> generateDefaultFactoryImplementationModule = new HashMap<>();
        Map<String, DiFactoryAnnotatedClassJavaSourceData> annotatedClasses = new HashMap<>();

        // for each Class annotated with ...
        for (Element annotatedClassElement : mAnnotatedElements) {

            DiFactory diFactoryAnnotation = annotatedClassElement.getAnnotation(mAnnotationClass);

            DiFactoryGeneratedClassJavaSourceData diFactoryGeneratedClassJavaSourceData =
                    new DiFactoryGeneratedClassJavaSourceData(this, annotatedClassElement, diFactoryAnnotation);

            List<DiFactoryAnnotatedClassJavaSourceData> annotatedClassesInFactory =
                    mAnnotatedClassesGroupedByFactory.get(diFactoryGeneratedClassJavaSourceData);
            // First occurence of new Factory
            if (annotatedClassesInFactory == null) {
                // First occurence of diFactoryAnnotation for the factory with factoryPackageName.factoryClassName
                annotatedClassesInFactory = new ArrayList<>();
                mAnnotatedClassesGroupedByFactory.put(diFactoryGeneratedClassJavaSourceData, annotatedClassesInFactory);

                diFactoryGeneratedClassJavaSourceData.generateDefaultFactoryImplementationModule =
                        diFactoryAnnotation.generateDefaultFactoryImplementation();
                generateDefaultFactoryImplementationModule.put(diFactoryGeneratedClassJavaSourceData,
                        diFactoryAnnotation.generateDefaultFactoryImplementation());

            } else {
                /*
                * if generateDefaultFactoryImplementation = true, default implementation module is generated for annotated class.
                * PRB: ambiguous parameter: what to do if annotated classes related to the same factory module
                * will have different values of this parameter ?
                * Solution 1: Implementation module is generated by developer.
                * Fortunately, AS will help to generate bodies for constructor and methods to be implemented.
                * Solution 2: Raise error and stop generation.
                */
                if (generateDefaultFactoryImplementationModule.get(diFactoryGeneratedClassJavaSourceData) != diFactoryAnnotation.generateDefaultFactoryImplementation()) {
                    printDiagnosticError(true,
                            "Annotated class %s has value of the generateDefaultFactoryImplementation, which is different from other annotated classes related to the same factory module",
                            annotatedClassElement.getEnclosingElement() + "." + annotatedClassElement.getSimpleName().toString());
                }
            }

            DiFactoryAnnotatedClassJavaSourceData diFactoryAnnotatedClassJavaSourceData =
                    new DiFactoryAnnotatedClassJavaSourceData(this, annotatedClassElement, diFactoryAnnotation);
            annotatedClassesInFactory.add(diFactoryAnnotatedClassJavaSourceData);
            annotatedClasses.put(diFactoryAnnotatedClassJavaSourceData.classifiedClassName, diFactoryAnnotatedClassJavaSourceData);

        }
        getConstructorsAnnotatedWithInject(annotatedClasses);
        return this;

    }

    public void getConstructorsAnnotatedWithInject(Map<String, DiFactoryAnnotatedClassJavaSourceData> aAnnotatedClasses) throws ProcessorException {
        Set<? extends Element> annotatedConstructors = roundEnvironment.getElementsAnnotatedWith(DiFactory.Inject.class);
        // for each Class annotated with ...
        for (Element annotatedConstructor : annotatedConstructors) {
            String classifiedClassName = annotatedConstructor.getEnclosingElement().toString();
            DiFactoryAnnotatedClassJavaSourceData diFactoryAnnotatedClassJavaSourceData = aAnnotatedClasses.get(classifiedClassName);
            if (diFactoryAnnotatedClassJavaSourceData == null) {
                printDiagnosticError(true, "Constructor of the class %s is annotated with @DiFactory.Inject. The class must be annotated with @DiFactory",
                        classifiedClassName);
            } else {
                List<? extends TypeMirror> parameters = ProcessorUtil.getParameterTypes(annotatedConstructor);
                int i = 0;
                for (TypeMirror parameter : parameters) {
                    DeclaredType declaredType = (DeclaredType) parameter;

                    DiFactory.Inject diFactoryInjectAnnotation = annotatedConstructor.getAnnotation(DiFactory.Inject.class);

                    String singletonInstanceName =
                            i < diFactoryInjectAnnotation.singletonInstanceNames().length
                                    ? diFactoryInjectAnnotation.singletonInstanceNames()[i++]
                                    : "";

                    diFactoryAnnotatedClassJavaSourceData.constructorData.parameters.add(
                            new DiFactoryAnnotatedClassJavaSourceData.ConstructorData.ParameterData(
                                    declaredType.asElement().getSimpleName().toString(),
                                    singletonInstanceName));
                }
            }
        }
    }

    private DiFactoryProcessor processAnnotatedClassesGrouppedByFactory() throws IOException {
        for (Map.Entry<DiFactoryGeneratedClassJavaSourceData, List<DiFactoryAnnotatedClassJavaSourceData>> entry : mAnnotatedClassesGroupedByFactory.entrySet()) {
//            printDiagnosticMessage("Map.Entry<DiFactoryGeneratedClassJavaSourceData, List<DiFactoryAnnotatedClassJavaSourceData>> = \"%s\" \"%d\" ", entry.getKey().factoryClassifiedName, entry.getValue().size());
            processAnnotatedClassesInFactory(entry.getKey(), entry.getValue());
        }
        return this;
    }

    private void processAnnotatedClassesInFactory(DiFactoryGeneratedClassJavaSourceData aDiFactoryGeneratedClassJavaSourceData, List<DiFactoryAnnotatedClassJavaSourceData> aAnnotatedClassesInFactory) throws IOException {
        writeWithWriter(aDiFactoryGeneratedClassJavaSourceData.factoryClassifiedNameA, new DiFactoryJavaSourceGeneratorA(mAnnotationClass, aDiFactoryGeneratedClassJavaSourceData, aAnnotatedClassesInFactory).generateSourceFile());
        if (aDiFactoryGeneratedClassJavaSourceData.generateDefaultFactoryImplementationModule)
            writeWithWriter(aDiFactoryGeneratedClassJavaSourceData.factoryClassifiedName, new DiFactoryJavaSourceGeneratorImpl(mAnnotationClass, aDiFactoryGeneratedClassJavaSourceData, aAnnotatedClassesInFactory).generateSourceFile());
    }

    static String generateSourceFileImportLine(String aImportClassName) {
        return String.format("import %s;\n", aImportClassName);
    }

    private void writeWithWriter(String aJavaFileSourceFullName, String aJavaFileSource) throws IOException {
        Writer writer = processingEnv.getFiler().createSourceFile(aJavaFileSourceFullName).openWriter();
        try {
            writer.write(aJavaFileSource);
            writer.flush();
//            writer.close();
        } finally {
            if (writer != null) writer.close();
        }
    }

}