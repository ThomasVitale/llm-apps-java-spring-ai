# LLM and AI-Infused Applications with Java & Spring AI

Samples showing how to build Java applications powered by Generative AI and Large Language Models (LLMs) using [Spring AI](https://docs.spring.io/spring-ai/reference/).

## 🛠️ Pre-Requisites

* Java 23
* Podman/Docker

## 💡 Use Cases

* 🤖 **[Chatbot](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/use-cases/chatbot)**
  Chatbot using LLMs via Ollama.

* ❓ **[Question Answering](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/use-cases/question-answering)**
  Question answering with documents (RAG) using LLMs via Ollama and PGVector.

* 🔍 **[Semantic Search](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/use-cases/semantic-search)**
  Semantic search using LLMs via Ollama and PGVector.

* 📊 **[Structured Data Extraction](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/use-cases/structured-data-extraction)**  
  Structured data extraction using LLMs via Ollama.

* 🏷️ **[Text Classification](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/use-cases/text-classification)**
  Text classification using LLMs via Ollama.

## 🧠 Models

### 💬 Chat Models

Chat completion with LLMs via different model providers:

* **[Mistral AI](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/models/chat/chat-mistral-ai)**
* **[Ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/models/chat/chat-ollama)**
* **[OpenAI](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/models/chat/chat-openai)**
* **[Multiple Providers](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/models/chat/chat-multiple-providers)**

### 🔤 Embedding Models

Vector transformation (embeddings) with LLMs via different model providers:

* **[Mistral AI](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/models/embedding/embedding-mistral-ai)**
* **[Ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/models/embedding/embedding-ollama)**
* **[OpenAI](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/models/embedding/embedding-openai)**
* **[ONNX Transformers](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/models/embedding/embedding-transformers)**

### 🎨 Image Models

Image generation with LLMs via different model providers:

* **[OpenAI](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/models/image/image-openai)**

### 🎵 Audio Models

Speech generation with LLMs via different model providers:

* **[OpenAI](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/models/audio/speech-to-text-openai)**

Speech transcription with LLMs via different model providers:

* **[OpenAI](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/models/audio/text-to-speech-openai)**

### 🛡️ Moderation Models

_Coming soon_

## 📐 Patterns

### 📝 Prompts, Messages, and Templates

Prompting using simple text:

* **[Ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/patterns/prompts/prompts-basics-ollama)**
* **[OpenAI](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/patterns/prompts/prompts-basics-openai)**

Prompting using structured messages and roles:

* **[Ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/patterns/prompts/prompts-messages-ollama)**
* **[OpenAI](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/patterns/prompts/prompts-messages-openai)**

Prompting using templates:

* **[Ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/patterns/prompts/prompts-templates-ollama)**
* **[OpenAI](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/patterns/prompts/prompts-templates-openai)**

### 📊 Structured Output

Converting LLM output to structured JSON and Java objects:

* **[Ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/patterns/structured-output/structured-output-ollama)**
* **[OpenAI](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/patterns/structured-output/structured-output-openai)**

### 🎯 Multimodality

Including various media in prompts with LLMs:

* **[Mistral AI](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/patterns/multimodality/multimodality-mistral-ai)**
* **[Ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/patterns/multimodality/multimodality-ollama)**
* **[OpenAI](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/patterns/multimodality/multimodality-openai)**

### ⚙️ Function Calling/Tools

Function calling with LLMs via different model providers:

* **[Mistral AI](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/patterns/function-calling/function-calling-mistral-ai)**
* **[Ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/patterns/function-calling/function-calling-ollama)**
* **[OpenAI](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/patterns/function-calling/function-calling-openai)**

### 🧠 Memory

_Coming soon_

## 📥 Data Ingestion

### 📚 Document Readers

Reading and vectorizing documents with LLMs via Ollama:

* **[JSON](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/data-ingestion/document-readers/document-readers-json-ollama)**
* **[Markdown](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/data-ingestion/document-readers/document-readers-markdown-ollama)**
* **[PDF](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/data-ingestion/document-readers/document-readers-text-ollama)**
* **[Text](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/data-ingestion/document-readers/document-readers-text-ollama)**
* **[Tika](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/data-ingestion/document-readers/document-readers-tika-ollama)**

### 🔄 Document Transformers

Document transformation with LLMs via Ollama:

* **[Metadata](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/data-ingestion/document-transformers/document-transformers-metadata-ollama)**  
  Enrich documents with keywords and summary metadata for enhanced retrieval.
* **[Splitters](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/data-ingestion/document-transformers/document-transformers-splitters-ollama)**  
  Divide documents into chunks to fit the LLM context window.

## 🔢 Vector Stores

_Coming soon_

## 🔄 Retrieval Augmented Generation (RAG)

Question answering with documents using different RAG flows (with Ollama and PGVector):

### ➡️ Sequential RAG

* **[Naive](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/rag/rag-sequential/rag-naive)**
* **[Advanced](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/rag/rag-sequential/rag-advanced)**

### 🌳 Branching RAG

* **[Branching](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/rag/rag-branching)**

## 📊 Observability

### 🧠 LLM Observability

LLM Observability for different model providers:

* **[Mistral AI](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/observability/observability-models-mistral-ai)**
* **[Ollama](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/observability/observability-models-ollama)**
* **[OpenAI](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/observability/observability-models-openai)**

### 🔢 Vector Store Observability

Vector Store Observability for different vector stores:

* **[PGVector](https://github.com/ThomasVitale/llm-apps-java-spring-ai/tree/main/observability/observability-vector-stores-pgvector)**

## 📋 Evaluation

_Coming soon_

## 🤖 Agents

_Coming soon_

## 📚 References and Additional Resources

* [Spring AI Reference Documentation](https://docs.spring.io/spring-ai/reference/index.html)

### 🎤 Conferences

* [Introducing Spring AI by Christian Tzolov and Mark Pollack (Spring I/O 2024)](https://www.youtube.com/watch?v=umKbaXsiCOY)
* [Spring AI Is All You Need by Christian Tzolov (GOTO Amsterdam 2024)](https://www.youtube.com/watch?v=vuhMti8B5H0)
* [Concerto for Java and AI - Building Production-Ready LLM Applications by Thomas Vitale (GOTO Copenhagen 2024)](https://www.youtube.com/watch?v=la4kc57F6jU)

### 🎥 Videos

* [Building Intelligent Applications With Spring AI by Dan Vega (JetBrains Live Stream)](https://www.youtube.com/watch?v=x6KmUyPWy2Q)
* [Spring AI Series by Dan Vega](https://www.youtube.com/playlist?list=PLZV0a2jwt22uoDm3LNDFvN6i2cAVU_HTH)
* [Spring AI Series by Craig Walls](https://www.youtube.com/playlist?list=PLH5OU4wXVJc9aECkMUVPCi8g3pzs8pZ3E)
* [Spring AI Series by Josh Long](https://www.youtube.com/playlist?list=PLgGXSWYM2FpMXvYb681axdH5JSLEPPyrz)

### 🎮 Demos

* [Airline Customer Support (Marcus Hellberg)](https://github.com/marcushellberg/java-ai-playground/tree/spring-ai)
* [Composer Assistant (Thomas Vitale)](https://github.com/ThomasVitale/concerto-for-java-and-ai)
* [Document Assistant (Marcus Hellberg)](https://github.com/marcushellberg/docs-assistant)
* [Flight Booking (Christian Tzolov)](https://github.com/tzolov/playground-flight-booking)

### 📚 Workshops

* [Spring AI - Zero to Hero (Adib Saikali, Christian Tzolov)](https://github.com/asaikali/spring-ai-zero-to-hero/tree/main)
* [AI Applications with Java and Spring AI (Thomas Vitale)](https://github.com/ThomasVitale/java-ai-workshop)
