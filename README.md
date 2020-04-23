# Azure App Configuration for Java with Spring Boot

Use Azure App Configuration to get all the application variables configured in Azure

Proof of concept (POC) implemented based on Azure documentation at link - https://github.com/Azure/azure-sdk-for-java/blob/master/sdk/appconfiguration/azure-data-appconfiguration/README.md

### Include the Package

[//]: # ({x-version-update-start;com.azure:azure-data-appconfiguration;current})
Maven
```xml
<dependency>
  <groupId>com.azure</groupId>
  <artifactId>azure-data-appconfiguration</artifactId>
  <version>1.1.1</version>
</dependency>
```
or

Gradle
```
implementation 'com.azure:azure-data-appconfiguration:1.1.1'
```

##### Create a Configuration Client

Once you have the value of the connection string you can create the configuration client:

<!-- embedme ./src/samples/java/com/azure/data/appconfiguration/ReadmeSamples.java#L46-L48 -->
```Java
ConfigurationClient configurationClient = new ConfigurationClientBuilder()
    .connectionString(connectionString)
    .buildClient();
```

or

<!-- embedme ./src/samples/java/com/azure/data/appconfiguration/ReadmeSamples.java#L52-L54 -->
```Java
ConfigurationAsyncClient configurationClient = new ConfigurationClientBuilder()
    .connectionString(connectionString)
    .buildAsyncClient();
```
### List Configuration Settings with multiple keys

List multiple configuration settings by calling `listConfigurationSettings`.
Pass a null `SettingSelector` into the method if you want to fetch all the configuration settings and their fields.

<!-- embedme ./src/samples/java/com/azure/data/appconfiguration/ReadmeSamples.java#L136-L141 -->
```Java
String key = "some_key";
String key2 = "new_key";
configurationClient.setConfigurationSetting(key, "some_label", "some_value");
configurationClient.setConfigurationSetting(key2, "new_label", "new_value");
SettingSelector selector = new SettingSelector().setKeyFilter(key + "," + key2);
PagedIterable<ConfigurationSetting> settings = configurationClient.listConfigurationSettings(selector);
```