package com.gerivansantos.azureonfig;

import com.azure.core.http.rest.PagedIterable;
import com.azure.data.appconfiguration.ConfigurationClient;
import com.azure.data.appconfiguration.ConfigurationClientBuilder;
import com.azure.data.appconfiguration.models.ConfigurationSetting;
import com.azure.data.appconfiguration.models.SettingSelector;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AzureConfiguration {

    public Map<String, String> getAllEnvVariablesByLabel(String connectionString, String serviceLabel) {
        ConfigurationClient configurationClient = new ConfigurationClientBuilder()
                .connectionString(connectionString)
                .buildClient();
        String[] labels = {"global", serviceLabel};

        Map<String, String> envCloud = new HashMap<String, String>();

        for (String label : labels) {
            SettingSelector selectorByLabelGlobal = new SettingSelector().setLabelFilter(label);

            PagedIterable<ConfigurationSetting> envCloudLabel = configurationClient.listConfigurationSettings(selectorByLabelGlobal);

            envCloudLabel.streamByPage().forEach(resp ->
                    resp.getElements().forEach(value ->
                            envCloud.put(value.getKey(), value.getValue())
                    )
            );
        }
        return envCloud;
    }

    public Map<String, String> setLocalSystemVariables(Map<String, String> envCloud) {
        Map<String, String> envSystem = System.getenv();

        for (String envName : envSystem.keySet()) {
            envCloud.put(envName, envSystem.get(envName));
        }

        return envCloud;
    }

    public Map<String, String> getAllVariablesEnviroment() {
        final String APP_CONFIGURATION_CONNECTION_STRING = System.getenv("APP_CONFIGURATION_CONNECTION_STRING");
        final String SERVICE_LABEL = System.getenv("SERVICE_LABEL");

        return setLocalSystemVariables(getAllEnvVariablesByLabel(APP_CONFIGURATION_CONNECTION_STRING, SERVICE_LABEL));
    }
}
