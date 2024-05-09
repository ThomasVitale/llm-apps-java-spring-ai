# LLM Applications with Java and Spring AI

Samples showing how to build Java applications powered by Generative AI and LLMs using [Spring AI](https://docs.spring.io/spring-ai/reference/).

## Pre-Requisites

* Java 21
* Docker/Podman
* [Mistral AI](https://console.mistral.ai) API Key (optional)
* [OpenAI](https://platform.openai.com) API Key (optional)
* [Ollama](https://ollama.ai) (optional)

## Content

### 0. Use Cases

| Project                                                                                                              | Description                                              |
|----------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------|
| [question-answering-with-documents](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/00-use-cases/question-answering-with-documents) | Question answering with documents using LLMs via Ollama. |

### 1. Chat Completion Models

| Project                                                                                                                                       | Description                                      |
|-----------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------|
| [chat-models-mistral-ai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/01-chat-models/chat-models-mistral-ai)    | Text generation with LLMs via Mistral AI.        |
| [chat-models-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/01-chat-models/chat-models-ollama)            | Text generation with LLMs via Ollama.            |
| [chat-models-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/01-chat-models/chat-models-openai)            | Text generation with LLMs via OpenAI.            |

### 2. Prompts, Templates and Multimodality

| Project                                                                                                                                     | Description                                                              |
|---------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------|
| [prompts-basics-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/02-prompts/prompts-basics-ollama)                 | Prompting using simple text with LLMs via Ollama.                        |
| [prompts-basics-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/02-prompts/prompts-basics-openai)                 | Prompting using simple text with LLMs via OpenAI.                        |
| [prompts-messages-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/02-prompts/prompts-messages-ollama)             | Prompting using structured messages and roles with LLMs via Ollama.      |
| [prompts-messages-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/02-prompts/prompts-messages-openai)             | Prompting using structured messages and roles with LLMs via OpenAI.      |
| [prompts-multimodality-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/02-prompts/prompts-multimodality-ollama)   | Multimodality to include various media in a prompt with LLMs via Ollama. |
| [prompts-multimodality-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/02-prompts/prompts-multimodality-openai)   | Multimodality to include various media in a prompt with LLMs via OpenAI. |
| [prompts-templates-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/02-prompts/prompts-templates-ollama)           | Prompting using templates with LLMs via Ollama.                          |
| [prompts-templates-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/02-prompts/prompts-templates-openai)           | Prompting using templates with LLMs via OpenAI.                          |

### 3. Output Converters

| Project                                                                                                                               | Description                                                                     |
|---------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------|
| [output-converters-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/03-output-converters/output-converters-ollama) | Converting the LLM output to structured objects (Beans, Map, List) via Ollama.  |
| [output-converters-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/03-output-converters/output-converters-openai)    | Converting the LLM output to structured objects (Beans, Map, List) via Open AI. |

### 4. Embedding Models

| Project                                                                                                                                          | Description                                                                                     |
|--------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------|
| [embedding-models-mistral-ai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/04-embedding-models/embedding-models-mistral-ai) | Vector transformation (embeddings) with LLMs via Mistral AI.                                    |
| [embedding-models-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/04-embedding-models/embedding-models-ollama)         | Vector transformation (embeddings) with LLMs via Ollama.                                        |
| [embedding-models-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/04-embedding-models/embedding-models-openai)         | Vector transformation (embeddings) with LLMs via OpenAI.                                        |
| [embedding-models-transformers](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/04-embedding-models/embedding-models-transformers)   | Vector transformation (embeddings) with LLMs via ONNX Sentence Transformers. |

### 5. Document ETL Pipeline

| Project                                                                                                                                                                     | Description                                                  |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------|
| [document-readers-json-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/05-etl-pipeline/document-readers-json-ollama)                              | Reading and vectorizing JSON documents with LLMs via Ollama. |
| [document-readers-pdf-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/05-etl-pipeline/document-readers-text-ollama)                           | Reading and vectorizing PDF documents with LLMs via Ollama.  |
| [document-readers-text-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/05-etl-pipeline/document-readers-text-ollama)                          | Reading and vectorizing text documents with LLMs via Ollama. |
| [document-transformers-metadata-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/05-etl-pipeline/document-transformers-metadata-ollama)   | Enrich documents with keywords and summary metadata for enhanced retrieval via Ollama.  |
| [document-transformers-splitters-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/05-etl-pipeline/document-transformers-splitters-ollama) | Divide documents into chunks to fit the LLM context window via Ollama.                  |

### 6. Vector Stores

_Coming soon_

### 7. Function Calling

| Project                                                                                                                                          | Description                                |
|--------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------|
| [function-calling-mistral-ai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/07-function-calling/function-calling-mistral-ai) | Function calling with LLMs via Mistral AI. |
| [function-calling-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/07-function-calling/function-calling-openai)         | Function calling with LLMs via OpenAI.     |

### 8. Image Models

| Project                                                                                                                | Description                            |
|------------------------------------------------------------------------------------------------------------------------|----------------------------------------|
| [image-models-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/08-image-models/image-models-openai) | Image generation with LLMs via OpenAI. |

### 9. Audio Models

| Project                                                                                                                                           | Description                                |
|---------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------|
| [audio-models-speech-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/09-audio-models/audio-models-speech-openai)        | Speech generation with LLMs via OpenAI.    |
| [audio-models-transcription-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/09-audio-models/audio-models-transcription-openai) | Speech transcription with LLMs via OpenAI. |

## References and Additional Resources

* [Spring AI Reference Documentation](https://docs.spring.io/spring-ai/reference/index.html)

### Videos

* [Building Intelligent Applications With Spring AI by Dan Vega (JetBrains Live Stream)](https://www.youtube.com/watch?v=x6KmUyPWy2Q)
* [Making your @Beans Intelligent by Mark Pollack (Devoxx Belgium 2023)](https://www.youtube.com/watch?v=7OY9fKVxAFQ)
* [Spring AI Series by Dan Vega](https://www.youtube.com/playlist?list=PLZV0a2jwt22uoDm3LNDFvN6i2cAVU_HTH)
* [Spring AI Series by Craig Walls](https://www.youtube.com/playlist?list=PLH5OU4wXVJc9aECkMUVPCi8g3pzs8pZ3E)
* [Spring Tips: Spring AI by Josh Long](https://www.youtube.com/watch?v=aNKDoiOUo9M)

### Demos

* [Airline Customer Support (Marcus Hellberg)](https://github.com/marcushellberg/java-ai-playground/tree/spring-ai)
* [Spring AI Chat (VMware Tanzu)](https://github.com/vmware-tanzu/application-accelerator-samples/tree/main/spring-ai-chat)

### Workshops

* [Spring AI with Azure OpenAI (Azure)](https://github.com/Azure-Samples/spring-ai-azure-workshop)
