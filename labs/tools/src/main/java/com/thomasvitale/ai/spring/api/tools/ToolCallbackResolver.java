package com.thomasvitale.ai.spring.api.tools;

import org.springframework.ai.model.function.FunctionCallback;

public interface ToolCallbackResolver {

    FunctionCallback[] getToolCallbacks();

}
