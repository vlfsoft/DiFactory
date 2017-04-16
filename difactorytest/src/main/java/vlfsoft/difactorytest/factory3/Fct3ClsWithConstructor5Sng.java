package vlfsoft.difactorytest.factory3;

import vlfsoft.common.annotations.di.DiFactory;
import vlfsoft.difactorytest.factory4.Fct4ClsOnlyImpl;

public class Fct3ClsWithConstructor5Sng extends Fct3ClsWithConstructor5SngA {
    @Override
    public String getName() {
        return getClass().getName();
    }

    private Fct3SngClsAzA mFct3SngClsAzA1;
    private Fct3SngClsAzA mFct3SngClsAzA2;
    private Fct4ClsOnlyImpl mFct4ClsOnlyImpl;

    @DiFactory.ConstructorInject
    @DiFactory.AbstractionTypeSuffix
    public Fct3ClsWithConstructor5Sng(@DiFactory.Singleton.NameSuffix("Ab") Fct3SngClsAzA aFct3SngClsAzA1,
                                      @DiFactory.Singleton.NameSuffix("Yz") Fct3SngClsAzA aFct3SngClsAzA2,
                                      Fct4ClsOnlyImpl aFct4ClsOnlyImpl) {
        mFct3SngClsAzA1 = aFct3SngClsAzA1;
        mFct3SngClsAzA2 = aFct3SngClsAzA2;
        mFct4ClsOnlyImpl = aFct4ClsOnlyImpl;
    }

}
