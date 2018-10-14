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
- (Not necessary function, just for FUN!)embed the Neo4J module and inject the data, refer to https://neo4j.com/developer/spring-data-neo4j/
- allow the users to update the authors' information (done on 2018/10/09)
- allow multi-page show in which ajax technique may be used, refer to http://www.importnew.com/24722.html 
- allow the article title to be highlighted
- try new analyzer other tan IKAnalyzer
- add the user management module including sign in and out
 
## Quick start
use whatever JAVA IDE on your favor, download AIAS codes and run, have FUN!

## Status
I'm proud of issuing the Beta 0.1 which nicely solves most of editor's questions(2018 Mid-autumn Day).

## What is included?
Due to the complexity of the designed system, many underlying technologies are used including Java, Spring, Spring boot, MySQL, Elasticsearch and etc(Neo4J in future).


## Thanks
Many thanks to Mr. NI Shunqian who took me to the IDEA world.


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

# elasticsearch(ES)的java开发
创建完成ES的索引(index)和记录（document）后，如何利用java查询数据成为首先要解决的问题。ES提供了两个强大的类协助解决此类问题，分别是QueryBuilders和SearchQuery。下面结合范例具体讲解两个类的用法。
## QueryBuilders的用法
ES提供了与REST Query DSL具有类似功能的纯JAVA查询DSL（domain specific language）。查询构建者工厂是QueryBuilders。通过查看源码可知，该类是抽象类，包含了一系列用于构建各种类型查询的静态函数。使用QueryBuilders需要import到代码中，如下
```aidl
import static org.elasticsearch.index.query.QueryBuilders.*;
```

QueryBuilders工厂构建的查询共分为七大类：

| 类别名称 | 使用场景 | 功能说明 | 包含的函数 |
| :------: | ------ | ------ | ------ |
| Full text queries | 对整个文本域上进行文本查询 | 对查询语句使用各域上配置分词器进行分词后执行查询操作| matchQuery, multiMatchQuery, commonTermsQuery, queryStringQuery |
| Term level queries | 用于数字、日期以及枚举等结构化数据 | 对查询语句不做分词预处理，作为整体进行查询 | termQuery，termsQuery，rangeQuery， existsQuery， wildcardQuery， regexpQuery |
| Compound queries | 复合查询 | 将复合查询或者叶子查询归拢在一起后进行两种操作，组合这些查询的结果或者评分或者从查询（query context）切换到过滤上下文（filter context） | constantScoreQuery，|
| Joining queries | 交叉查询 ||
| Geo queries | 地理位置查询 ||
| Specialized queries | 特殊查询 | 用于相似文本检索或者使用脚本作为过滤条件或者|
| Span queries | span查询 | 主要用于立法文件或专利 |

背景知识：
- Query Context 和 Filter Context
查询短句的行为取决于查询是在query还是filter上下文中使用。
1. Query Context: 在此场景使用的查询短句回答了这个问题：“这个文档有多么好的匹配这个查询短句？”在决定文档是否匹配的基础上，查询短句也计算了_score的值，用于表示相对于其他文档，这个文档的匹配度。
2. Filter Context:  在此场景使用的查询短句回答了这个问题：“这个文档匹配这个查询短句吗？”回答是YES或者NO，无需计算任何分值。Filter Context大部分应用于结构化数据。

难点函数解析：
- commonTermsQuery：此函数是一种替换停用词（stopwords）的先进功能，在考虑停用词前提下提高了查询结果的准确性和召回率。不熟悉停用词的请参见：https://blog.csdn.net/yang090510118/article/details/37993627
在已有开发系统中只是使用了matchQuery，确实遇到了这个问题，比如查询“我是天才”，会把含有“我”和“是”的搜索结果排的靠前。为此，commonTermsQuery就颇为有用。对查询语句进行分词后对词语进行了分组，重要词（频率低）和次重要词（频率高）。
通常来看，频率高的词是停用词的可能性较大。common term query首先对重要词进行全文检索，然后在检索结果中对次重要词进行检索。
- boolQuery：此函数主要用于组合查询，用于匹配满足多个查询的布尔组合，对应于lucene的BooleanQuery。基于一个或者多个布尔查询短句，
每个短句都有一种关键词类型，如下：

| 关键字类型 | 功能说明 |
| :------: | ------ |
| must | 查询短句必须出现在文本中并且对评分有贡献 |
| filter | 查询短句必须出现在文本中. 与must不同， 此查询的评分不计算. Filter短句在filter context中执行, 也就是说不计算评分并短句将被缓存。|
| should | 短句视情况是否出现在文本中。当bool查询组合中含有must和filter时，匹配must和filter文档无需考虑是否匹配should短句。否则，文档仅需满足should短句中一条或者多条，由 minimum_should_match参数决定。|
| must_not | 匹配文档中必定不包含此类短句 |

## Search API的用法
可以使用Search API进行查询（查询语句由QueryBuilders来构建）并返回搜索命中结果。

- Using scrolls in Java

#Java Basics
## interface extends implements
extends用于继承，implements用于实现接口。可以通过继承在新接口中组合数个接口。

## import static

# other useful resource
Chinese Stopwords:

的一不在人有是为以于上他而后之来及了因下可到由这与也此但并个其已无小我们起最再今去好只又或很亦某把那你乃它吧被比别趁当从到得打凡儿尔该各给跟和何还即几既看据距靠啦了另么每们嘛拿哪那您凭且却让仍啥如若使谁虽随同所她哇嗡往哪些向沿哟用于咱则怎曾至致着诸自