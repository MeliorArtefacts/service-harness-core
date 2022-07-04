# Melior Service Harness :: Core
<div style="display: inline-block;">
<img src="https://img.shields.io/badge/version-2.2-green?style=for-the-badge"/>
<img src="https://img.shields.io/badge/production-ready-green?style=for-the-badge"/>
<img src="https://img.shields.io/badge/compatibility-spring_boot_2.4.5-green?style=for-the-badge"/>
</div>
<div style="display: inline-block;">
<img src="https://img.shields.io/badge/version-2.3-green?style=for-the-badge"/>
<img src="https://img.shields.io/badge/production-ready-green?style=for-the-badge"/>
<img src="https://img.shields.io/badge/compatibility-spring_boot_2.4.5-green?style=for-the-badge"/>
</div>

## Artefact
Get the artefact and the POM file in the *artefact* folder.
```
<dependency>
    <groupId>org.melior</groupId>
    <artifactId>melior-harness-core</artifactId>
    <version>2.3</version>
</dependency>
```

## Logging
The Melior logging system replaces the Logback logging system that comes with Spring Boot by default, and provides up to 20% better throughput than Logback.

<img src="https://github.com/MeliorArtefacts/service-harness-core/blob/main/pics/logging_performance.png"/>  
[measured in log entries per second; 10 runs of 1,000,000 identical size log entries each]

&nbsp;  
All the Spring Boot logging is muted and is overlaid with logging that developers and operations teams need to see, for example the statistics and activity of the connection pools that are used by the Melior modules, the timing of web requests and of JDBC calls to the database, and glimpses into the CPU usage and memory usage of the application.

The logging system is auto-configured from the application properties.  The **service.name** property is mandatory when the **logging.file.path** property is used.  In this scenario the **service.name** property is used to name the log files.

```
server.port=8000
service.name=myapplication
logging.file.path=/log/file/path
logging.file.history-path=/archive/file/path/%d
logging.level=DEBUG
```

Log entries are written to different types of log files to make analysis and trouble-shooting easier and faster.

```
myapplication.2022-05-25.000001.trc
myapplication.2022-05-25.000001.err
myapplication.2022-05-25.000001.trx
myapplication.2022-05-25.000001.erx
```

|Extension|Description|
|:---|:---|
|`trc`|All detailed trace log entries written at the configured logging level|
|`err`|Only trace log entries written at ERROR logging level|
|`trx`|All transaction log entries, both successful and failed|
|`erx`|Only failed transaction log entries|

&nbsp;  
Trace log entries have a fixed format to ensure consistency across applications.
```
<time stamp, logging level, host name, thread id, correlation id, location, message>
2022-05-25 17:28:39.454, DEBUG, my-pc, 32137927, 4dc6d229-9c88-411d-b7ca-e12911abd8ef, com.example.MyApp.fooBar(), This is a log entry.
```

Transaction log entries have a fixed format to ensure consistency across applications.
```
<time stamp, host name, thread id, correlation id, location, transaction type, transaction status, list of supplimentary parameters, error message>
2022-05-25 17:28:39.486, my-pc, 32137927, 4dc6d229-9c88-411d-b7ca-e12911abd8ef, com.example.MyApp.fooBar(), MyTrx, FAILED, 211, Param1, value1, Param2, value2, Error: This is a failed transaction entry.
```

Set the transaction type and supplimentary parameters in the transaction context, then log the transaction in the success and failure scenarios.
```
String methodName = "fooBar";
TransactionContext transactionContext = TransactionContext.get();
try {
    transactionContext.setTransactionType("MyTrx");
    transactionContext.addArgument("Param1", "value1");
    transactionContext.addArgument("Param2", "value2");

    // transaction body here

    if (error) {
        throw new Exception("This is a failed transaction entry.");
    }
    // success
    logger.transaction(methodName, transactionContext);
}
catch (Exception ex) {
    // failure
    logger.transaction(methodName, transactionContext, "Error: ", ex);
}
```

The logging system may be configured using these application properties.

|Name|Default|Description|
|:---|:---|:---|
|`service.name`||The service name.  Used to generate the log file name when the logging.file.path property is used|
|`logging.file.name`||The log file name.  The current date and the log file number are appended|
|`logging.file.path`||The log file path.  The service name is used to generate the log file name.  The current date and the log file number are appended|
|`logging.file.max-size`|10MB|The maximum size of each individual log file.  The logging system rolls to a new file when this limit is reached|
|`logging.file.history-path`||The log file path for historic files.  Use %d to specify the current date|
|`logging.file.max-history`|7|The number of days to retain historic files for|

&nbsp;
## Service Component
Use the service component harness to get the standard Melior logging system and a configuration object that may be used to access the application properties anywhere and at any time in the application code, even in the constructor.
```
@Component
public class MyComponent extends ServiceComponent {

public MyComponent(ServiceContext serviceContext) throws ApplicationException {
    super(serviceContext)

```

Implement a **configure** method to have more control over accessing the application properties than using @Value annotations or using constructor injection.
```
protected void configure() throws ApplicationException {
    name = configuration.getProperty("service.name");
    active = configuration.getProperty("scanning.active", boolean.class);
}
```

&nbsp;
## Utilities

Various utilities are available, most of which are used in the Melior modules themselves.  These suppliment the utilities that are available in the JAVA APIs and in Spring Boot.

|Package|Highlights|
|:---|:---|
|`org.melior.util.cache`|A bounded LRU cache, a bounded timed cache|
|`org.melior.util.collection`|Blocking queues|
|`org.melior.util.number`|Counters, clamp functions|
|`org.melior.util.object`|Coalesce functions, collect objects into arrays|
|`org.melior.util.resilience`|Retry method calls|
|`org.melior.util.semaphore`|A gate to coordinate thread interactions|
|`org.melior.util.serialize`|Serialize and deserialize POJOs|
|`org.melior.util.string`|Various string based functions|
|`org.melior.util.thread`|Lightweight daemon threads, thread control|
|`org.melior.util.time`|Date parser/formatter, timers|

&nbsp;  
## References
[**Melior Service Harness :: JDBC**](https://github.com/MeliorArtefacts/service-harness-jdbc) - JDBC connection pool and data access object.  
[**Melior Service Harness :: Web : Rest**](https://github.com/MeliorArtefacts/service-harness-web-rest) - REST client and REST service harness.  
[**Melior Service Harness :: Mongo**](https://github.com/MeliorArtefacts/service-harness-mongo) - MongoDB client, MongoDB listener and MongoDB service harness.  
[**Melior Service Harness :: LDAP**](https://github.com/MeliorArtefacts/service-harness-ldap) - LDAP client.  
[**Melior Service Harness :: JMS : ActiveMQ**](https://github.com/MeliorArtefacts/service-harness-jms-activemq) - ActiveMQ client.  
