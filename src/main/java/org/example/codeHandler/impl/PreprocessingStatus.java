package org.example.codeHandler.impl;

public enum PreprocessingStatus {
    /**
     * 预处理成功的状态。
     */
    SUCCESSFUL,

    /**
     * 预处理失败的状态。
     */
    FAILED,

    /**
     * 正在进行预处理的状态。
     */
    RUNNING,

    /**
     * 等待预处理的状态。
     */
    WAITING
}
