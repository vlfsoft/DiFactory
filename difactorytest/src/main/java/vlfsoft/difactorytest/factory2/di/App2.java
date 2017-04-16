package vlfsoft.difactorytest.factory2.di;

import vlfsoft.common.annotations.di.DiFactory;
import vlfsoft.difactorytest.factory2.Fct2ClsA;
import vlfsoft.difactorytest.factory2.Fct2SngClsA;

// To test "Annotated class %s has value of the generateDefaultFactoryImplementation, which is different from other annotated classes related to the same factory module",
// change to false
@DiFactory(false)
interface App2 {

    @DiFactory.Component
    @DiFactory.AbstractionTypeSuffix
    void get(Fct2ClsA a);

    @DiFactory.Component
    @DiFactory.AbstractionTypeSuffix
    @DiFactory.Singleton
    void get(Fct2SngClsA a);

}
