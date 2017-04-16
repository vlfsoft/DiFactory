package vlfsoft.common.annotations.di;

import java.util.List;

import static vlfsoft.common.annotations.di.DiFactoryProcessor.INDENT;

class DiFactoryJavaSourceGeneratorA extends DiFactoryJavaSourceGenerator {

    DiFactoryJavaSourceGeneratorA(DiFactoryGeneratedClassJavaSourceData aDiFactoryGeneratedClassJavaSourceData, List<DiFactoryAnnotatedClassJavaSourceData> aAnnotatedClassesInFactory) {
        super(aDiFactoryGeneratedClassJavaSourceData, aAnnotatedClassesInFactory);
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
            if (annotatedClassInFactory.singleton) {
                codeBlock += generateSingltonFactoryInstanceBlockAssignment(annotatedClassInFactory);
            }
        }
        return codeBlock + "\n";
    }

    private String generateSingltonFactoryInstanceBlockAssignment(DiFactoryAnnotatedClassJavaSourceData aAnnotatedClassInFactory) {
        String codeBlock = "";
        for (String singletonInstanceNameSuffix : aAnnotatedClassInFactory.singletonInstanceNameSuffixes) {
                codeBlock += generateSingltonFactoryInstanceBlockAssignment(aAnnotatedClassInFactory, singletonInstanceNameSuffix);
        }
        return codeBlock;
    }

    private String generateSingltonFactoryInstanceBlockAssignment(DiFactoryAnnotatedClassJavaSourceData aAnnotatedClassInFactory, String aSingletonInstanceNameSuffix) {
        return
//                getInstance().injectSingltonFactory1ClassInstance();
                String.format("%s%sgetInstance().injectSinglton%sInstance%s();\n", INDENT, INDENT,
                        aAnnotatedClassInFactory.className, aSingletonInstanceNameSuffix) +
                        "";
    }

    protected String generateSingletonBlock(DiFactoryAnnotatedClassJavaSourceData aAnnotatedClassInFactory, String aSingletonInstanceNameSuffix) {
        return "" +
//        @CreationalPattern.Singleton
                // String.format("%s@CreationalPattern.Singleton\n", INDENT) +
//        protected static Factory1ClassA singltonFactory1ClassInstance;
                String.format("%sprotected static %s %s;\n", INDENT, aAnnotatedClassInFactory.classNameA, aAnnotatedClassInFactory.singltonClassNameInstanceField(aSingletonInstanceNameSuffix)) +
//                "\n" +
                // String.format("%s@CreationalPattern.Singleton\n", INDENT) +
//        abstract protected Factory1ClassA injectSingltonFactory1ClassInstance();
                String.format("%sabstract protected %s %s;\n", INDENT, aAnnotatedClassInFactory.classNameA, aAnnotatedClassInFactory.injectSingltonClassNameInstanceMethod(aSingletonInstanceNameSuffix)) +
//                "\n" +
//        @CreationalPattern.AbstractFactory
                // String.format("%s@CreationalPattern.AbstractFactory\n", INDENT) +
//        @CreationalPattern.FactoryMethod
                // String.format("%s@CreationalPattern.FactoryMethod\n", INDENT) +
//        public Factory1ClassA ofFactory1Class() {
                String.format("%spublic %s %s {\n", INDENT, aAnnotatedClassInFactory.classNameA, aAnnotatedClassInFactory.ofClassNameSuffixMethod(aSingletonInstanceNameSuffix)) +
//            return singltonFactory1ClassInstance;
                String.format("%s%sreturn %s;\n", INDENT, INDENT, aAnnotatedClassInFactory.singltonClassNameInstanceField(aSingletonInstanceNameSuffix)) +
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
                String.format("%sabstract public %s %s;\n", INDENT, aAnnotatedClassInFactory.classNameA, aAnnotatedClassInFactory.ofClassNameMethod()) +
                "\n" +
                "";
    }

}
