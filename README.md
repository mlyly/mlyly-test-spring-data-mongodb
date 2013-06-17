# Spring data and Mongodb testing

1. Build and test (AppTest.java)

Using flapdoodle to bootstartp embedded Mongodb.

	mvn clean install

You should see something like this:

	Running fi.zcode.springmongo.AppTest
	14:08:30,136 INFO  [main] [TestContextManager] @TestExecutionListeners is not present for class [class fi.zcode.springmongo.AppTest]: using defaults.
	14:08:30,158 INFO  [main] [AppTest] setUp()...
	Jun 17, 2013 2:08:30 PM de.flapdoodle.embed.process.store.ArtifactStoreBuilder build
	SEVERE: Build ArtifactStore(useCache:true)
	Extract /Users/mlyly/.embedmongo/osx/mongodb-osx-x86_64-2.4.3.tgz START
	...
	14:08:33,949 INFO  [main] [AppTest] testApp)()...
	14:08:33,949 INFO  [main] [AppTest] save...
	Mon Jun 17 14:08:33.979 [initandlisten] connection accepted from 127.0.0.1:58812 #1 (1 connection now open)
	[mongod output] Mon Jun 17 14:08:33.981 [conn1] run command admin.$cmd { isMaster: 1 }
	[mongod output] Mon Jun 17 14:08:33.981 [conn1] command admin.$cmd command: { isMaster: 1 } ntoreturn:1 keyUpdates:0  reslen:115 0ms
	[mongod output] Mon Jun 17 14:08:33.988 [conn1] opening db:  springmongo
	[mongod output] 14:08:33,988 INFO  [main] [AppTest] find...
	...
	[INFO] ------------------------------------------------------------------------
	[INFO] BUILD SUCCESS
	[INFO] ------------------------------------------------------------------------
	[INFO] Total time: 9.212s
	[INFO] Finished at: Mon Jun 17 14:08:37 EEST 2013
	[INFO] Final Memory: 11M/554M
	[INFO] ------------------------------------------------------------------------
