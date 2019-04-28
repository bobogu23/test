package com.gu.exercise.agent;

import javassist.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Arrays;

/**
 * @author:ben.gu
 * @Date:2019/4/26 5:57 PM
 */
public class MyClassFileTransformer implements ClassFileTransformer {
    final static String prefix = "\nlong startTime = System.currentTimeMillis();\n";

    final static String postfix = "\nlong endTime = System.currentTimeMillis();\n";

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        if ((className.startsWith("java") || className.startsWith("sun")) && !className.contains("java_core")) {
            return null;
        }
        String clazzName = className.replace("/", ".");
        try {
            CtClass ctClass = ClassPool.getDefault().get(clazzName);

            for (CtMethod m : ctClass.getDeclaredMethods()) {
                String methodName = m.getName();
                if (methodName.contains("main")) {
                    continue;
                }
                //老方法改名
                String methodNameNew = methodName + "$old";
                m.setName(methodNameNew);


                //新方法使用老方法名
                CtMethod newMethod = null;
                try {
                    newMethod = CtNewMethod.copy(m, methodName, ctClass, null);
                } catch (CannotCompileException e) {
                    System.err.println(e.getMessage());
                    continue;
                }

                StringBuilder sb = new StringBuilder();

                sb.append("{");
                sb.append("System.out.println(\"---enter method:" + clazzName + ",methodName:" + methodName
                        + "----\");");
                sb.append(prefix);
                sb.append(methodNameNew + "($$);\n");
                sb.append(postfix);
                sb.append("System.out.println(\"---exit method:" + clazzName + ",methodName:" + methodName
                        + ",cost:\"+(endTime-startTime)+\"ms\");");
                sb.append("}");

                try {
                    newMethod.setBody(sb.toString());
                    ctClass.addMethod(newMethod);

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }

            }
            File file = new File(".", ctClass.getSimpleName() + "11.class");
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                fileOutputStream.write(ctClass.toBytecode());
            } catch (Exception e) {
            }

            return ctClass.toBytecode();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return null;
    }
}
