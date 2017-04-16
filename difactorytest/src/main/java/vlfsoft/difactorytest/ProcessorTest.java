package vlfsoft.difactorytest;

import vlfsoft.difactorytest.factory1.Fct1ClsA;
import vlfsoft.difactorytest.factory1.di.App1Factory;
import vlfsoft.difactorytest.factory1.di.App1FactoryA;
import vlfsoft.difactorytest.factory3.di.App3Factory;
import vlfsoft.difactorytest.factory3.di.App3FactoryA;
import vlfsoft.difactorytest.factory4.Fct4ClsOnlyImpl;
import vlfsoft.difactorytest.factory4.di.App4FactoryA;

public class ProcessorTest {

    public static void main(String[] args) {

        App1FactoryA.inject(new App1Factory());
        Fct1ClsA fct1ClsA = App1FactoryA.getInstance().ofFct1Cls();
        fct1ClsA.getName();

        App3FactoryA.inject(new App3Factory() {
            @Override
            public Fct4ClsOnlyImpl ofFct4ClsOnlyImpl() {
                return App4FactoryA.getInstance().ofFct4ClsOnlyImpl();
            }
        });

        System.out.println(App3FactoryA.getInstance().ofFct3ClsWithConstructor3Sng().getName());
        System.out.println(App3FactoryA.getInstance().ofFct3ClsWithConstructor4Sng().getName());

//        System.out.println(AppFactoryA.getInstance().ofFct3ClsWithConstructor3Sng().getName2());
        System.out.println(App3FactoryA.getInstance().ofFct3ClsWithConstructor4Sng().getName2());

    }

}
