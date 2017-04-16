package vlfsoft.common.annotations.di;

import java.util.List;

abstract class DiFactoryJavaSourceGenerator {

    DiFactoryGeneratedClassJavaSourceData mDiFactoryGeneratedClassJavaSourceData;
    List<DiFactoryAnnotatedClassJavaSourceData> mAnnotatedClassesInFactory;

    DiFactoryJavaSourceGenerator(DiFactoryGeneratedClassJavaSourceData aDiFactoryGeneratedClassJavaSourceData, List<DiFactoryAnnotatedClassJavaSourceData> aAnnotatedClassesInFactory) {
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

    private String generatePackageBlock() {
        return
//        package vlfsoft.difactorytest.factory1;
                String.format("package %s;\n", mDiFactoryGeneratedClassJavaSourceData.factoryPackageName) +
                        "";
    }

    private String generateImportBlock() {
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

    private String generateConstructorBlock() {
        return
                "";
    }

    private String generateSourceBlock() {
        String codeBlock = "";
        for (DiFactoryAnnotatedClassJavaSourceData annotatedClassInFactory : mAnnotatedClassesInFactory) {
            if (annotatedClassInFactory.singleton)
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

    private String generateSingletonBlock(DiFactoryAnnotatedClassJavaSourceData aAnnotatedClassInFactory) {

        String codeBlock = "";
        for (String singletonInstanceName : aAnnotatedClassInFactory.singletonInstanceNameSuffixes) {
            codeBlock += generateSingletonBlock(aAnnotatedClassInFactory, singletonInstanceName);
            codeBlock += "\n";
        }
        return codeBlock;
    }

    abstract protected String generateSingletonBlock(DiFactoryAnnotatedClassJavaSourceData aAnnotatedClassInFactory, String aSingletonInstanceName);

    abstract protected String generateNonSingletonBlock(DiFactoryAnnotatedClassJavaSourceData aAnnotatedClassInFactory);

    private String generateEndClassBlock() {
        return
                "}\n" +
                        "";
    }

}
