package vlfsoft.difactorytest;

import vlfsoft.difactorytest.di.factory1.Factory1;
import vlfsoft.difactorytest.di.factory1.Factory1A;
import vlfsoft.difactorytest.factory1.Fct1ClsA;

public class ProcessorTest {

    public static void main(String[] args) {

        Factory1A.inject(new Factory1());
        Fct1ClsA fct1ClsA = Factory1A.getInstance().ofFct1Cls();
        fct1ClsA.getName();

    }

}
