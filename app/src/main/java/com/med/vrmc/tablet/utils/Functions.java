package com.med.vrmc.tablet.utils;

import java.util.HashMap;

/**
 * Created by raytine on 2018/1/8.
 */

public class Functions {
    //带参数方法的集合，key值为方法的名字
    private HashMap<String, FunctionWithParam> mFunctionWithParam;
    //无参数无返回值的方法集合，同理key值为方法名字
    private HashMap<String, FunctionNoParamAndResult> mFunctionNoParamAndResult;
    //无参数无返回值的方法集合，同理key值为方法名字
    private HashMap<String, FunctionWithResult> mFunctionResult;
    /**
     * 基础方法类
     */
    public static abstract class Function {
        //方法的名字，用来做调用，也可以理解为方法的指针
        public String mFunctionName;

        public Function(String functionName) {
            this.mFunctionName = functionName;
        }
    }

    /**
     * 带有参数没有返回值的方法
     *
     * @param <Param> 参数
     */
    public static abstract class FunctionWithParam<Param> extends Function {

        public FunctionWithParam(String functionName) {
            super(functionName);
        }

        public abstract void function(Param param);
    }

    /**
     * 没有参数和返回值的方法
     */
    public static abstract class FunctionNoParamAndResult extends Function {
        public FunctionNoParamAndResult(String functionName) {
            super(functionName);
        }

        public abstract void function();
    }

    /**
     * 有返回值，没有参数的方法
     *
     * @param <Result>
     */
    public static abstract class FunctionWithResult<Result> extends Function {

        public FunctionWithResult(String functionName) {
            super(functionName);
        }

        public abstract Result function();
    }

    /**
     * 添加带参数的函数
     *
     * @param function
     * @return
     */
    public Functions addFunction(FunctionWithParam function) {
        if (function == null) {
            return this;
        }
        if (mFunctionWithParam == null) {
            mFunctionWithParam = new HashMap<>(1);
        }

        mFunctionWithParam.put(function.mFunctionName, function);
        return this;
    }

    /**
     * 添加带返回值的函数
     *
     * @param function
     * @return
     */
    public Functions addFunction(FunctionNoParamAndResult function) {
        if (function == null) {
            return this;
        }
        if (mFunctionNoParamAndResult == null) {
            mFunctionNoParamAndResult = new HashMap<>(1);
        }
        mFunctionNoParamAndResult.put(function.mFunctionName, function);
        return this;
    }

    /**
     * 添加带返回值的函数
     *
     * @param function
     * @return
     */
    public Functions addFunction(FunctionWithResult function) {
        if (function == null) {
            return this;
        }
        if (mFunctionResult == null) {
            mFunctionResult = new HashMap<>(1);
        }
        mFunctionResult.put(function.mFunctionName, function);
        return this;
    }
    /**
     * 根据函数名，回调无参无返回值的函数
     *
     * @param funcName
     */
    public void invokeFunc(String funcName) throws FunctionException {
        FunctionNoParamAndResult f = null;
        if (mFunctionNoParamAndResult != null) {
            f = mFunctionNoParamAndResult.get(funcName);
            if (f != null) {
                f.function();
            }
        }
        if (f == null) {
            throw new FunctionException("没有此函数");
        }
    }

    /**
     * 调用具有参数的函数
     *
     * @param funcName
     * @param param
     * @param <Param>
     */
    public <Param> void invokeFunc(String funcName, Param param) throws FunctionException {
        FunctionWithParam f = null;
        if (mFunctionWithParam != null) {
            f = mFunctionWithParam.get(funcName);
            if (f != null) {
                f.function(param);
            }
        }
    }
    /**
     * 调用无参数有返回值的函数
     *
     * @param funcName
     * @param <Param>
     */
    public <Param> void invokeFuncs(String funcName,Param s) throws FunctionException {
        FunctionWithResult f = null;
        if (mFunctionResult != null) {
            f = mFunctionResult.get(funcName);
            if (f != null) {
                f.function();
            }
        }
    }
}
