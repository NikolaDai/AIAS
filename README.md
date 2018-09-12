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
# To-do list
- Create the testing MySQL database and inject the test-data and test successfully;(done on 09/12 22:39)
- Embed the Elasticsearch module and run the test;

# Key lessons learnt in the coding
The entity's attribute name, let's say authorName, will map to the column "author_name" in the MySQL table.