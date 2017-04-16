package vlfsoft.difactorytest.factory3;

import vlfsoft.common.annotations.di.DiFactory;

public class Fct3ClsWithConstructor3Sng extends Fct3ClsWithConstructor3SngA {
    @Override
    public String getName() {
        return getClass().getName();
    }

//    @Override
//    public String getName2() {
//        return mFct3ClsWithConstructor4SngA.getName();
//    }
//
//    private Fct3ClsWithConstructor4SngA mFct3ClsWithConstructor4SngA;
//
//    @DiFactory.ConstructorInject(abstractionClassSuffix = "A")
//    public Fct3ClsWithConstructor3Sng(Fct3ClsWithConstructor4SngA aFct3ClsWithConstructor4SngA) {
//        mFct3ClsWithConstructor4SngA = aFct3ClsWithConstructor4SngA;
//    }

}
