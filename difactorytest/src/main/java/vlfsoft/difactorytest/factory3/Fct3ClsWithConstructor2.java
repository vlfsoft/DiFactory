package vlfsoft.difactorytest.factory3;

import vlfsoft.common.annotations.di.DiFactory;

public class Fct3ClsWithConstructor2 extends Fct3ClsWithConstructor2A {
    @Override
    public String getName() {
        return "Impl";
    }

    private Fct3ClsWithConstructor1A mFct3ClsWithConstructor1;

    @DiFactory.ConstructorInject
    @DiFactory.AbstractionTypeSuffix
    public Fct3ClsWithConstructor2(Fct3ClsWithConstructor1A aFct3ClsWithConstructor1) {
        mFct3ClsWithConstructor1 = aFct3ClsWithConstructor1;
    }

}
