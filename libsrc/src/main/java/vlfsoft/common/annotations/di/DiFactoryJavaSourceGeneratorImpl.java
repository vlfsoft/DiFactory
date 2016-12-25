package vlfsoft.common.annotations.di;

import java.util.List;

import static vlfsoft.common.annotations.di.DiFactoryProcessor.INDENT;

public class DiFactoryJavaSourceGeneratorImpl extends DiFactoryJavaSourceGenerator {

    public DiFactoryJavaSourceGeneratorImpl(Class<DiFactory> aAnnotationClass, DiFactoryGeneratedClassJavaSourceData aDiFactoryGeneratedClassJavaSourceData, List<DiFactoryAnnotatedClassJavaSourceData> aAnnotatedClassesInFactory) {
        super(aAnnotationClass, aDiFactoryGeneratedClassJavaSourceData, aAnnotatedClassesInFactory);
    }

    protected String generateImportBlock(DiFactoryAnnotatedClassJavaSourceData aAnnotatedClassInFactory) {
        return super.generateImportBlock(aAnnotatedClassInFactory) +
                (aAnnotatedClassInFactory.ofClassNameReturnNull
                        || aAnnotatedClassInFactory.classifiedClassName.equals(aAnnotatedClassInFactory.classifiedClassNameA)
                        ? ""
                        : DiFactoryProcessor.generateSourceFileImportLine(aAnnotatedClassInFactory.classifiedClassName)
                );
    }

    protected String generateBeginClassBlock() {
        return
//        public class Factory1 extends Factory1A {
                String.format("public class %s extends %s {\n", mDiFactoryGeneratedClassJavaSourceData.factoryClassName, mDiFactoryGeneratedClassJavaSourceData.factoryClassNameA) +
                "";
    }

    protected String generateSingletonBlock(DiFactoryAnnotatedClassJavaSourceData aAnnotatedClassInFactory, String aSingletonInstanceName) {
        return "" +
//        @Override
                String.format("%s@Override\n", INDENT) +
//        protected Factory1ClassA injectSingltonFactory1ClassInstance() {
                String.format("%sprotected %s injectSinglton%sInstance%s() {\n", INDENT, aAnnotatedClassInFactory.classNameA, aAnnotatedClassInFactory.className, aSingletonInstanceName) +
//                return new Factory1Class();
                ( aAnnotatedClassInFactory.ofClassNameReturnNull
                        ? String.format("%s%sreturn null;\n", INDENT, INDENT)
                        : String.format("%s%sreturn new %s(%s);\n", INDENT, INDENT, aAnnotatedClassInFactory.className, generateConstructorParametersInjectionBlock(aAnnotatedClassInFactory))
                ) +

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
//        public Factory1ClassA ofFactory1Class() {
                String.format("%spublic %s of%s() {\n", INDENT, aAnnotatedClassInFactory.classNameA, aAnnotatedClassInFactory.className) +
//                return new Factory1Class();

                ( aAnnotatedClassInFactory.ofClassNameReturnNull
                        ? String.format("%s%sreturn null;\n", INDENT, INDENT)
                        : String.format("%s%sreturn new %s(%s);\n", INDENT, INDENT, aAnnotatedClassInFactory.className, generateConstructorParametersInjectionBlock(aAnnotatedClassInFactory))
                ) +
//        }
                String.format("%s}\n", INDENT) +
                "\n" +
                "";
    }


    private String generateConstructorParametersInjectionBlock(DiFactoryAnnotatedClassJavaSourceData aAnnotatedClassInFactory) {
//                return new Factory1Class(FactoryA.getInstance().ofClassName1(), FactoryA.getInstance().ofClassName2());
        // FactoryA.getInstance().ofClassName1xx(), FactoryA.getInstance().ofClassName2yy()
        String constructorParametersInjectionBlock = "";
        for (DiFactoryAnnotatedClassJavaSourceData.ConstructorData.ParameterData parameterData: aAnnotatedClassInFactory.constructorData.parameters) {
            if (!constructorParametersInjectionBlock.isEmpty()) constructorParametersInjectionBlock += ", ";
            constructorParametersInjectionBlock += String.format("of%s%s()", parameterData.type, parameterData.singletonInstanceName);
        }
        return constructorParametersInjectionBlock;
    }

}
