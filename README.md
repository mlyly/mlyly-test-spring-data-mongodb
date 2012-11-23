# Spring data and Mongodb testing

1. Get MongoDB and run it

```
	mkdir data
	mongod --dbpath=data
```

2. build & test (see TestApp.java)
```
	mvn clean install
```

You should see something like this:
```
15:21:30,981 INFO  [main] [AppTest] testApp)()...
15:21:30,982 INFO  [main] [AppTest]   mongoTemplate: org.springframework.data.mongodb.core.MongoTemplate@7e7f8062
15:21:30,982 INFO  [main] [AppTest] save...
15:21:31,022 INFO  [main] [AppTest] find...
15:21:31,996 INFO  [main] [AppTest]   found: User[username=theusername, password=***, age=42, fn=NA, ln=NA]
15:21:31,996 INFO  [main] [AppTest] update...
15:21:31,999 INFO  [main] [AppTest] find...
15:21:32,001 INFO  [main] [AppTest]   found: User[username=theusername, password=***, age=42, fn=NA, ln=NA]
15:21:32,001 INFO  [main] [AppTest] findAll...
15:21:32,004 INFO  [main] [AppTest] findAll... number of users: 1
15:21:32,004 INFO  [main] [AppTest]   user: User[username=theusername, password=***, age=42, fn=NA, ln=NA]
15:21:32,004 INFO  [main] [AppTest] delete...
15:21:32,005 INFO  [main] [AppTest] findAll...
15:21:32,006 INFO  [main] [AppTest] findAll... number of users: 0
15:21:32,006 INFO  [main] [AppTest] Create 1000 users...
15:21:32,273 INFO  [main] [AppTest] Find all age <= 2...
15:21:32,281 INFO  [main] [AppTest]   found number of users where age <= 2: 28
15:21:32,281 INFO  [main] [AppTest]   user: User[username=1353676892006, password=***, age=2, fn=Elvis, ln=Mozart]
15:21:32,281 INFO  [main] [AppTest]   user: User[username=1353676892006, password=***, age=2, fn=Elvis, ln=Joplin]
```
