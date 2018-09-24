<p align="center">
  <h3 align="center">AIAS</h3>
  <p align="center">
    Satisfy the editors' most urgent needs and provide powerful and accurate tools for writers.
  </p>
</p>
<br>

## Table of Contents
- [To-Do List](#to-do-list)
- [Quick start](#quick-start)
- [Status](#status)
- [What's included](#whats-included)
- [Bugs and feature requests](#bugs-and-feature-requests)
- [Documentation](#documentation)
- [Contributing](#contributing)
- [Community](#community)
- [Versioning](#versioning)
- [Creators](#creators)
- [Thanks](#thanks)
- [Copyright and license](#copyright-and-license)

## To-Do List
- Create the testing MySQL database and inject the test-data and test successfully;(done on 09/12 22:39)
- Embed the Elasticsearch module and run the test;(done on 09/13 23:22)
- improve the returning number of results and based on results do some statistics.(done on 09/16 23:20)
- Look into the URL: https://grokbase.com/t/gg/elasticsearch/128qz6wpzp/highlight-whole-sentence 
- Revisit what i have done and rethink the real need from the users.based on the query input, make some basic judgement, for example, if the user inputs a name, directly show the related author information;
(build a mysql-type database to store the author information for name querying)(done on 09/23 23:40)
- embed the Neo4J module and inject the data;

## 

# Author Information Analysis and Scheduling System(AIAS)
## Step 1: build the initial springMVC based demo
* follow the instructions in 《Spring Boot in Action》
* follow the instructions in 《深入实践spring boot》
* the project files' organization

- 1.authorInformation(based on MySQL): entity and repository;
    - 1.1 create the entity "author"
    - 1.2 create the "author" repository
- 2.paperInformation(based on Elasticsearch)


## Step 2: add MySQL component to this project
I have done all the sub-steps from getting search content from the index.html to return the query data to the searchResult.html. All Fancy Cool.
## step 3: add ElasticSearch component to this project
I will mainly use the spring-data-elasticsearch but have to check if it can really work out. The related document URL is attached:
https://www.baeldung.com/spring-data-elasticsearch-tutorial(done!)

## step 4: finish the key step to make statistics on the result of elasticsearch query
https://blog.csdn.net/goodluck_mh/article/details/73655657

https://blog.csdn.net/Hello_Ray/article/details/82153042
## step 5: highlight the results based on the queries.
https://blog.csdn.net/napoay/article/details/53910646(will refer to this URL later on when checking the USAGE of words)

Create entity annotated by @Document
引题    eyebrowTitle
标题    mainTitle
副题    subTitle
作者    authorName
责编    editorName
版面    pageName
分类    paperCategory
日期    publishDate
体裁    paperType
正文    articleText

# To-do list

## elasticsearch query usage
highlight query:
http://localhost:9200/papers/_search/ POST
{"query":{"match":{"articleText":"身先士卒"}},"size":1,"highlight":{"fields":{"articleText":{}},"boundary_scanner_locale":"zh-cn","boundary_scanner":"sentence","type":"unified"}}

# Key lessons learnt in the coding
The entity's attribute name, let's say authorName, will map to the column "author_name" in the MySQL table.

#Bugs Report
When search "十二届", then something weird happens. Cant tell the reason now.(09/18 11:29) => solved

#Java开发者值得关注的十个技术博客
Java是世界上最流行的程序语言，从1995年问世以来，Java的生态系统在一直在蓬勃的发展着。作为一门健壮的技术，Java社区为广大开发者提供了包括框架、库、web开发sdk、JVM语言等资源。在各种科技行业都能够找到关于Java编程的相关资源，这就是为什么Java会如此受人喜爱。
在今天，发达的网络技术推动了Java的发展，越来越多的人选择在网上学习，查看技术大牛的博客并进行交流成为了日常学习的一个重要手段。在这里，我整理了国外最流行的十大Java技术博客，它们都是由Java技术专家维护的，无论是初学者还是专业程序员都能够在这些地方找到你所需要的Java技术的一系列信息。
1. Adam Bien
Adam Bien的网络博客是最受Java EE开发人员欢迎的博客之一。Adam Bien是Java开发人员的专家，写过一些关于Java编程的书籍，如Real World Java EE Patterns。Adam Bien的网络博客讨论了在Java中的众多最优实践以及在Java EE 7 和Java EE 8的设计模式。除此之外，这里还有许多其它的主题信息，比如Java EE 7微服务、web sockets、应用程序服务器和Java测试等。
2. Antonio’s Blog
Antonio’s Blog是由Java EE顾问专家Antonio Goncalves运营的专业Java博客，此博客可链接到他在Devoxx、Jazoon等国际会议上的一些谈话、研究论文和相关文章。Antonio还出版了几本关于在Java EE 7, Java EE 6 上部署 GlassFish的书籍。Antonio’s Blog上的帖子种类繁多，包括了关于Java EE 7应用程序服务器、数据库模式、Groovy、Intellij IDEA等有用的Java主题的文章。
3. Arun Gupta
Arun Gupta是Java EE团队的创始成员之一，目前是Couchbase的倡导者。他在Sun, Oracle和Red Hat公司所从事的大型技术项目所累积的专业经验是巨大的，这些经验都融入到了他所编写的书籍、教程和文章之中。他的博客中经常会提到与Couchbase、Oracle, NoSQL、Java EE 7、Eclipse等有用的Java主题。
4. A Java Geek
A Java Geek是由Nicolas Fränkel维护的一个有用的Java编程博客。Nicolas Fränkel是一个在Spring框架、Java EE、流程和网络应用程序构建等多个java领域拥有专家头衔的软件架构师。他的博客文章包含了大量的说明、图表和代码示例等内容。 A Java Geek中的包括了Kotlin与变异测试，Spring Boot的登录管理，JUnit vs TestNG等内容。
5. Java, SQL and JOOQ
对于那些想要寻找关于JOOQ有用信息的Java开发者来说，这个博客网站是你不容错过的，它拥有许多关于JOOQ库(Java object-oriented querying)、SQL技巧和Java最佳编程实践的有用文章。这个博客包含了许多有趣的主题，如SQL与NoSQL数据库引擎，Java泛型， Streams API以及Kotlin编程语言等。
6. Vlad Mihalcea’s Blog
Vlad Mihalcea是Red Hat公司Hibernate项目的一名开发倡议者，他出版过一本名为“High Performance Java Persistence”的书籍。他的博客包含了关于Java和相关框架技术（如Hibernate框架、Spring框架、JOOQ、SQL等主题 ）的文章。Vlad Mihalcea的博客主题涉及数据库集成测试、JDBC状态日志、继承等方面内容。
7. Baeldung
Baeldung是由Eugen运营维护的编程博客，目前它成为了关于Java编程语言和相关技术的最热门的博客之一。Eugen是一个充满激情的教育家，他发布了许多有用的课程，包括了关于REST With Spring、Jackson JSON的教程和Spring Security的学习资料。 Java程序员可以在上面找到Java Persistence、REST、 Spring、JSON in Java等主题的文章。
8. The Pragmatic Integrator
The Pragmatic Integrator 是由Pascal Alma运营维护的备受欢迎的Java博客。Pascal Alma是一位编写过开源ESBs、持续集成、云技术、web服务、REST api等多个领域主题文章的专家级JEE开发者，他经常将java各种技术的见解分享在博客上，包括Java SDK for AWS的使用，J2EE应用程序的构建，Java Hadoop的单元测试等内容。
9. Java Revisited
Java Revisited是一个关于Java编程、FIX协议以及Tibco RV的编程类博客，在这里Java语言爱好者可以找到关于Java内核、Java设计模式、多线程、面向对象编程和Spring框架等多种类型的文章。Java Revisited还提供了对各种常见的编程面试问题的解决方法，涵盖的主题包括SQL、数据结构与算法、Hibernate等，除此之外，它还提供了关于Java JSON、Java多线程、Java编程和Java网络的一系列教程。
10. Program Creek
Program Creek是一个覆盖了大量文章、文本教程、代码示例和图表的Java编程类博客，网站的文章包括了Java基础知识、Java XML解析、Java多线程、面向对象概念、Java 8λ、Java API 示例和Java框架等主题。Program Creek还包含了大量的插图和图表来简化复杂的Java主题，比如空语句、监视器、JVM运行时数据分区等。