package com.gu.exercise.agent;

import java.lang.instrument.Instrumentation;

/**
 * @author:ben.gu
 * @Date:2019/4/26 5:30 PM
 */
public class MyAgent {

    public static void premain(String args,Instrumentation inst) {
        System.err.println("i am agent");
        inst.addTransformer(new MyClassFileTransformer());
    }

}
