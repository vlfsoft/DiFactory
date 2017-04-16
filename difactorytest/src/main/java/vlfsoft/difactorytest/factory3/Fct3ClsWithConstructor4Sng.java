package vlfsoft.difactorytest.factory3;

import vlfsoft.common.annotations.di.DiFactory;

public class Fct3ClsWithConstructor4Sng extends Fct3ClsWithConstructor4SngA {
    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    public String getName2() {
        return mFct3ClsWithConstructor3SngA.getName();
    }

    private Fct3ClsWithConstructor3SngA mFct3ClsWithConstructor3SngA;

    @DiFactory.ConstructorInject
    @DiFactory.AbstractionTypeSuffix
    public Fct3ClsWithConstructor4Sng(Fct3ClsWithConstructor3SngA aFct3ClsWithConstructor3SngA) {
        mFct3ClsWithConstructor3SngA = aFct3ClsWithConstructor3SngA;
    }

}
