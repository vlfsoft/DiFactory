package vlfsoft.common.annotations.di;

import java.util.List;

abstract class DiFactoryJavaSourceGenerator {

    Class<DiFactory> mAnnotationClass;
    DiFactoryGeneratedClassJavaSourceData mDiFactoryGeneratedClassJavaSourceData;
    List<DiFactoryAnnotatedClassJavaSourceData> mAnnotatedClassesInFactory;

    DiFactoryJavaSourceGenerator(Class<DiFactory> aAnnotationClass, DiFactoryGeneratedClassJavaSourceData aDiFactoryGeneratedClassJavaSourceData, List<DiFactoryAnnotatedClassJavaSourceData> aAnnotatedClassesInFactory) {
        mAnnotationClass = aAnnotationClass;
        mDiFactoryGeneratedClassJavaSourceData = aDiFactoryGeneratedClassJavaSourceData;
        mAnnotatedClassesInFactory = aAnnotatedClassesInFactory;
    }

    String generateSourceFile() {
        return "" +
                generatePackageBlock() +
                "\n" +
                generateImportBlock() +
                "\n" +
                generateBeginClassBlock() +
                "\n" +
                generateConstructorBlock() +
                "\n" +
                generateSingltonFactoryInstanceBlock() +
                "\n" +
                generateSourceBlock() +
//                "\n" +
                generateEndClassBlock() +
                "";
    }

    protected String generatePackageBlock() {
        return
//        package vlfsoft.difactorytest.factory1;
                String.format("package %s;\n", mDiFactoryGeneratedClassJavaSourceData.factoryPackageName) +
                        "";
    }

    protected String generateImportBlock() {
        String importBlock = "";
        for (DiFactoryAnnotatedClassJavaSourceData annotatedClassInFactory : mAnnotatedClassesInFactory) {
            importBlock += generateImportBlock(annotatedClassInFactory);
        }
        return importBlock;
    }

    protected String generateImportBlock(DiFactoryAnnotatedClassJavaSourceData annotatedClassInFactory) {
        return DiFactoryProcessor.generateSourceFileImportLine(annotatedClassInFactory.classifiedClassNameA);
    }

    abstract protected String generateBeginClassBlock();

    protected String generateConstructorBlock() {
        return
                "";
    }

    protected String generateSourceBlock() {
        String codeBlock = "";
        for (DiFactoryAnnotatedClassJavaSourceData annotatedClassInFactory : mAnnotatedClassesInFactory) {
            if (annotatedClassInFactory.diFactoryAnnotation.singleton())
                codeBlock += generateSingletonBlock(annotatedClassInFactory);
            else
                codeBlock += generateNonSingletonBlock(annotatedClassInFactory);
        }
        return codeBlock;
    }

    protected String generateSingltonFactoryInstanceBlock() {
        return
                "";
    }

    protected String generateSingletonBlock(DiFactoryAnnotatedClassJavaSourceData aAnnotatedClassInFactory) {

        String codeBlock = "";
        for (String singletonInstanceName : aAnnotatedClassInFactory.singletonInstanceNames) {
            codeBlock += generateSingletonBlock(aAnnotatedClassInFactory, singletonInstanceName);
            codeBlock += "\n";
        }
        return codeBlock;
    }

    abstract protected String generateSingletonBlock(DiFactoryAnnotatedClassJavaSourceData aAnnotatedClassInFactory, String aSingletonInstanceName);

    abstract protected String generateNonSingletonBlock(DiFactoryAnnotatedClassJavaSourceData aAnnotatedClassInFactory);

    protected String generateEndClassBlock() {
        return
                "}\n" +
                        "";
    }

}
