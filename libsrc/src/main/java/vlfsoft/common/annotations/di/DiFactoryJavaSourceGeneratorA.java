package vlfsoft.common.annotations.di;

import java.util.List;

import static vlfsoft.common.annotations.di.DiFactoryProcessor.INDENT;

class DiFactoryJavaSourceGeneratorA extends DiFactoryJavaSourceGenerator {

    public DiFactoryJavaSourceGeneratorA(Class<DiFactory> aAnnotationClass, DiFactoryGeneratedClassJavaSourceData aDiFactoryGeneratedClassJavaSourceData, List<DiFactoryAnnotatedClassJavaSourceData> aAnnotatedClassesInFactory) {
        super(aAnnotationClass, aDiFactoryGeneratedClassJavaSourceData, aAnnotatedClassesInFactory);
    }

    protected String generateBeginClassBlock() {
        return
//        @CreationalPattern.AbstractFactory
                // String.format("@CreationalPattern.AbstractFactory\n") +
//        @CreationalPattern.Singleton
                // String.format("@CreationalPattern.Singleton\n") +
//        @DiFactory
//        abstract public class Factory1 {
                String.format("abstract public class %s {\n", mDiFactoryGeneratedClassJavaSourceData.factoryClassNameA) +
                        "";
    }

    protected String generateSingltonFactoryInstanceBlock() {
        return
//            @CreationalPattern.Singleton
                // String.format("%s@CreationalPattern.Singleton\n", INDENT) +
//            protected static Factory1 sInstance;
                String.format("%sprotected static %s sInstance;\n", INDENT, mDiFactoryGeneratedClassJavaSourceData.factoryClassNameA) +
                        "\n" +

//            @CreationalPattern.Singleton
                        // String.format("%s@CreationalPattern.Singleton\n", INDENT) +
//            public static Factory1 getInstance() {
                        String.format("%spublic static %s getInstance() {\n", INDENT, mDiFactoryGeneratedClassJavaSourceData.factoryClassNameA) +
//                return sInstance;
                        String.format("%s%sreturn sInstance;\n", INDENT, INDENT) +
//            }
                        String.format("%s}\n", INDENT) +
                        "\n" +

//            @CreationalPattern.Singleton
                        // String.format("%s@CreationalPattern.Singleton\n", INDENT) +
//            public static void inject(Factory1A aInstance) {
                        String.format("%spublic static void inject(%s aInstance) {\n", INDENT, mDiFactoryGeneratedClassJavaSourceData.factoryClassNameA) +
//                sInstance = aInstance;
                        String.format("%s%ssInstance = aInstance;\n", INDENT, INDENT) +
                        "\n" +
                        generateSingltonFactoryInstanceBlockAssignment() +
//            }
                        String.format("%s}\n", INDENT) +
                "";
    }

    private String generateSingltonFactoryInstanceBlockAssignment() {
        String codeBlock = "";
        for (DiFactoryAnnotatedClassJavaSourceData annotatedClassInFactory : mAnnotatedClassesInFactory) {
            if (annotatedClassInFactory.diFactoryAnnotation.singleton()) {
                codeBlock += generateSingltonFactoryInstanceBlockAssignment(annotatedClassInFactory);
            }
        }
        return codeBlock + "\n";
    }

    private String generateSingltonFactoryInstanceBlockAssignment(DiFactoryAnnotatedClassJavaSourceData aAnnotatedClassInFactory) {
        String codeBlock = "";
        for (String singletonInstanceName : aAnnotatedClassInFactory.singletonInstanceNames) {
                codeBlock += generateSingltonFactoryInstanceBlockAssignment(aAnnotatedClassInFactory, singletonInstanceName);
        }
        return codeBlock;
    }

    private String generateSingltonFactoryInstanceBlockAssignment(DiFactoryAnnotatedClassJavaSourceData aAnnotatedClassInFactory, String aSingletonInstanceName) {
        return
//                singltonFactory1ClassInstance = getInstance().injectSingltonFactory1ClassInstance();
                String.format("%s%ssinglton%sInstance%s = getInstance().injectSinglton%sInstance%s();\n", INDENT, INDENT,
                        aAnnotatedClassInFactory.className, aSingletonInstanceName, aAnnotatedClassInFactory.className, aSingletonInstanceName) +
                "";
    }

    protected String generateSingletonBlock(DiFactoryAnnotatedClassJavaSourceData aAnnotatedClassInFactory, String aSingletonInstanceName) {
        return "" +
//        @CreationalPattern.Singleton
                // String.format("%s@CreationalPattern.Singleton\n", INDENT) +
//        private static Factory1ClassA singltonFactory1ClassInstance;
                String.format("%sprivate static %s singlton%sInstance%s;\n", INDENT, aAnnotatedClassInFactory.classNameA, aAnnotatedClassInFactory.className, aSingletonInstanceName) +
//                "\n" +
                // String.format("%s@CreationalPattern.Singleton\n", INDENT) +
//        abstract protected Factory1ClassA injectSingltonFactory1ClassInstance();
                String.format("%sabstract protected %s injectSinglton%sInstance%s();\n", INDENT, aAnnotatedClassInFactory.classNameA, aAnnotatedClassInFactory.className, aSingletonInstanceName) +
//                "\n" +
//        @CreationalPattern.AbstractFactory
                // String.format("%s@CreationalPattern.AbstractFactory\n", INDENT) +
//        @CreationalPattern.FactoryMethod
                // String.format("%s@CreationalPattern.FactoryMethod\n", INDENT) +
//        public Factory1ClassA ofFactory1Class() {
                String.format("%spublic %s of%s%s() {\n", INDENT, aAnnotatedClassInFactory.classNameA, aAnnotatedClassInFactory.className, aSingletonInstanceName) +
//            return singltonFactory1ClassInstance;
                String.format("%s%sreturn singlton%sInstance%s;\n", INDENT, INDENT, aAnnotatedClassInFactory.className, aSingletonInstanceName) +
//        }
                String.format("%s}\n", INDENT) +
                "";
    }

    protected String generateNonSingletonBlock(DiFactoryAnnotatedClassJavaSourceData aAnnotatedClassInFactory) {
        return "" +
//        @CreationalPattern.AbstractFactory
                // String.format("%s@CreationalPattern.AbstractFactory\n", INDENT) +
//        @CreationalPattern.FactoryMethod
                // String.format("%s@CreationalPattern.FactoryMethod\n", INDENT) +
//        abstract public Factory1ClassA ofFactory1Class();
                String.format("%sabstract public %s of%s();\n", INDENT, aAnnotatedClassInFactory.classNameA, aAnnotatedClassInFactory.className) +
                "\n" +
                "";
    }

}
