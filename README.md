# LLM and AI-Infused Applications with Java and Spring AI

Samples showing how to build Java applications powered by Generative AI and Large Language Models (LLMs) using [Spring AI](https://docs.spring.io/spring-ai/reference/).

## Pre-Requisites

* Java 22
* Docker/Podman
* [Mistral AI](https://console.mistral.ai) API Key (optional)
* [OpenAI](https://platform.openai.com) API Key (optional)
* [Ollama](https://ollama.ai) (optional)

## Content

### 0. Use Cases

| Project                                                                                                                                 | Description                                                                 |
|-----------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------|
| [chatbot](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/00-use-cases/chatbot)                                       | Chatbot using LLMs via Ollama.                                              |
| [question-answering](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/00-use-cases/question-answering)                 | Question answering with documents (RAG) using LLMs via Ollama and PGVector. |
| [semantic-search](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/00-use-cases/semantic-search)                       | Semantic search using LLMs via Ollama and PGVector.                         |
| [structured-data-extraction](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/00-use-cases/structured-data-extraction) | Structured data extraction using LLMs via Ollama.                           |
| [text-classification](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/00-use-cases/text-classification)               | Text classification using LLMs via Ollama.                                  |

### 1. Chat Completion Models

| Project                                                                                                                                           | Description                                       |
|---------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------|
| [chat-models-mistral-ai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/01-chat-models/chat-models-mistral-ai)                 | Text generation with LLMs via Mistral AI.         |
| [chat-models-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/01-chat-models/chat-models-ollama)                         | Text generation with LLMs via Ollama.             |
| [chat-models-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/01-chat-models/chat-models-openai)                         | Text generation with LLMs via OpenAI.             |
| [chat-models-multiple-providers](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/01-chat-models/chat-models-multiple-providers) | Text generation with LLMs via multiple providers. |

### 2. Prompts, Messages, and Templates and Multimodality

| Project                                                                                                                                     | Description                                                              |
|---------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------|
| [prompts-basics-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/02-prompts/prompts-basics-ollama)                 | Prompting using simple text with LLMs via Ollama.                        |
| [prompts-basics-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/02-prompts/prompts-basics-openai)                 | Prompting using simple text with LLMs via OpenAI.                        |
| [prompts-messages-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/02-prompts/prompts-messages-ollama)             | Prompting using structured messages and roles with LLMs via Ollama.      |
| [prompts-messages-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/02-prompts/prompts-messages-openai)             | Prompting using structured messages and roles with LLMs via OpenAI.      |
| [prompts-templates-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/02-prompts/prompts-templates-ollama)           | Prompting using templates with LLMs via Ollama.                          |
| [prompts-templates-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/02-prompts/prompts-templates-openai)           | Prompting using templates with LLMs via OpenAI.                          |

### 3. Structured Output

| Project                                                                                                                               | Description                                                                     |
|---------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------|
| [structured-output-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/03-structured-output/structured-output-ollama) | Converting the LLM output to structured JSON and Java objects via Ollama.       |
| [structured-output-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/03-structured-output/structured-output-openai)    | Converting the LLM output to structured JSON and Java objects via Open AI. |

### 4. Multimodality

| Project                                                                                                                                | Description                                                              |
|----------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------|
| [multimodality-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/04-multimodality/multimodality-ollama) | Multimodality to include various media in a prompt with LLMs via Ollama. |
| [multimodality-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/04-multimodality/multimodality-openai)      | Multimodality to include various media in a prompt with LLMs via OpenAI. |

### 5. Function Calling

| Project                                                                                                                                          | Description                                |
|--------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------|
| [function-calling-mistral-ai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/05-function-calling/function-calling-mistral-ai) | Function calling with LLMs via Mistral AI. |
| [function-calling-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/05-function-calling/function-calling-ollama)         | Function calling with LLMs via Ollama.     |
| [function-calling-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/05-function-calling/function-calling-openai)         | Function calling with LLMs via OpenAI.     |

### 6. Embedding Models

| Project                                                                                                                                              | Description                                                                                     |
|------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------|
| [embedding-models-mistral-ai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/06-embedding-models/embedding-models-mistral-ai)     | Vector transformation (embeddings) with LLMs via Mistral AI.                                    |
| [embedding-models-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/06-embedding-models/embedding-models-ollama)             | Vector transformation (embeddings) with LLMs via Ollama.                                        |
| [embedding-models-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/06-embedding-models/embedding-models-openai)             | Vector transformation (embeddings) with LLMs via OpenAI.                                        |
| [embedding-models-transformers](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/06-embedding-models/embedding-models-transformers) | Vector transformation (embeddings) with LLMs via ONNX Sentence Transformers. |

### 7. Data Ingestion

| Project                                                                                                                                                            | Description                                                  |
|--------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------|
| [document-readers-json-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/07-data-ingestion/document-readers-json-ollama)                   | Reading and vectorizing JSON documents with LLMs via Ollama. |
| [document-readers-pdf-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/07-data-ingestion/document-readers-text-ollama)                      | Reading and vectorizing PDF documents with LLMs via Ollama.  |
| [document-readers-text-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/07-data-ingestion/document-readers-text-ollama)                     | Reading and vectorizing text documents with LLMs via Ollama. |
| [document-transformers-metadata-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/07-data-ingestion/document-transformers-metadata-ollama)   | Enrich documents with keywords and summary metadata for enhanced retrieval via Ollama.  |
| [document-transformers-splitters-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/07-data-ingestion/document-transformers-splitters-ollama) | Divide documents into chunks to fit the LLM context window via Ollama.                  |

### 8. Vector Stores

_Coming soon_

### 9. Retrieval Augmented Generation (RAG)

_Coming soon_

### 10. Memory

_Coming soon_

### 11. Image Models

| Project                                                                                                                      | Description                            |
|------------------------------------------------------------------------------------------------------------------------------|----------------------------------------|
| [image-models-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/11-image-models/image-models-openai) | Image generation with LLMs via OpenAI. |

### 12. Audio Models

| Project                                                                                                                                                  | Description                                |
|----------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------|
| [audio-models-speech-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/12-audio-models/audio-models-speech-openai)               | Speech generation with LLMs via OpenAI.    |
| [audio-models-transcription-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/12-audio-models/audio-models-transcription-openai) | Speech transcription with LLMs via OpenAI. |

### 13. Moderation Models

_Coming soon_

### 14. Observability

| Project                                                                                                                                                         | Description                              |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------|
| [observability-models-mistral-ai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/10-observability/observability-models-mistral-ai)            | LLM Observability for Mistral AI.        |
| [observability-models-ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/10-observability/observability-models-ollama)                   | LLM Observability for Ollama.            |
| [observability-models-openai](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/10-observability/observability-models-openai)                   | LLM Observability for OpenAI.            |
| [observability-vector-stores-pgvector](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/10-observability/observability-vector-stores-pgvector) | Vector Store Observability for PGVector. |

### 15. Evaluation

_Coming soon_

### 16. Agents

_Coming soon_

## References and Additional Resources

* [Spring AI Reference Documentation](https://docs.spring.io/spring-ai/reference/index.html)

### Conferences

* [Introducing Spring AI by Christian Tzolov and Mark Pollack (Spring I/O 2024)](https://www.youtube.com/watch?v=umKbaXsiCOY)
* [Concerto for Java and AI - Building Production-Ready LLM Applications by Thomas Vitale (Spring I/O 2024)](https://www.youtube.com/watch?v=3zTf8NxF-6o)

### Videos

* [Building Intelligent Applications With Spring AI by Dan Vega (JetBrains Live Stream)](https://www.youtube.com/watch?v=x6KmUyPWy2Q)
* [Spring AI Series by Dan Vega](https://www.youtube.com/playlist?list=PLZV0a2jwt22uoDm3LNDFvN6i2cAVU_HTH)
* [Spring AI Series by Craig Walls](https://www.youtube.com/playlist?list=PLH5OU4wXVJc9aECkMUVPCi8g3pzs8pZ3E)
* [Spring AI Series by Josh Long](https://www.youtube.com/playlist?list=PLgGXSWYM2FpMXvYb681axdH5JSLEPPyrz)

### Demos

* [Airline Customer Support (Marcus Hellberg)](https://github.com/marcushellberg/java-ai-playground/tree/spring-ai)
* [Composer Assistant (Thomas Vitale)](https://github.com/ThomasVitale/concerto-for-java-and-ai)
* [Document Assistant (Marcus Hellberg)](https://github.com/marcushellberg/docs-assistant)
* [Flight Booking (Christian Tzolov)](https://github.com/tzolov/playground-flight-booking)

### Workshops

* [Spring AI - Zero to Hero (Adib Saikali, Christian Tzolov)](https://github.com/asaikali/spring-ai-zero-to-hero/tree/main)
