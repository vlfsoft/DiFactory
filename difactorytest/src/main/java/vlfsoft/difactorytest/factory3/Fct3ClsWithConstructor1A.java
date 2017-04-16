package vlfsoft.difactorytest.factory3;

import vlfsoft.common.annotations.di.DiFactory;

public abstract class Fct3ClsWithConstructor1A {

    abstract public String getName();

    private Fct3ClsOnlyImpl mfct3C3sOnlyImpl;

    @DiFactory.ConstructorInject
    public Fct3ClsWithConstructor1A(Fct3ClsOnlyImpl afct3ClsOnlyImpl) {
        this.mfct3C3sOnlyImpl = afct3ClsOnlyImpl;
    }

}
