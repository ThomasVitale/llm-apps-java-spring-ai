# LLM Applications with Java and Spring AI

Samples showing how to build Java applications powered by Generative AI and LLMs using [Spring AI](https://docs.spring.io/spring-ai/reference/).

## Pre-Requisites

* Java 21
* Docker/Podman
* [OpenAI](http://platform.openai.com) API Key (optional)
* [Ollama](https://ollama.ai) (optional)

## Content

### 1. Chat Models

| Project                                                                                                                   | Description                           |
|---------------------------------------------------------------------------------------------------------------------------|---------------------------------------|
| [chat-models-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/01-chat-models/chat-models-ollama) | Text generation with LLMs via Ollama. |
| [chat-models-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/01-chat-models/chat-models-openai) | Text generation with LLMs via OpenAI. |

### 2. Prompts

| Project                                                                                                                            | Description                                                         |
|------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------|
| [prompts-basics-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/02-prompts/prompts-basics-ollama)        | Prompting using simple text with LLMs via Ollama.                   |
| [prompts-basics-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/02-prompts/prompts-basics-openai)        | Prompting using simple text with LLMs via OpenAI.                   |
| [prompts-messages-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/02-prompts/prompts-messages-ollama)    | Prompting using structured messages and roles with LLMs via Ollama. |
| [prompts-messages-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/02-prompts/prompts-messages-openai)    | Prompting using structured messages and roles with LLMs via OpenAI. |
| [prompts-templates-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/02-prompts/prompts-templates-ollama)  | Prompting using templates with LLMs via Ollama.                     |
| [prompts-templates-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/02-prompts/prompts-templates-openai)  | Prompting using templates with LLMs via OpenAI.                     |

### 3. Output Parsers

| Project                                                                                                                            | Description                                                                  |
|------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------|
| [output-parsers-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/03-output-parsers/output-parsers-ollama) | Parsing the LLM output as structured objects (Beans, Map, List) via Ollama.  |
| [output-parsers-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/03-output-parsers/output-parsers-openai) | Parsing the LLM output as structured objects (Beans, Map, List) via Open AI. |

### 4. Embedding Models

| Project                                                                                                                                  | Description                                              |
|------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------|
| [embedding-models-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/04-embedding-models/embedding-models-ollama) | Vector transformation (embeddings) with LLMs via Ollama. |
| [embedding-models-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/04-embedding-models/embedding-models-openai) | Vector transformation (embeddings) with LLMs via OpenAI. |

### 5. Document Readers

| Project                                                                                                                                            | Description                                                  |
|----------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------|
| [document-readers-json-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/05-document-readers/document-readers-json-ollama) | Reading and vectorizing JSON documents with LLMs via Ollama. |
| [document-readers-pdf-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/05-document-readers/document-readers-text-ollama)  | Reading and vectorizing PDF documents with LLMs via Ollama.  |
| [document-readers-text-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/05-document-readers/document-readers-text-ollama) | Reading and vectorizing text documents with LLMs via Ollama. |

### 6. Document Transformers

| Project                                                                                                                                                                     | Description                                                                             |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------|
| [document-transformers-metadata-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/06-document-transformers/document-transformers-metadata-ollama)   | Enrich documents with keywords and summary metadata for enhanced retrieval via Ollama.  |
| [document-transformers-splitters-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/06-document-transformers/document-transformers-splitters-ollama) | Divide documents into chunks to fit the LLM context window via Ollama.                  |

### 7. Document Writers

_Coming soon_

### 8. Vector Stores

_Coming soon_

## References and Additional Resources

* [Spring AI](https://docs.spring.io/spring-ai/reference/index.html)
* [Spring AI Azure Workshop](https://github.com/Azure-Samples/spring-ai-azure-workshop)
